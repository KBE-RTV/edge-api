# Edge Api

## General information: How to get the entire Microservice project started
To get the whole application started, Docker needs to be installed.
After cloning the sub-repositories and opening terminals in each of the projects, run
```
docker-compose up --build
```
to get the services started.
This has to be done in the following order:
1. Warehouse
2. Rabbit MQ
3. Price Service
4. Product Service
5. Currency Service
6. Gateway
7. Frontend

## API Gateway: What needs to be done additionally, to get the API Gateway started correctly
We encountered the following problem concerning keycloak and docker:
<https://jtuto.com/solved-keycloak-invalid-token-issuer-in-docker/?amp=1>

One possible work around is to set ```127.0.0.1 keycloak``` in the ```/etc/hosts``` file.

Furthermore, after starting the app as described above, a keycloak user has to be added in the "KBERTV" Realm, by going to ```http://localhost:8080```, logging into the Administration console with username ```admin``` and password ```admin```, selecting the Realm ```KBERTV```, going to ```Users```, and adding a user and its password. 
