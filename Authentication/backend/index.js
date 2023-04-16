const express = require('express');
const cors = require('cors');
const dotenv = require('dotenv');
const { OAuth2Client } = require('google-auth-library');
const axios = require('axios');
const jwt = require('jsonwebtoken');


dotenv.config();

const app = express();

app.use(express.json());

const googleClient = new OAuth2Client(process.env.GOOGLE_CLIENT_ID, process.env.GOOGLE_CLIENT_SECRET);

app.use(cors('*'));

app.get('/auth/google', (req, res) => {
  const googleAuthURL = `https://accounts.google.com/o/oauth2/v2/auth?client_id=${process.env.GOOGLE_CLIENT_ID}&redirect_uri=${process.env.GOOGLE_REDIRECT_URI}&response_type=code&scope=profile%20email&access_type=offline`;
  res.redirect(googleAuthURL);
});
  

app.get('/auth/google/callback', async (req, res) => {
  const { code } = req.query;

  try {
    // Exchange the code for an access token
    const { tokens } = await googleClient.getToken({
      code: code,
      redirect_uri: process.env.GOOGLE_REDIRECT_URI,
    });
    googleClient.setCredentials(tokens);

    // Refresh the access token every 5 minutes
    const refreshAccessToken = async () => {
      try {
        // Use the refresh token to get a new access token
        const { data } = await axios.post('https://accounts.google.com/o/oauth2/token', {
          grant_type: 'refresh_token',
          client_id: clientId,
          client_secret: secretKey,
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
        `${process.env.REACT_APP_FRONTEND_URL}/register?email=${encodeURIComponent(data.email)}&first_name=${encodeURIComponent(
          data.given_name
        )}&last_name=${encodeURIComponent(data.family_name)}&access_token=${encodeURIComponent(tokens.access_token)}`
      );
    } else {
      // Send access token to parent window with postMessage
      const access_token = tokens.access_token;
      const message = {
        type: 'access_token',
        data: { access_token, email: data.email },
      };
      console.log(req.headers.referer);
      const parentUrl = process.env.REACT_APP_FRONTEND_URL

      res.send(`<script>window.opener.postMessage(${JSON.stringify(message)}, '${parentUrl}'); window.close();</script>`);
    }
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Authentication failed', error: error });
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
    // Extract the access token from the Authorization header
    const authHeader = req.headers.authorization;
    if (!authHeader) {
      return res.status(401).json({ error: 'No access token provided' });
    }
    const token = authHeader.split(' ')[1];

    // Verify the access token using the /checkToken endpoint
    const { data } = await axios.post('http://localhost:3001/checkToken', null, {
      headers: { 'Authorization': `Bearer ${token}` }
    });

    if (!data) {
      // Handle the case when the data object is null or undefined
      return res.status(500).json({ error: 'Failed to verify access token' });
    }

    // If the access token is valid, get the current access token and send it to the client
    const { credentials } = googleClient;
    const currentAccessToken = credentials.access_token;
    res.status(200).json({ access_token: currentAccessToken });
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Failed to refresh access token' });
  }
});

app.post('/checkToken', async (req, res) => {
  const authHeader = req.headers.authorization;

  // Check if Authorization header exists
  if (!authHeader) {
    return res.status(401).json({ message: 'No access token provided' });
  }

  // Extract token from Authorization header
  const token = authHeader.split(' ')[1];

  try {
    // Verify the token using Google's token validation endpoint
    const { data } = await axios.post('https://oauth2.googleapis.com/tokeninfo', {
      access_token: token,
    });

    // Check the audience (aud claim) to ensure it matches your client ID
    const clientId = process.env.GOOGLE_CLIENT_ID; // Replace with your Google Client ID
    if (data.aud !== clientId) {
      return res.status(401).json({ message: 'Invalid audience' });
    }

    // Token is valid
    return res.status(200).json({ message: 'Token is valid' });
  } catch (error) {
    console.error(error);
    return res.status(401).json({ message: `Token is invalid:${token}` });
  }
});
const PORT = process.env.PORT || 3001;
app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}`);
});
