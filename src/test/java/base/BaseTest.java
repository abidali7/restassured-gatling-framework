package base;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {
    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080/api";

        // Filter to log request and response to allure report automatically
        boolean isAllureFilterPresent = RestAssured.filters().stream().anyMatch(found -> found instanceof AllureRestAssured);
        if (!isAllureFilterPresent) RestAssured.filters(new AllureRestAssured());

    }
}