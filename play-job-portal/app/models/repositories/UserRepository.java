package models.repositories;

import jakarta.persistence.NoResultException;
import models.sql.User;
import util.BaseRepository;


public class UserRepository extends BaseRepository {
    public User insertOne(String username, String password, String email) {
        User user = new User();

        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        this.persist(user);

        return user;
    }

    public void deleteOne(String username) {
        User user = selectOne(username);

        if (user == null) return;

        this.getEntityManager().remove(user);
        this.persist();
    }

    public User selectOne(String username) {
        String query = "SELECT u from User u WHERE u.username = :username";
        User user = null;

        try {
            user = this.getEntityManager()
                    .createQuery(query, User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException ignored) {}

        if (user == null) {
            try {
                query = "SELECT u from User u WHERE u.email = :username";
                user = this.getEntityManager()
                        .createQuery(query, User.class)
                        .setParameter("username", username)
                        .getSingleResult();
            } catch (NoResultException ignored) {}
        }

        return user;
    }
}
