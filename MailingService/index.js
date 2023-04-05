const nodemailer = require('nodemailer');
const bodyParser = require('body-parser');
const express = require('express');
const HTTPStatus = require('http-status');

if (process.env.NODE_ENV !== 'production') {
  require('dotenv').config();
}

const transporter = nodemailer.createTransport({
  host: process.env.SMTP_HOST,
  port: process.env.SMTP_PORT,
  secure: false,
  requireTLS: false,
  logger: true
});

const app = express();
const router = express.Router();
const baseUrl = process.env.BASE_URL || 'http://localhost:5000';
router.post('/', async (req, res) => {
  const { subject, body, recipients} = req.body;

  const mailOptions = {
    from: process.env.FROM_EMAIL,
    to: recipients,
    subject,
    html: `Hello,<br><br>${body}<br><br>Best regards,<br>Welhome Team`
  };

  transporter.sendMail(mailOptions, (error, info) => {
    if (error) {
      console.error(error);
    }
  });

  res.status(HTTPStatus.OK).send();
});


app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(baseUrl, router);
