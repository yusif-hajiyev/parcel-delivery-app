# Parcel Delivery App

 # Database
 I use postgre as database
 
 # Container technology
 For to start services was used Docker Compose.
 Please run the run.sh file to start and test the applications.
 
 # Microservices
 ms-auth(for Authentication and Authorization),
 ms-user(for Create user and other user operation),
 ms-parcel(for parcel operation),
 ms-api-gateway(for 3 microservices is proxy).

 # Swagger
 Swagger urls:
 http://localhost:8080/ms-auth/swagger-ui.html
 http://localhost:8080/ms-user/swagger-ui.html
 http://localhost:8080/ms-parcel/swagger-ui.html

 # JWT
 when you login with auth endpoint to system then return token,
 This token will sended in all request as header parameter

 # Unit test
 I used spock framework for unit test
 
