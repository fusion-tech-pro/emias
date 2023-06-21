# Emias
## Deployment
To deploy this project:
1. Clone this application from the master branch
```bash
  git clone
```
2. Add .env file to the root of the project and provide following variables
3. Run docker-compose file to run db container and server application
```bash
    docker-compose up -d --build
```
5. Check the running application
```bash
    {server_host}:8001/swagger-ui/index.html#/
```
## Environment Variables
To run this project, you will need to add the following environment variables to your .env file
`POSTGRES_USERNAME`
`POSTGRES_PASSWORD`
`POSTGRES_HOST`
`POSTGRES_PORT`
`POSTGRES_DB_NAME`
example values
```bash
    POSTGRES_USERNAME=postgres
    POSTGRES_PASSWORD=postgres
    POSTGRES_HOST={db_host}
    POSTGRES_PORT=5434
    POSTGRES_DB_NAME=emias_db
```
## Documentation
All necessary documentation related to development process and testing can be found in `docs` directory inside the repo.