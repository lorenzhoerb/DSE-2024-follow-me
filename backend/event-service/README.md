# Event Service

Event services manages events. 
- It receives events from the event queue: `event.exchange` with the routing key `event.message.event.message.#`,
- stores them in the database,
- forwards them to a websocket
  - Websocket Endpoint: `/ws`
  - Websocket Topic: `/topic/events`
  - 
## Environment

The application supports two Maven and Spring profiles: `dev` (development) and `prod` (production).

### Development

For the Maven and Spring Boot profile `dev`, an H2 in-memory database is used.

### Production

 For the Maven and Spring Boot profile `prod`, a PostgreSQL database is used.

## Running the Application

### Development

- Ensure the `dev` Maven and Spring profile is active.
- Inventory Service uses the `api` module. Ensure it is installed in the local package repository by running: `mvn install`

## Deploy

1. Set Maven and Spring profile to `prod` and package the application
   Ensure the API module is installed, then run:
   `mvn package -Pprod`

2. Set the database URL
   Define the database URL with the environment variable:
   `SPRING_DATASOURCE_URL=[db url]`

3. Set the frontend URL for CORS
   Define the frontend URL with the environment variable:
   `APP_FRONTEND_URL=[frontend url]`

4. Build the Docker image
   Run the following command to build the Docker image:
   `docker build -t dse/event-service:latest .`



