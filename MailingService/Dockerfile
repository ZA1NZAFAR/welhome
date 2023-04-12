FROM node:latest as base
WORKDIR /app
COPY package*.json .
RUN npm install

FROM base as build
COPY index.ts .
COPY tsconfig.json .
RUN npm run build

FROM base as release
COPY --from=build /app/dist .
RUN npm prune --production
EXPOSE 3005
CMD ["node", "index.js"]
