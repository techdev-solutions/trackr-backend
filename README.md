trackr backend
==============

What is it?
-------------
trackr is an application to track petty much everything that is going on in your company.
Keep track of vacations, sick days, invoices and many more.

trackr comes with a Java-based backend and a [frontend](https://github.com/techdev-solutions/trackr-frontend) written in AngularJS. This project is the Java/Spring based
backend, a stateless REST API with either OAuth2 or basic authentication.

You can read all about trackr in our developer blog:

* [Architecture and Backend](http://blog.techdev.de/trackr-an-angularjs-app-with-a-java-8-backend-part-i/)
* [Testing](http://blog.techdev.de/testing-a-secured-spring-data-rest-service-with-java-8-and-mockmvc/)
* [Mail Approvals with Spring Integration](http://blog.techdev.de/mail-approvals-with-spring-integration/)
* [Frontend](http://blog.techdev.de/trackr-an-angularjs-app-with-a-java-8-backend-part-ii/)
* [File Downloads with AngularJS](http://blog.techdev.de/an-angularjs-directive-to-download-pdf-files/)
* [Processes and Tools](http://blog.techdev.de/trackr-an-angularjs-app-with-a-java-8-backend-part-iii/)

For the API documentation just go [here](http://techdev-solutions.github.io/trackr-api-documentation/getting_started.html).
There is also a [Vagrant](https://www.vagrantup.com/) project building the whole application over [here](https://github.com/techdev-solutions/trackr-vagrant).

How to start
------------
If you just want to mess around with the API a bit the default configuration is very sensible and has no external dependencies (well, except Java).

If you have gradle, just run

    gradle run

If you don't have gradle and want to use the wrapper run

    ./gradlew run
    # or
    gradlew.bat run

If you want to start from your IDE, i.e. for debugging open the class `Trackr` and start the main method.

To verify it works you can use curl. The users don't have a password in this configuration, so just press enter when curl asks for one. If you don't like the usernames
change them in import.sql.

    curl --user moritz.schulze@techdev.de localhost:8080

The default config uses port 8080, if that is used on your system you can add

    server:
        port: $port

to the top of the application.yaml and choose a port that you want for `$port`.

Profiles
--------
trackr has a lot of Spring profiles to add/switch features.

| profile            | description                                                | notes                                                                         |
|--------------------|------------------------------------------------------------|-------------------------------------------------------------------------------|
| in-memory-database | uses a H2 database, creates the schema und runs import.sql | excluse with real-database                                                    |
| real-database      | uses a configurable database, executes flyway              | exclusive with in-memory-database                                             |
| oauth              | protects the API as a OAuth2 resource server               | when off, HTTP basic authentication is on. Database for OAuth2 tokens needed. |
| granular-security  | roles and per endpoint security                            |                                                                               |
| gmail              | sends mail with Gmail and enables mail receiving           | when off, does not receive mails and uses a logging mail sender.              |
| dev, prod          | just textual properties for different environments         |                                                                               |

Take a look in the application.yaml to see what properties these profiles need.

The default profiles are `in-memory-database,dev,granular-security`. If you want to use other profiles, there are several possible ways.
1. You can change the `spring.profiles.acitve` value in application.yaml
2. If you use `gradle run` you can prepend (example) `SPRING_PROFILES_ACTIVE=dev,gmail,real-database`. You can also use this to overwrite e.g. the port with `SERVER_PORT=8000`.
3. If you run from your IDE, you can add `--spring.profiles.active=dev,gmail,real-database` as program arguments to the run configuration.

Please refer to the [Spring Boot Reference](http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/) for more information.

### The oauth profile
The oauth profile marks the trackr backend as a OAuth2 resource server, that means access is only possible with a valid access token issued by an authorization server. We use a
JDBC token store, so valid tokens need to be put there. Please take a look at our (soon to be open sourced) techdev portal to see how we do this.

Switching this profile off is currently only meant for test purposes and has no real authentication method behind it. But it is much easier for local development.

### The granular-security profile
When this is not selected, to access the API the user needs to be authenticated. With granular security the access to some endpoints depend on the role of the user or even the
id of the user. In trackr, the id of a user is the email address of the belonging employee.

When the oauth profile is switched off, all users have the role ROLE_ADMIN. When oauth is on, the roles must be stored in the access token.

Take a look at the `@PreAuthorize` and `@PostAuthorize` annotations in the code to see what this will activate.

How to build
------------
Just run

    gradle build

(or use the wrapper if you don't have gradle installed). The JAR file will be in `build/libs` and can just be run with `java -jar`. The application.yaml file has to be in the
working directory where the `java` command was issued.