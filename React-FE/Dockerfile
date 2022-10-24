FROM node:14.18.3-alpine as base
WORKDIR /app
ENV PATH /app/node_modules/.bin:$PATH
COPY package.json package-lock.json ./
RUN npm ci
COPY . .

FROM base as build
ARG env
RUN if [ -z "$env" ] ; then \
    npm run build; \
  else \
    npm run build:$env; \
  fi

FROM nginx:stable-alpine as release
RUN apk add --no-cache jq
COPY --from=build /app/build /usr/share/nginx/html
COPY nginx/nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80

COPY set-runtime-env.sh /docker-entrypoint.d
CMD ["nginx", "-g", "daemon off;"]
