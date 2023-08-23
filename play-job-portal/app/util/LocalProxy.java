package util;

import jakarta.persistence.EntityManager;
import models.sql.User;
import play.Logger;
import play.mvc.Http;
import redis.clients.jedis.Jedis;

public class LocalProxy {
    private static final ThreadLocal<User> currentUserLocal = new ThreadLocal<>();
    private static final ThreadLocal<String> requestIdLocal = new ThreadLocal<>();
    private static final ThreadLocal<RequestMapper> requestMapperLocal = new ThreadLocal<>();
    private static final ThreadLocal<EntityManager> entityManagerLocal = new ThreadLocal<>();
    private static final ThreadLocal<Jedis> redisLocal = new ThreadLocal<>();
    private static final ThreadLocal<Logger.ALogger> loggerLocal = new ThreadLocal<>();

    public static void setCurrentUser(User user) {
        currentUserLocal.set(user);
    }

    public static User getCurrentUser() {
        return currentUserLocal.get();
    }

    public static void setRequestId(String rid) {
        requestIdLocal.set(rid);
    }

    public static String getRequestId() {
        return requestIdLocal.get();
    }

    public static void setRequestMapper(Http.Request request) {
        requestMapperLocal.set(new RequestMapper(request));
    }

    public static RequestMapper getRequestMapper() {
        return requestMapperLocal.get();
    }

    public static void setEntityManager(EntityManager entityManager) {
        entityManagerLocal.set(entityManager);
    }

    public static EntityManager getEntityManager() {
        return entityManagerLocal.get();
    }

    public static void setRedis(Jedis jedis) {
        redisLocal.set(jedis);
    }

    public static Jedis getRedis() {
        return redisLocal.get();
    }

    public static void setLogger(Logger.ALogger logger) {
        loggerLocal.set(logger);
    }

    public static Logger.ALogger getLogger() {
        return loggerLocal.get();
    }
}
