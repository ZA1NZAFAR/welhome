const nodemailer = require('nodemailer');
const bodyParser = require('body-parser');
const express = require('express');
const HTTPStatus = require('http-status');
const swaggerUi = require('swagger-ui-express')
const swaggerFile = require('./swagger-output.json')
const cors = require('cors');

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
app.use(cors('*'));
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
      console.log(`[${new Date().toISOString()}] Authorized request`);
      next();
    } else {
      const data = await response.json();
      console.log(`[${new Date().toISOString()}] Unauthorized request:`, data);
      res.status(HTTPStatus.UNAUTHORIZED).send({ error: 'Unauthorized request' });
    }
  } catch (error) {
    console.error(error);
    res.status(500).send({ error: 'Error' });
  }
  

});
router.post('/mail', async (req:any, res:any) => {
  // #swagger.tags = ['Mail']
  // #swagger.summary = 'Send a mail to some recipients'
  // #swagger.description = 'Send a mail to some recipients'
  /* #swagger.security = [{
    "bearerAuth": []
  }] */
  /* #swagger.requestBody = {
    required: true,
    content: {
      'application/json': {
        schema: {
          $ref: '#/definitions/Mail'
        }
      }
    }
  } */
  /* #swagger.responses[200] = {
    description: 'Email sent',
    schema: {
      $ref: '#/definitions/Message'
    }
  } */
  /* #swagger.responses[401] = {
    description: 'Unauthorized request',
    schema: {
      $ref: '#/definitions/Error'
    }
  } */
  /* #swagger.responses[500] = {
    description: 'Error',
    schema: {
      $ref: '#/definitions/Error'
    }
  } */

  const { subject, htmlBody, recipients} = req.body;

  const mailOptions = {
    from: process.env.USER,
    to: recipients,
    subject,
    html: `Hello,<br><br>${htmlBody}<br><br>Best regards,<br>Welhome Team`
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
app.use('/api', swaggerUi.serve, swaggerUi.setup(swaggerFile))
app.use(router);

app.listen(process.env.PORT || 3005, () => {
  console.log('Server started in port ' + (process.env.PORT || 3005));
});
