## IDATT2105 Project - Backend


### About

The backend project is part of the IDATT2105 Semester Project 2024. It serves as the server-side component for the full-stack web application aimed at quiz management. For the frontend component, please refer to [this link](https://github.com/ViktorGrev/IDATT2105_project).

### Prerequisites
Before running the backend project, ensure you have the following installed:

- Java (v17 or v21)
- Maven
- MySQL (v8) or H2 database
- Docker

### Installation

To install and run the backend project:

1. Clone this repository to your local machine.
2. Set up a database and update the database configuration in the `application.properties` file. (See the below point for more information)
3. Build the project using Maven: `mvn clean install`.
4. Run the application: `mvn spring-boot:run`.

#### Persistent storage

This project uses Java Persistence API (JPA) to access data sources. It is recommended to run a MySQL image in a docker container by following these steps:
1. Install [Docker](https://www.docker.com/get-started/)
2. Run a MySQL container using `docker run -e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql:latest`
3. Log in to the MySQL container and create a database
4. Navigate to [application.properties](src/main/resources/application.properties) and make sure that `spring.datasource.url` is defined on the following format: `jdbc:mysql://localhost:3306/{database}`. 

### CI

This project utilizes GitHub Actions for CI. The workflow defined in the [.github/workflows](.github/workflows) directory ensures that the code is compiled, tested, and built automatically on each push to the main branch.

### License

Distributed under the MIT license. See [license](license.txt) for more.

### Contact

Henrik Berg - henridb@stud.ntnu.no \
Victor Kaste - vekaste@stud.ntnu.no \
Viktor Grevskott - viktorgg@stud.ntnu.no