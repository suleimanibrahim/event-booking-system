# event-booking-system

 Event booking app 
 swagger link: http://localhost:8081/swagger-ui/index.html#/

 Steps to run the app
 1. clone the repository
 2. add postgreSQL connection details by creating the db
 3. Run the app and access the swagger link above
 4. test All the endpoints

Rate Limiting: I use Spring Boot Resilience4j to limit the number of requests.
Testing: i used JUnit and Mockito for unit tests, and Spring Boot Test for integration tests.

NOTE #
1. Admin need to signup and login then create event before user can book an event
2. only USER with role Admin can access admin endpoints
3. only USER with role User can accesss users endpoints
4. User need to also register, login, before booking, or cancel booking
5. also only admin can delete or update an event

I choosed the event booking sytem using springboot because i need to write Java code because i already have most of the task asked using node.js and i dont want to just push those one's instead i preper to work on new one. but i can also attempt all of them using node.js also

