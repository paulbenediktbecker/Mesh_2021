# Backend service for e01
This is the backend service for our platform.

# Tech
The backend service is build with the Java Spring framework.
Currently it does provide the tech to add addresses and parkinglots for renting.
Our client fetches the backend data using our API.

## Concept
The application distinguishes between `Addresses` and `Parkinglots`. Each address can have multiple parkinglots - up to 3 at the moment (set custom with property `application.parking.parkinglots.maxnumber`).

## API
Once the service is started, the API can be reached on `localhost:8080/parking/address`
Our API provides an total of 14 endpoints for adding addresses / parkinglots, changing them or removing them.
All endpoints can be seen and tested using the OpenAPI/Swagger-Documentation that can be reached at `localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/` once the application runs.

# Building and running the application
This application requires Java 11 or newer to run.
Simple build the application using `Maven clean package`. Drop the generated `SmartEnv-x.x.x.jar` in the `/target/` folder to an directory of your chosing and launch the app using `java -jar SmartEnv-x.x.x.jar`.

