package services;

import jakarta.inject.Inject;
import models.sql.User;
import models.repositories.UserRepository;
import schema.request.CreateUserRequestSchema;
import schema.request.UserLoginRequestSchema;
import util.BaseService;
import util.authentication.PasswordHashing;
import util.exceptions.InvalidException;

public class UserService extends BaseService {

    private final UserRepository userRepository;

    @Inject
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createNewUser(CreateUserRequestSchema userSchema) throws Throwable {

        // check username
        User user = userRepository.selectOne(userSchema.username);
        if (user != null) throw new InvalidException(400, "username is already used");

        // check email
        user = userRepository.selectOne(userSchema.email);
        if (user != null) throw new InvalidException(400, "email is already used");

        // hash the password
        String password = PasswordHashing.hash(userSchema.password);

        return userRepository.insertOne(userSchema.username, password, userSchema.email);
    }

    public void removeUser(String username) {
        userRepository.deleteOne(username);
    }

    public User loginUser(UserLoginRequestSchema loginSchema) throws Throwable {
        User user = userRepository.selectOne(loginSchema.username);

        // check user exist
        if (user == null) throw new InvalidException(401, "username or email is not exist");

        // check password
        if (!PasswordHashing.check(loginSchema.password, user.getPassword()))
            throw new InvalidException(401, "unauthorized");

        return user;
    }
}
