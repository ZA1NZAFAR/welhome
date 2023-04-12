const nodemailer = require('nodemailer');
const bodyParser = require('body-parser');
const express = require('express');
const HTTPStatus = require('http-status');

if (process.env.NODE_ENV !== 'production') {
  require('dotenv').config();
}

const configOptions = {
  service: 'gmail',
  auth: {
    user: process.env.USER,
    pass: process.env.PASSWORD
  }
}


const transporter = nodemailer.createTransport(configOptions);
transporter.verify((error:any, success:any) => {
  if (error) {
    console.error(error);
  } else {
    console.log('Server is ready to take our messages');
  }
});
const app = express();
const router = express.Router();

router.use(async (req:any, res:any, next:any) => {
  if (!process.env.AUTH_API) {
    next();
  }
  try {
    const authorization = req.headers.authorization;
    const response = await fetch(process.env.AUTH_API || '', {
      method: 'POST',
      headers: {
        authorization: authorization
      }
    });
    if (response.ok) {
      console.log('Authorized request')
      next();
    } else {
      const data = await response.json();
      console.log('Unauthorized request:', data);
      res.status(HTTPStatus.UNAUTHORIZED).send({ message: 'Unauthorized request' });
    }
  } catch (error) {
    console.error(error);
    res.status(500).send({ message: 'Error' });
  }
  

});
router.post('/mail', async (req:any, res:any) => {
  const { subject, body, recipients} = req.body;

  const mailOptions = {
    from: process.env.USER,
    to: recipients,
    subject,
    html: `Hello,<br><br>${body}<br><br>Best regards,<br>Welhome Team`
  };

  transporter.sendMail(mailOptions, (error:any, info:any) => {
    if (error) {
      console.error(error);
    }
    console.log(`${new Date().toISOString()} - Message sent to ${recipients}`);
  });

  res.status(HTTPStatus.OK).send({ message: 'Email sent' });
});


app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(router);

app.listen(process.env.PORT || 3005, () => {
  console.log('Server started in port ' + (process.env.PORT || 3005));
});
