# tenpo-challenge

# Install Docker

### Windows and macOS

Docker Compose is included in
[Docker Desktop](https://www.docker.com/products/docker-desktop)
for Windows and macOS.

### Linux

```
apt  install docker-compose
```
# Clone the repository

```
git clone https://github.com/julyy123/tenpo-challenge.git
```

# Install project using maven

```
mvn install
```


# Start the project with docker-compose

```
docker-compose up --build
```

# Import postman collection

* Open postman
* Go to Collections -> Import
* Select `/postman-collections/Tenpo challenge.postman_collection.json` file
* Execute endpoints to test the api
 * (Note: To execute `sum` and `Get Sum History` endpoints you need to authenticate first and use the token as a bearer token)