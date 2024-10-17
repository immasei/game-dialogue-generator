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
- will be using `.env` in future updates
  ```
    Do not push application.properties with your spring.datasource.password
  ```

### Current features
- **security**
    - csrf disabled (check `config/`)
    - session authentication
        - follow [this](https://medium.com/@ZiaurrahmanAthaya/how-to-create-session-authentication-using-spring-boot-801320adcd26)
        - we may change to [jwt](https://github.com/Erik-Cupsa/Spring-Security-Tutorial/tree/main) in the future
    - only some api endpoints enabled without authenticated
        - you cannot access `/home` without login/ signup
- **frontend**:
    - draft login/signup page, connected to css
- **backend**:
    - login/ signup with hashed password (bcrypt only)
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
    - **Controllers for Dialogue Generation**
        - `OpenAIRequestController` for sending dialogue generation requests to OpenAI and retrieving the output.
        - `OutputMessageController` for managing output messages generated from OpenAI API.

    - **note**
        - we have 2 types of controller
            - `@RestController` (ie AuthController) returns {} like above example
            - `@Controller` returns html view
        - work flow will be
            - frontend using `axios` send http request via `@RestController` that returns `json response` (customisable, see `handler/`)
            - if returning response ok ie 200 201
                - front end do the redirection via `@Controller` that returns `html view`

### Setup
- go to `application.properties` under `main/java/resources`
- insert mysql password `spring.datasource.password`
- you only need the account, you dont need to create a db beforehand
- if you have error for `@Getter`, `@Setter`, try [this](https://stackoverflow.com/questions/17729384/lombok-added-but-getters-and-setters-not-recognized-in-intellij-idea)
- file structure follow [this](https://malshani-wijekoon.medium.com/spring-boot-folder-structure-best-practices-18ef78a81819)
    - I only make up the `handler/` folder to contain my custom http response

### Test API
- via `postman` (install it)
    - post
        - `/signup`
        - `/login`

    - get
        - `/users`: list all users
- via `localhost:8080`

### OpenAIRequestController

The `OpenAIRequestController` is responsible for handling OpenAI API requests. It provides endpoints for creating new requests, retrieving requests by ID, testing the OpenAI API, and deleting requests.

#### Endpoints:

- **POST `/api/openai`**: Create a new OpenAI request and generate dialogue.
    - **Example Request (JSON)**:
      ```json
      {
          "genre": "scifi, fantasy",
          "setting": "Planet of Penacony - the land of dreams",
          "location": "Penacony Grand Hotel",
          "timePeriod": "Day",
          "language": "ENGLISH",
          "plot": "The Trailblazer is attempting to check in at the hotel, but she is not on the invite list.",
          "characterNames": ["Trailblazer", "Hotel Manager"],
          "characterPersonalities": ["curious, slightly dumb", "polite and patient"],
          "characterSpeechFeatures": [
              "refers to herself as the galactic baseballer, likes trash cans, catchphrase: rules are meant to be broken",
              "polite speech, attempting to explain how she cannot let the Trailblazer check in if she is not on the guest list"
          ],
          "depth": 2,
          "width": 2,
          "userId": 1
      }
      ```
- **GET `/api/openai/{id}`**: Retrieve a request by ID.
- **DELETE `/api/openai/{id}`**: Delete a request by ID.
- **GET `/api/openai/test`**: Test OpenAI API call with a mock request.
- **GET `/api/openai/test/string`**: Get raw JSON response string from OpenAI API.

The `POST` endpoint requires a properly formatted `OpenAIRequestDTO` object as input, which defines the settings for the OpenAI API call such as genre, setting, character details, dialogue depth, and width. The service will return an `OutputMessageDTO` containing the generated dialogue.

### OutputMessageController

The `OutputMessageController` is responsible for managing output messages generated from the OpenAI API. It provides CRUD (Create, Retrieve, Update, Delete) operations.

#### Endpoints:

- **POST `/api/outputmessages`**: Create a new output message.
    - **Example Request (JSON)**:
      ```json
      {
          "message": "Generated dialogue content",
          "userId": 1
      }
      ```
- **GET `/api/outputmessages/{id}`**: Get an output message by ID.
    - **Example Request**: `GET /api/outputmessages/1`
- **PUT `/api/outputmessages/{id}`**: Update an output message by ID.
    - **Example Request (JSON)**:
      ```json
      {
          "message": "Updated dialogue content",
          "userId": 1
      }
      ```
- **DELETE `/api/outputmessages/{id}`**: Delete an output message by ID.
    - **Example Request**: `DELETE /api/outputmessages/1`

The `POST`, `PUT`, and `DELETE` endpoints allow for managing messages in the database, while the `GET` endpoint allows for retrieving a single output message by its ID.

### References

#### Frontend

original idea for login/signup and theme switch (modified enough) https://codepen.io/oliviale/pen/GbmyYx?fbclid=IwY2xjawFhtahleHRuA2FlbQIxMAABHc25h0OC2oKJgmixFtmCRAcTs8pKfdc4kdRYgUT3lhzcG_X-z3MB6rPw_A_aem_zv7_ZxKDOQgQt4_hNnFF7w

original idea for sidebar (mini modification but it's easy-understandable html css) https://www.youtube.com/watch?v=neb7WcDixoY

state of theme switch https://stackoverflow.com/questions/24278494/jquery-if-checkbox-is-checked-bootstrap-switch

set input checked https://stackoverflow.com/questions/16906018/bootstrap-doesnt-use-checked-attribute-of-checkbox

bootstrap 5 https://getbootstrap.com/docs/5.0/getting-started/introduction/

sidebar footer https://stackoverflow.com/questions/76061838/make-footer-not-a-direct-child-of-body-stick-to-bottom-in-flexbox-based-layout

font awesome https://stackoverflow.com/questions/71146319/how-to-use-font-awesome-icons-in-html https://fontawesome.com/search?q=save&o=r&m=free&s=solid&f=classic%2Csharp

#### Backend
mysql & java spring boot
https://spring.io/guides/gs/accessing-data-mysql

spring boot file structure best practice
https://malshani-wijekoon.medium.com/spring-boot-folder-structure-best-practices-18ef78a81819

another file structure https://github.com/sidharthsahoodev/user-auth/tree/main/src/main/java/org/example/config

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

thymeleaf base included https://www.thymeleaf.org/doc/tutorials/3.1/usingthymeleaf.html#defining-and-referencing-fragments

axios https://axios-http.com/docs/post_example

jwt and authentication manager https://dev.to/m1guelsb/authentication-and-authorization-with-spring-boot-4m2n

lombok getter setter https://stackoverflow.com/questions/17729384/lombok-added-but-getters-and-setters-not-recognized-in-intellij-idea

authentication manager & jwt https://github.com/Erik-Cupsa/Spring-Security-Tutorial/tree/main

http persist session https://medium.com/@ZiaurrahmanAthaya/how-to-create-session-authentication-using-spring-boot-801320adcd26
