package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    @Test
    public void testCreateUser() {
        String username = app.config().getString("test.user.username");
        String password = app.config().getString("test.user.password");
        String email = app.config().getString("test.user.email");

        JsonNode json = Json.toJson(
            Map.of(
                "username", username,
                "password", password,
                "email", email
            )
        );

        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(POST)
                .bodyJson(json)
                .uri("/user/create");

        Result result = route(app, request);

        assertEquals(OK, result.status());

        JsonNode jsonResult = Json.parse(contentAsString(result));
        assertEquals(jsonResult.get("username").asText(), username);
        assertEquals(jsonResult.get("email").asText(), email);
    }

    @Test
    public void testLoginUser() {
        String username = app.config().getString("test.user.username");
        String password = app.config().getString("test.user.password");

        JsonNode json = Json.toJson(
            Map.of(
                "username", username,
                "password", password
            )
        );

        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(POST)
                .bodyJson(json)
                .uri("/user/login");

        Result result = route(app, request);

        assertEquals(OK, result.status());
    }

}
