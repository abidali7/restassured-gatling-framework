package com.example.tests.user;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;

import base.BaseTest;
import utils.UserUtils;

@Epic("User API")
@Feature("Delete User")
@DisplayName("Delete user API tests")
public class DeleteUserTest extends BaseTest {

    @Test
    @Story("Delete existing user by userId")
    @DisplayName("Verify that if a user can be deleted by userId")
    public void testDeleteUser() {
        // Get random userId of a user from the list
        String userId = UserUtils.getRandomUserId();

        sendDeleteUserRequest(userId);
        checkUser(userId);
    }

    @Step("Delete user by userId")
    private void sendDeleteUserRequest(String userId) {
        given()
                .pathParam("id", userId)
                .when()
                .delete("/users/{id}")
                .then()
                .statusCode(204);
    }

    @Step("Check if user still exist by userId")
    private void checkUser(String userId) {
        given()
                .pathParam("id", userId)
                .when()
                .get("/users/{id}")
                .then()
                .statusCode(404);
    }
}
