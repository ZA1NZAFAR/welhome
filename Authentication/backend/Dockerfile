FROM node:latest as node
WORKDIR /app
COPY . .
RUN npm install

FROM node as production
EXPOSE 3001
CMD ["npm", "start"]