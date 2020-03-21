FROM node:12.13.1-alpine

COPY . .
RUN npm install && \
    npm run build

CMD ["node", "server.js"]
