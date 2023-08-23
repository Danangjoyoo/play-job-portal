package modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.typesafe.config.Config;
import controllers.UserController;
import middlewares.AuthenticationMiddleware;
import models.repositories.UserRepository;
import services.UserService;
import util.adapter.RedisAdapter;

public class UserModule extends AbstractModule {
    @Provides
    UserRepository provideUserRepository(){
        return new UserRepository();
    }

    @Provides
    UserService provideUserService(UserRepository jobRepository) {
        return new UserService(jobRepository);
    }

    @Provides
    UserController provideUserController(UserService jobService, Config config, RedisAdapter redisAdapter) {
        return new UserController(jobService, config, redisAdapter);
    }

    @Provides
    public AuthenticationMiddleware provideAuthMiddleware(
            UserRepository userRepository,
            Config config,
            RedisAdapter redisAdapter
    ) {
        return new AuthenticationMiddleware(userRepository, config, redisAdapter);
    }
}
