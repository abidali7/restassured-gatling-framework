package com.example.tests.user;

import io.qameta.allure.*;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import base.BaseTest;
import utils.UserUtils;

@Epic("User API")
@Feature("Update User")
@DisplayName("Update user API tests")
public class UpdateUserTest extends BaseTest {

    @Test
    @Story("Update existing user")
    @DisplayName("Verify that user details can be updated")
    public void updateUser() {
        // Get random userId of a user from the list
        String userId = UserUtils.getRandomUserId();

        String userPayload = (UserUtils.getUserById(userId)).getBody().asString();

        JSONObject userObject = new JSONObject(userPayload);

        // Update the name and email of user
        userObject.put("name", UserUtils.name);
        userObject.put("email", UserUtils.email);

        sendUpdateUserRequest(userObject, userId);
    }

    @Step("Request to update user details")
    private void sendUpdateUserRequest(JSONObject userObject, String userId) {
        given()
                .header("Content-Type", "application/json")
                .body(userObject.toString())
                .pathParam("id", userId)
                .when()
                .put("/users/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(userId))
                .body("name", equalTo(userObject.getString("name")))
                .body("email", equalTo(userObject.getString("email")));
    }
}
