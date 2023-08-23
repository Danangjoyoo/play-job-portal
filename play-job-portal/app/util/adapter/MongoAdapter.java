package util.adapter;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.typesafe.config.Config;
import jakarta.inject.Inject;
import lombok.Getter;

@Getter
public class MongoAdapter {
    private final MongoDatabase database;
    private final MongoClient client;

    @Inject
    public MongoAdapter(Config config) {
        String uri = config.getString("mongodb.uri");
        client = MongoClients.create(uri);
        database = client.getDatabase("play_jobportal_mongo");
    }
}
