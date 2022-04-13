# tenpo-challenge

# Install Docker

### Windows and macOS

Docker Compose is included in
[Docker Desktop](https://www.docker.com/products/docker-desktop)
for Windows and macOS.

### Linux

You can download Docker Compose binaries from the
[release page](https://github.com/docker/compose/releases) on this repository.

Rename the relevant binary for your OS to `docker-compose` and copy it to `$HOME/.docker/cli-plugins`

Or copy it into one of these folders for installing it system-wide:

* `/usr/local/lib/docker/cli-plugins` OR `/usr/local/libexec/docker/cli-plugins`
* `/usr/lib/docker/cli-plugins` OR `/usr/libexec/docker/cli-plugins`

(might require to make the downloaded file executable with `chmod +x`)

# Clone the repository

```
git clone https://github.com/julyy123/tenpo-challenge.git
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