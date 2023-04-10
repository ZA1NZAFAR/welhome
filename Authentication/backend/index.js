const express = require('express');
const cors = require('cors');
const dotenv = require('dotenv');
const { OAuth2Client } = require('google-auth-library');
const axios = require('axios');
const db = require('./db');
const jwt = require('jsonwebtoken');


dotenv.config();

const app = express();

app.use(express.json());

const googleClient = new OAuth2Client("203530020577-ngl05517r4rdn6fv8nudhgaod7p8itrt.apps.googleusercontent.com", "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");
const secretKey = process.env.SECRET_KEY;
app.use(cors({
  origin: 'http://localhost:3000',
  credentials: true,
}));

app.get('/auth/google', (req, res) => {
  //const googleAuthURL = `https://accounts.google.com/o/oauth2/v2/auth?client_id=${process.env.GOOGLE_CLIENT_ID}&redirect_uri=${process.env.GOOGLE_REDIRECT_URI}&response_type=code&scope=profile%20email&access_type=offline`;
  const googleAuthURL = `https://accounts.google.com/o/oauth2/v2/auth?client_id=203530020577-ngl05517r4rdn6fv8nudhgaod7p8itrt.apps.googleusercontent.com&redirect_uri=https://backend.zain.ovh/auth/google/callback&response_type=code&scope=profile%20email&access_type=offline`;
  res.redirect(googleAuthURL);
});
  

app.get('/auth/google/callback', async (req, res) => {
  const { code } = req.query;

  try {
    // Exchange the code for an access token
    const { tokens } = await googleClient.getToken({
      code: code,
      redirect_uri: "https://backend.zain.ovh/auth/google/callback",
    });
    googleClient.setCredentials(tokens);

    // Refresh the access token every 5 minutes
    const refreshAccessToken = async () => {
      try {
        // Use the refresh token to get a new access token
        const { data } = await axios.post('https://accounts.google.com/o/oauth2/token', {
          grant_type: 'refresh_token',
          //client_id: process.env.GOOGLE_CLIENT_ID,
          //client_secret: process.env.GOOGLE_CLIENT_SECRET,
          client_id: "203530020577-ngl05517r4rdn6fv8nudhgaod7p8itrt.apps.googleusercontent.com",
          client_secret: "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",
          refresh_token: tokens.refresh_token,
        });

        // Update the access token
        tokens.access_token = data.access_token;
        googleClient.setCredentials(tokens);
      } catch (error) {
        console.error(error);
      }
    };
    setInterval(refreshAccessToken, 5 * 60 * 1000);

    // Get user info
    const { data } = await axios.get('https://www.googleapis.com/oauth2/v1/userinfo', {
      headers: {
        Authorization: `Bearer ${tokens.access_token}`,
      },
    });

    // Check if user exists in the database
    let userResponse;
    try {
      userResponse = await axios.get(`http://zain.ovh:9090/api/profiles/${data.email}`);
    } catch (error) {
      if (error.response.status === 404) {
        userResponse = { data: null };
      } else {
        throw error;
      }
    }

    // If not, we redirect to the registration form with prefilled fields (email, first name and last name).
    if (!userResponse.data) {
      res.redirect(
        `https://frontend.zain.ovh/register?email=${encodeURIComponent(data.email)}&first_name=${encodeURIComponent(
          data.given_name
        )}&last_name=${encodeURIComponent(data.family_name)}&access_token=${encodeURIComponent(tokens.access_token)}`
      );
    } else {
      // Send access token to parent window with postMessage
      const accessToken = tokens.access_token;
      const message = {
        type: 'access_token',
        data: { accessToken },
      };
      const parentUrl = new URL(req.headers.referer).origin;
      // res.send(`<script>window.opener.postMessage(${JSON.stringify(message)}, 'https://frontend.zain.ovh/'); window.close();</script>`);
      res.send(`<script>window.parent.postMessage(${JSON.stringify(message)}, ${parentUrl}'); window.close();</script>`);
    }
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Authentication failed' });
  }
});

app.post('/register', async (req, res) => {
  const { email, firstName, lastName, birthDate, phoneNumber, gender, registrationDate } = req.body;

  try {
    // Send a POST request to the /profiles endpoint to create a new profile
    const result = await axios.post(`http://zain.ovh:9090/api/profiles`, {
      email,
      firstName,
      lastName,
      birthDate,
      phoneNumber,
      gender,
      registrationDate
    });

    res.status(201).json(result.data);
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Registration failed' });
  }
});

app.get('/auth/refresh-token', async (req, res) => {
  try {
    // Get the current access token
    const { credentials } = googleClient;
    const currentAccessToken = credentials.access_token;

    // Send the access token to the client
    res.status(200).json({ accessToken: currentAccessToken });
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Failed to refresh access token' });
  }
});

// app.post('/register', async (req, res) => {
//   const { email, firstName, lastName, birthDate, phoneNumber, gender } = req.body;

//   try {
//     // Insert a new user record into the 'profiles' table
//     const result = await db.query(`
//       INSERT INTO profile(email, first_name, last_name, birth_date, phone_number, gender)
//       VALUES($1, $2, $3, $4, $5, $6)
//       RETURNING *
//     `, [email, firstName, lastName, birthDate, phoneNumber, gender]);

//     res.status(201).json(result.rows[0]);
//   } catch (error) {
//     console.error(error);
//     res.status(500).json({ error: 'Registration failed' });
//   }
// });


// frontend calls this endpoint with the access token in the header of the request to check token's validity
app.post('/checkToken', async (req, res) => {
  const authHeader = req.headers.authorization;

  // Check if Authorization header exists
  if (!authHeader) {
    return res.status(401).json({ message: 'No access token provided' });
  }

  // Extract token from Authorization header
  const token = authHeader.split(' ')[1];

  try {
    // Verify the token signature and decode its payload
    const decodedToken = jwt.verify(token, secretKey);

    // // Check if the token belongs to the right user (you could check this against your database)
    // if (decodedToken.userId !== req.user.id) {
    //   return res.status(401).json({ message: 'Unauthorized' });
    // }

    // Check if the token is still valid (i.e., has not expired)
    if (decodedToken.exp < Date.now() / 1000) {
      return res.status(401).json({ message: 'Token has expired' });
    }

    // Token is valid
    return res.status(200).json({ message: 'Token is valid' });
  } catch (error) {
    console.error(error);
    return res.status(401).json({ message: 'Token is invalid' });
  }
});
  
const PORT = process.env.PORT || 3001;
app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}`);
});
