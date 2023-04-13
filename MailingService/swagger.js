if (process.env.NODE_ENV !== 'production') {
  require('dotenv').config();
}

const options = {
  openapi: '3.0.0'
}

const swaggerAutogen = require('swagger-autogen')(options);


const doc = {
  info: {
    version: '1.0.0',      // by default: '1.0.0'
    title: 'Mailing Service',        // by default: 'REST API'
    description: 'Mailing Service for Welhome Application',  // by default: ''
  },
  host: `${process.env.DOMAIN}:${process.env.PORT}`,
  basePath: '/',
  securityDefinitions: {
    bearerAuth: {
        type: 'http',
        scheme: 'bearer',
        bearerFormat: 'oAuth2'
    }
  },
  schemes: ['http'],
  definitions: {
    Mail: {
      subject: 'Hello, world!',
      htmlBody: 'This is the body of the email<br>You can use <b>html</b> tags here',
      recipients: [
        'john.doe@gmail.com',
        'jane.del@gmail.com'
      ]
    },
    Message: {
      message: 'Mail sent'
    },
    Error: {
      error: 'Error message'
    }
  }
};

const outputFile = './swagger-output.json';
const endpointsFiles = ['./index.ts'];

swaggerAutogen(outputFile, endpointsFiles, doc);