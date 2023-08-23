package util.adapter;

import com.typesafe.config.Config;
import jakarta.inject.Inject;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;

public class RedisAdapter {
    private final String redisHost;
    private final Integer redisPort;

    @Inject
    public RedisAdapter(Config config) {
        redisHost = config.getString("redis.host");
        redisPort = config.getInt("redis.port");
    }

    public void hmset(String hash, String key, String value) {
        try (Jedis jedis = new Jedis(redisHost, redisPort)) {
            jedis.hmset(hash, Map.of(key, value));
        }
    }

    public String hmget(String hash, String key) {
        try (Jedis jedis = new Jedis(redisHost, redisPort)) {
            List<String> out = jedis.hmget(hash, key);
            if (out == null) return null;
            return out.get(0);
        }
    }

}
