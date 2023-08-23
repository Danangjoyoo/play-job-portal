package middlewares;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import redis.clients.jedis.Jedis;
import util.LocalProxy;

import java.util.concurrent.CompletionStage;

public class GeneralMiddleware extends Action.Simple {
    @Override
    public CompletionStage<Result> call(Http.Request request) {

        // set request mapper
        LocalProxy.setRequestMapper(request);

        CompletionStage<Result> result = delegate.call(request);

        return result;
    }
}
