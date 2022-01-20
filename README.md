# Parcel Delivery App

 # Database
 I use postgre as database
 
 # Container technology
 For to start services was used Docker Compose.<br>
 Please run the run.sh file to start and test the applications.<br>
 
 # Microservices
 ms-auth(for Authentication and Authorization)<br>
 ms-user(for Create user and other user operation)<br>
 ms-parcel(for parcel operation)<br>
 ms-api-gateway(for 3 microservices is proxy)<br>

 # Swagger
 Swagger urls: <br>
 http://localhost:8080/ms-auth/swagger-ui.html <br>
 http://localhost:8080/ms-user/swagger-ui.html <br>
 http://localhost:8080/ms-parcel/swagger-ui.html <br>

 # JWT
 when you login with auth endpoint to system then return token,
 This token will sended in all request as header parameter

 # Unit test
 I used spock framework for unit test
 
