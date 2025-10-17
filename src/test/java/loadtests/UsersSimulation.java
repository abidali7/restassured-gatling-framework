package com.example.gatling;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import java.util.UUID;

public class UsersSimulation extends Simulation {

    // Read base URL from system property, default to local API
    private static final String BASE_URL = "http://localhost:8080/api";

    HttpProtocolBuilder httpProtocol = http.baseUrl(BASE_URL)
            .acceptHeader("application/json")
            .contentTypeHeader("application/json");

    // Get user list
    ScenarioBuilder getUsersList = scenario("Laod test to get list of user")
            .exec(
                    http("Get all users")
                            .get("/users")
                            .check(status().is(200))
            )
            .pause(1);

    // Simple create user scenario using a dynamically generated payload
    ScenarioBuilder createUser = scenario("Create new user")
            .exec(session -> {
                String id = UUID.randomUUID().toString();
                String name = "LoadUser " + id.substring(0, 5);
                String email = "load." + id.substring(0,5) + "@example.com";

                String json = String.format(
                        "{\"id\":\"%s\",\"name\":\"%s\",\"email\":\"%s\",\"calendarIds\":[\"%s\"]}",
                        id, name, email, id
                );
                return session.set("payload", json);
            })
            .exec(
                    http("Create user")
                            .post("/users")
                            .body(StringBody(session -> session.getString("payload")))
                            .asJson()
                            .check(status().in(200,201))
            )
            .pause(1);

    {
        // Configure profile for injection, override via system props
        // Default values: 10 users ramping up over 30s
        int users = Integer.parseInt(System.getProperty("users", "10"));
        int durationSeconds = Integer.parseInt(System.getProperty("duration", "30"));

        // execution in parallel or mix them
        setUp(
                getUsersList.injectOpen(rampUsers(users).during(durationSeconds)),
                createUser.injectOpen(constantUsersPerSec(2).during(durationSeconds)) // e.g. 2 creations/sec
        ).protocols(httpProtocol);
    }
}
