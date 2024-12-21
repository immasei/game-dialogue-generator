# Game Dialogue Generator

A product of teamwork, soon to be revamped.

Status: Paused

## Table of Contents
**[Tech Stack](#techstack)**<br>
**[How to run](#how-to-run)**<br>
**[Working Functionalities](#working-functionalities)**<br>
**[References](#references)**<br>
**[Acknowledgement](#acknowledgement)**<br>

### Techstack

|            | Current                 | 
| :--------  | :-----------------------| 
| Frontend   | HTML/ CSS               |
| Backend    | Java Spring Boot    	   | 
| Database   | MySQL						  	   |

### How to run

- Go to `application.properties` under `main/java/resources`
- Insert mysql password `spring.datasource.password`
- Ensure MySql, Java 17.x, and Gradle 8.x is installed
- And fill in all of your api keys
- Run
  ```
    gradle clean build bootrun
  ```
- Visit `localhost:8080`


### Working Functionalities

```
  Besides login/signup page, all endpoints requires authentication (ie logged in users)
```

- `/`: Sign up, Login, Logout

- Theme switch: light mode/ dark mode

    - After logged, once switch theme, it will stay in that theme until user closes browser.

- `/home`: Multi-scenario dialogue generation

    - **Prompt creation** (a form)

        - Dialogue can be generated in **chosen language** (limited options)

    - **Form validator**: all fields must be filled

    - **Word count and Word limit**

    - **Generate**:

        - **External Frontend API**: random loader icon from [DiceBear API](https://www.dicebear.com/)

        - **AI API**: prompt is sent to OpenAI using [OpenAI API](https://platform.openai.com/docs/overview) to generate dialogue

        - Once successful, redirect to `/dialogue/{id}`


- `/dialogue/{id}`: Generated Dialogue
    ```
      can also be accessed via `/archive`
      generated dialogue by default is `Readonly-mode`
      cannot access if not the author
      if not author, show /archive
    ```

    - **Edit**: change to `Edit-mode`

    - **Save**: save changes

    - **Go to**: visit `/prompt/{id}`

    - **Export As**: `pdf`, `txt`, `json`, `pastebin`

        - **External Backend API**: export as `pastebin` will upload dialogue to Pastebin using [Pastebin API](https://pastebin.com/doc_api)

    - **Copy to Clipboard**

    - **Text to Speech**: using [AWS Polly Voice API](https://docs.aws.amazon.com/polly/latest/dg/API_Reference.html)
        - works with multiple languages

- `/dialogue/{id}`: Past Prompt
    ```
      can also be accessed via `/archive`
      cannot access if not the author
      if not author, show /archive
    ```

    - Prompt can be **edited**, but it won't be saved until `Save`

    - **Save**: save changes

    - **Regenerate**: Use current prompt to generate new dialogue

        - Once successful, redirect to `/dialogue/{id}`

    - **Go to**: visit `/dialogue/{id}`

- `/archive`: Show history of all generated prompts/dialogues of current user

    - **Search filter**

    - **Redirection**:

        - Press link under `Prompt` to visit `/prompt/{id}`

        - Press link under `Dialogue` to visit `/dialogue/{id}`


### References

#### Frontend

original idea for login/signup and theme switch (modified enough) https://codepen.io/oliviale/pen/GbmyYx?fbclid=IwY2xjawFhtahleHRuA2FlbQIxMAABHc25h0OC2oKJgmixFtmCRAcTs8pKfdc4kdRYgUT3lhzcG_X-z3MB6rPw_A_aem_zv7_ZxKDOQgQt4_hNnFF7w

original idea for sidebar (mini modification but it's easy-understandable html css) https://www.youtube.com/watch?v=neb7WcDixoY

state of theme switch https://stackoverflow.com/questions/24278494/jquery-if-checkbox-is-checked-bootstrap-switch

set input checked https://stackoverflow.com/questions/16906018/bootstrap-doesnt-use-checked-attribute-of-checkbox

bootstrap 5 https://getbootstrap.com/docs/5.0/getting-started/introduction/

sidebar footer https://stackoverflow.com/questions/76061838/make-footer-not-a-direct-child-of-body-stick-to-bottom-in-flexbox-based-layout

font awesome icon https://stackoverflow.com/questions/71146319/how-to-use-font-awesome-icons-in-html https://fontawesome.com/search?q=save&o=r&m=free&s=solid&f=classic%2Csharp

loader icon api https://www.dicebear.com/

fetch https://www.w3schools.com/jsref/api_fetch.asp?gidzl=EFrj2Nz0n0auvsaDUrR02p_hR0jW1y4B8xXeN6i3nGuglZq4EWV3L7ZjEWOnNSS1UELeLsHnYPmpUaF83W https://stackoverflow.com/questions/25515936/perform-curl-request-in-javascript

random seed https://stackoverflow.com/questions/20728783/shortest-code-to-get-random-string-of-numbers-and-letters https://www.youtube.com/shorts/TqnC96-nXAA

loader effect https://css3loaders.huckbit.com/loader/3

form validator bootstrap+geeks https://www.geeksforgeeks.org/bootstrap-5-validation-tooltips/

pass var from spring be to js fe https://stackoverflow.com/questions/25687816/setting-up-a-javascript-variable-from-spring-model-by-using-thymeleaf

textarea auto grow https://stackoverflow.com/questions/17772260/textarea-auto-height

thymeleaf access var via html https://www.thymeleaf.org/doc/articles/springmvcaccessdata.html

controller request forward https://stackoverflow.com/questions/31796952/forward-request-to-another-controller-in-spring-mvc

thymeleaf selected option from model https://stackoverflow.com/questions/50777036/how-to-select-an-option-in-a-dropdown-based-on-a-value-on-the-model-using-thymel

thymeleaf href https://stackoverflow.com/questions/33753975/thymeleaf-using-path-variables-to-thhref https://stackoverflow.com/questions/56060155/thymeleaf-button-call-url-with-parameters-in-new-tab/56061657

fe html jquery js https://www.w3schools.com/

save as json/txt https://stackoverflow.com/questions/34156282/how-do-i-save-json-to-local-text-file

save as pdf https://github.com/eKoopmans/html2pdf.js https://stackoverflow.com/questions/61669405/forcing-a-function-to-wait-until-another-function-is-complete

word count/lim per textarea https://stackoverflow.com/questions/25148479/how-can-i-limit-words-not-characters-in-textarea-html https://stackoverflow.com/questions/2823733/textarea-onchange-detection https://frontendinterviewquestions.medium.com/how-to-get-count-words-in-string-using-javascript-93baf76beb9b

bootstrap icon https://icons.getbootstrap.com/

#### Backend
jpa qry https://www.bezkoder.com/jpa-filter-by-multiple-columns/ https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html#jpa.query-methods.named-queries.annotation-based-configuration

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

write enums into database https://bootify.io/next-steps/load-initial-data-in-spring-boot.html
https://dev.to/noelopez/spring-rest-working-with-enums-ma

polly voice api https://docs.aws.amazon.com/polly/latest/dg/standard-voices.html https://stackoverflow.com/questions/61131383/how-to-enable-neural-text-to-speech-ntts-in-java-using-amazon-polly https://stackoverflow.com/questions/51184130/aws-polly-java-example https://mvnrepository.com/artifact/com.amazonaws/aws-java-sdk-polly/1.12.777 https://github.com/gopikrishhnaChokkalamani/springboot-amazon-polly

obj mapper https://medium.com/@bectorhimanshu/using-dto-data-transfer-object-projection-mapping-entities-to-dtos-and-vice-versa-in-java-a7a9fe6b50a4 https://stackoverflow.com/questions/65807229/how-can-i-use-the-modelmapper-in-spring

gradle exclude https://stackoverflow.com/questions/29887805/filter-jacoco-coverage-reports-with-gradle

### Acknowledgement
This project was joinly built by (USYD) ELEC5619 S2 2024 (Group 3), with contributions from the following members:

|                      | Username                | Role             | 
| :--------------------| :-----------------------| :--------------- |
| Lan Linh Nguyen (me) | immasei (lngu8896)      | FE & BE          |
| Megan Gock Kwai      | mgoc2312  	             | BE & Testing     |
| George Zhou          | yzho5574 						   | BE (OpenAI)      |
| Gavin Qiao (Gavin)   | kqia8770  						   | FE & Testing     |
| Xinyi He (Cecilia)   | xihe0208  						   | FE               |
