package middlewares;

import com.typesafe.config.Config;
import jakarta.inject.Inject;
import models.sql.User;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import models.repositories.UserRepository;
import util.LocalProxy;
import util.RequestMapper;
import util.adapter.RedisAdapter;
import util.authentication.Jwt;
import util.exceptions.InvalidException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class AuthenticationMiddleware extends Action.Simple {
    private final UserRepository userRepository;
    private final Config config;
    private final RedisAdapter redisAdapter;
    private final String revokedHash;

    @Inject
    public AuthenticationMiddleware(UserRepository userRepository, Config config, RedisAdapter redisAdapter) {
        this.userRepository = userRepository;
        this.config = config;
        this.revokedHash = config.getString("redis.revokeHash");
        this.redisAdapter = redisAdapter;
    }

    @Override
    public CompletionStage<Result> call(Http.Request req) {
        RequestMapper requestMapper = LocalProxy.getRequestMapper();

        try {
            // check api key
            checkApiKey(requestMapper);

            // get JWT token
            String authKey = requestMapper.getHeaders().get("Authorization");
            if (authKey == null || authKey.isBlank())
                throw new InvalidException(401, "no token");
            String token = Jwt.getTokenFromAuth(authKey);

            // check revoked JWT
            checkRevokedToken(token);

            // set current user
            User user = getUserFromToken(token);
            LocalProxy.setCurrentUser(user);
        } catch (InvalidException ie) {
            return CompletableFuture.completedFuture(ie.asResult());
        }

        return delegate.call(req);
    }

    public void checkApiKey(RequestMapper requestMapper) throws InvalidException {
        // get api key
        String apikey = requestMapper.getHeaders().get("x-api-key");

        if (apikey == null || !apikey.equals("danjoy123")) {
            throw new InvalidException(401, "missing api key");
        }
    }

    public void checkRevokedToken(String token) throws InvalidException {
        String value = redisAdapter.hmget(revokedHash, token);
        if (value != null) throw new InvalidException(401, "token expired");
    }

    public User getUserFromToken(String token) throws InvalidException {
        // get username from jwt
        String username = Jwt.getUsernameFromToken(token);

        // get user
        User user = userRepository.selectOne(username);

        if (user == null) throw new InvalidException(401, "invalid token");

        return user;
    }
}
