Celemo backend install:

1. Clone project.
2. In celemo\src\resources create a file called Application properties.
3. Add the following lines to the application.properties:

{your value} is a placeholder for sensitive information

spring.data.mongodb.uri = {your value}
spring.data.mongodb.database = {your value}
spring.data.mongodb.auto-index-creation=true

jwtSecret = {your value}
jwtExpirationMs = {your value}
jwtCookieName = {your value}
server.error.include-message=ALWAYS

4. 
