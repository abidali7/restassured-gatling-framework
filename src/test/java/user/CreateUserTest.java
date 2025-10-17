package com.example.tests.user;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

import base.BaseTest;
import utils.UserUtils;

@Epic("User API")
@Feature("Create user")
@DisplayName("Create a new user API tests")
public class CreateUserTest extends BaseTest {

    @Test
    @DisplayName("Verify a new user can be created successfully")
    public void createUser() {
        JSONObject userPayload = UserUtils.createUserPayload();

        given()
                .contentType(ContentType.JSON)
                .body(userPayload.toString())
                .when()
                .post("/users")
                .then().statusCode(201);
    }
}