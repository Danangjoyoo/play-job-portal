package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.typesafe.config.Config;
import jakarta.inject.Inject;
import middlewares.AuthenticationMiddleware;
import middlewares.ErrorHandlerMiddleware;
import middlewares.GeneralMiddleware;
import middlewares.ResponseTimeMiddleware;
import models.sql.User;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import schema.request.CreateUserRequestSchema;
import schema.request.UserLoginRequestSchema;
import schema.response.LoginResponseSchema;
import schema.response.SimpleResponseSchema;
import services.UserService;
import util.LocalProxy;
import util.adapter.RedisAdapter;
import util.authentication.Jwt;

public class UserController extends Controller {
    private Logger.ALogger logger = Logger.of("play");
    private final UserService userService;
    private final RedisAdapter redisAdapter;
    private final Config config;

    @Inject
    public UserController(UserService userService, Config config, RedisAdapter redisAdapter) {
        this.userService = userService;
        this.config = config;
        this.redisAdapter = redisAdapter;
    }

    @With({
        GeneralMiddleware.class,
        ResponseTimeMiddleware.class,
        ErrorHandlerMiddleware.class
    })
    public Result createUser() throws Throwable {
        CreateUserRequestSchema userSchema = new CreateUserRequestSchema(
                LocalProxy.getRequestMapper()
        );

        User user = userService.createNewUser(userSchema);

        return ok(Json.toJson(user));
    }

    @With({
        GeneralMiddleware.class,
        ResponseTimeMiddleware.class,
        ErrorHandlerMiddleware.class,
        AuthenticationMiddleware.class
    })
    public Result deleteUser() {
        String username = LocalProxy.getCurrentUser().getUsername();
        userService.removeUser(username);

        return ok(new SimpleResponseSchema(1, "user removed").toJson());
    }

    @With({
        GeneralMiddleware.class,
        ResponseTimeMiddleware.class,
        ErrorHandlerMiddleware.class
    })
    public Result userLogin() throws Throwable {
        UserLoginRequestSchema loginSchema = new UserLoginRequestSchema(
                LocalProxy.getRequestMapper()
        );

        // login
        User user = userService.loginUser(loginSchema);

        // create JWT
        String token = Jwt.generateAccessToken(user.getUsername());

        return ok(new LoginResponseSchema(token).toJson());
    }

    @With({
        GeneralMiddleware.class,
        ResponseTimeMiddleware.class,
        ErrorHandlerMiddleware.class,
        AuthenticationMiddleware.class
    })
    public Result userLogout() {
        String authKey = LocalProxy.getRequestMapper()
                .getHeaders()
                .get("Authorization");
        String token = Jwt.getTokenFromAuth(authKey);

        // revoked with redis
        redisAdapter.hmset(config.getString("redis.revokeHash"), token, "1");

        return ok(new SimpleResponseSchema(1, "revoked").toJson());

    }
}
