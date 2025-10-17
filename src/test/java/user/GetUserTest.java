package com.example.tests.user;

import io.restassured.response.Response;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.*;

import base.BaseTest;
import utils.UserUtils;

@Epic("User API")
@Feature("Get user")
@DisplayName("Get user API tests")
public class GetUserTest extends BaseTest {

    private static String userId;

    @Test
    @Story("Get all users")
    @DisplayName("Verify all users are retrieved successfully")
    public void getAllUsersTest() {
        // Calling the method getRandomUserId wil also verify list of users
        userId = UserUtils.getRandomUserId();
    }

    @Test
    @Story("Get user by ID")
    @DisplayName("Verify that user can be retrieved by ID")
    public void getUserByIdTest() {
        Response response = UserUtils.getUserById(userId);
        assertThat(String.valueOf((response.getBody().asString().contains(userId))), true);
    }
}
