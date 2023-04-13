FROM node:latest as base
WORKDIR /app
COPY package*.json .
RUN npm install

FROM base as build
COPY index.ts .
COPY tsconfig.json .
COPY swagger.js .
ARG DOMAIN
ARG PORT
RUN npm run build

FROM base as release
COPY --from=build /app/package*.json .
COPY --from=build /app/swagger-output.json .
COPY --from=build /app/index.js .
RUN npm i --omit-dev
EXPOSE 9095
CMD ["node", "index.js"]
