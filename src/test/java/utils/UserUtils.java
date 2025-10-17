package utils;

import io.qameta.allure.Allure;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import com.github.javafaker.Faker;
import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UserUtils {
    private static final Faker faker = new Faker();
    public static String name = faker.name().fullName();
    public static String email = faker.internet().emailAddress();

    /**
     * Builds and returns a JSON payload for creating a new user.
     * @return JSONObject - User creation request body which include ID, name, email and calendarIds.
     */
    public static JSONObject createUserPayload() {
        String userId = UUID.randomUUID().toString();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String fullName = firstName + " " + lastName;
        String customisedEmail = firstName + "." + lastName + "@gmail.com";

        JSONObject requestBody = new JSONObject();
        requestBody.put("id", userId);
        requestBody.put("name", fullName);
        requestBody.put("email", customisedEmail);
        requestBody.put("calendarIds", new String[] {userId});
        return requestBody;
    }

    /**
     * This method will return a random user ID based on response from user endpoint.
     * @return a random user id as a string
     * */
    @Step("Get all user and userId of a random user")
    public static String getRandomUserId() {
        Response response = given()
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .body("users.size()", greaterThan(0))
                .body("users[0].id", notNullValue())
                .extract()
                .response();

        List<String> userIds = response.path("users.id");
        if (userIds == null || userIds.isEmpty()) {
            throw new IllegalStateException("No users found in API response.");
        }

        Random random = new Random();
        String userId = userIds.get(random.nextInt(userIds.size()));

        Allure.attachment("Randomly selected userId", userId);
        return userId;
    }

    /**
     * Get a specific user object based on userId.
     * @param userId - The ID of the user object to be retrieved.
     * @return {@link Response} - Object containing the HTTP response,
     * including headers, status code and JSON body which will hold user object.
     * @throws AssertionError - if response's status code is not 200 or returned user ID
     * does not match the requested userId
     */
    @Step("Get user based on provided userId")
    public static Response getUserById(String userId) {
        return given()
                .pathParam("id", userId)
                .when()
                .get("/users/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(userId))
                .extract()
                .response();
    }
}
