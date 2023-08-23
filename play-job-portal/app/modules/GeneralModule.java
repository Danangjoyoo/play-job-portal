package modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.typesafe.config.Config;
import models.repositories.UserRepository;
import util.adapter.ESAdapter;
import util.adapter.MongoAdapter;
import util.adapter.RedisAdapter;

public class GeneralModule extends AbstractModule {
    @Provides
    public MongoAdapter provideMongoAdapter(Config config) {
        return new MongoAdapter(config);
    }

    @Provides
    public RedisAdapter provideRedisAdapter(Config config) {
        return new RedisAdapter(config);
    }

    @Provides
    public ESAdapter provideEsAdapter(Config config) {
        return new ESAdapter(config);
    }
}
