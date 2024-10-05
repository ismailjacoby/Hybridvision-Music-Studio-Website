FROM node:20.11.1-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
#Compatibility Compiler
RUN npx ngcc --properties es2023 browser module main --first-only --create-ivy-entry-points
COPY . .
RUN npm run build


FROM nginx:latest
COPY --from=build /app/dist/maker-hub-front-end/browser /usr/share/nginx/html
COPY /nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80



