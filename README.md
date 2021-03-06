# batch-create-users

A simple project to run jobs using spring batch, this job read users from postgres database and sign in as students to semester.

## Configuration
Two profiles are available, spring's default and "pgsql". the default's one run at memory database. the second one run at postgresql database.

## URL'S
 - GET localhost:9443/applications - Spring Batch Lightmin
 - POST localhost:9443/api/v1/create/person/{number} - endpoint to create persons to sign in

## Getting Started

This project show features which a job configuration must have:

- Spring batch lightmin as tool for manage jobs

### Prerequisites

You will need a java version installed (11 or later) and a IDE to run locally. (STS or Eclipse EE).

### Installing

I'll give you a hand a tell how to install this project on you own machine.

First, clone the project to your local storage.

Then,

```
After unzip on a directory of your choice, open your favorite java IDE and import the project as maven project
```

Finally,

```
Run the application as spring boot
```

End with an example of getting some data out of the system or using it for a little demo

## Built With

- [Spring Boot](https://spring.io/projects/spring-boot) - The web framework used
- [Spring Batch](https://spring.io/projects/spring-batch) - The Batch framework used
- [Spring Batch lightmin](https://github.com/tuxdevelop/spring-batch-lightmin) - To jobs Management tool 
- [Maven](https://maven.apache.org/) - Dependency Management

## License

This project is licensed under the MIT License
