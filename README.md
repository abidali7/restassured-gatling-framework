# QA Challenge – API Test Automation
This section contains API test automation for the "User Service" as part of QA Challenge.
All tests were automated implemented in Java (JUnit5 + RestAssured) with Allure Reporting for detailed visualization of test execution.

## RestAssured & Gatling Testing Framework
This repository demonstrates a comprehensive API testing framework built using:
* REST Assured for Functional and Regression API testing 
* JUnit 5 as the test runner 
* Allure Reports for detailed execution reporting 
* Gatling for Load and Performance testing

## Prerequisites
Before running the tests, please make sure the following are installed and properly configured:
* Java JDK 17+
* Maven 3.6+

## Functional API Testing (REST Assured + JUnit + Allure)
Key Highlights:
* Covers full CRUD operations for API endpoints 
* Uses data-driven and modular structure under utils/ folder 
* Includes detailed Allure reports with:
  * Custom test names, descriptions, and severity levels 
  * Step annotations and request-response attachments

### Running the Tests

To execute the test, follow the following steps:
1. Run all tests:
    ```bash
   mvn clean test
    ```
2. To run a specific test class:
    ```bash
   mvn clean -Dtest=CreateUserTest test
    ```
3. Once tests complete, results will be generated under:
    ```bash
   tests/target/allure-results
    ```

### Generate & View Allure Report
1. To generate the report
    ```bash
    mvn allure:report
    ```
2. To view the report: it will open allure report in browser
    ```bash
    mvn allure:serve
    ```
3. Report details: The report has been carefully structured, you will find the tests well organized into Epics, Features, Stories, with customized test names, detailed test steps and request–response attachments for each test case.

### Test Scenarios Covered

User API Tests (CRUD), all test cases have been covered for basic flow to validates what API supposed to do:

| Test Case          | Description                                              | Endpoint                                |
| ------------------ | -------------------------------------------------------- | --------------------------------------- |
| **CreateUserTest** | Validates creating a new user                            | `POST /api/users`                       |
| **GetUserTest**    | Validates retrieving all users and fetching a user by ID | `GET /api/users`, `GET /api/users/{id}` |
| **UpdateUserTest** | Updates user information such as name and email          | `PUT /api/users/{id}`                   |
| **DeleteUserTest** | Deletes a user by ID and validates deletion              | `DELETE /api/users/{id}`                |

## Load Testing (Gatling)
Key Highlights:
* Uses Gatling simulation scripts for realistic load scenarios 
* Supports custom ramp-up time, user load and duration 
* Integrates seamlessly within the same Maven project

### How to Run
1. To run gatling load tests:
    ```bash
   mvn gatling:test
    ```
2. Reports will be automatically generated in
    ```bash
   target/gatling
    ```
