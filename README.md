# FriGroup3

### Reminder

- please follow the commit message guide on Canvas
  - `feat`: A new feature
  - `fix`: A bug fix
  - `docs`: Documentation changes
  - `style`: Code style changes (non-functional)
  - `refactor`: Code refactoring (neither adding features nor fixing bugs)
  - `test`: Adding tests
  - `chore`: Changes to the build process or auxiliary tools
  
- if the report must have smt like 'good repo practice` (i havent checked) then
  ```
    Do not push application.properties with your spring.datasource.password
  ```

### Current features
  - **security**
    - csrf disabled (check `config/`)
    - only some api endpoints enabled without authenticated
      - if you add more endpoints, edit this file accordingly
  - **frontend**: 
    - null
  - **backend**:
    - login/ signup with hashed password (bcrypt)
    - request body validation (check `dto/`)
    - custom http response format (check `handler/`)
      - status code
      - message/ aka error (from request body validation)
      - data (ie user object)
      - example
      
        ```
            {
               "data": {
                    "username": "shin",
                    "userId": 9
               },
               "message": "Login OK",
               "status": 200
            }
        ```
        ```
            {
               "data": null,
               "message": [
                     "Username must be between 1 and 20 characters long",
                     "Password must be between 8 and 20 characters long",
                     "Username is required",
                     "Password is required"
               ],
               "status": 400
            }
        ```
    
### Setup
  - go to `application.properties` under `main/java/resources`
  - insert mysql password `spring.datasource.password`
  - you only need the account, you dont need to create a db beforehand
  - file structure follow [this](https://malshani-wijekoon.medium.com/spring-boot-folder-structure-best-practices-18ef78a81819)
    - I only make up the `handler/` folder to contain my custom http response

### Test API
  - install `postman`
    - post 
      - `/signup`
      - `/login`
    
    - get
      - `/users`: list all users
      
### References

mysql & java spring boot
https://spring.io/guides/gs/accessing-data-mysql

spring boot file structure best practice
https://malshani-wijekoon.medium.com/spring-boot-folder-structure-best-practices-18ef78a81819

mysql create new user
https://stackoverflow.com/questions/1720244/create-new-user-in-mysql-and-give-it-full-access-to-one-database

create db if not exists
https://stackoverflow.com/questions/26881739/unable-to-get-spring-boot-to-automatically-create-database-schema

spring pipeline https://medium.com/@piyumisudusinghe/error-handling-in-rest-api-4fa7bdbe379a

bcrypt https://stackoverflow.com/questions/55548290/using-bcrypt-in-spring https://stackoverflow.com/questions/54597495/how-to-compare-a-password-text-with-the-bcrypt-hashes

api return https://www.devglan.com/spring-boot/spring-boot-jpa-hibernate-login https://medium.com/@kodefyi/spring-boot-excercise-custom-json-response-with-responseentity-2fd183514b64

security https://jstobigdata.com/spring-security/spring-security-form-login-with-spring-boot/#toc_2_Maven_Dependencies

dto https://www.devglan.com/spring-boot/spring-boot-jpa-hibernate-login

dto validation https://medium.com/paysafe-bulgaria/springboot-dto-validation-good-practices-and-breakdown-fee69277b3b0

validation exception handler https://stackoverflow.com/questions/59952013/how-to-override-handlemethodargumentnotvalid-properly-in-spring-boot https://medium.com/@tericcabrel/validate-request-body-and-parameter-in-spring-boot-53ca77f97fe9 https://stackoverflow.com/questions/75493219/responseentityexceptionhandler-error-with-new-spring-boot-version/75493408