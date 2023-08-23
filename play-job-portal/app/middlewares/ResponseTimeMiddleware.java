package middlewares;


import play.Logger;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import util.LocalProxy;

import java.util.concurrent.CompletionStage;

public class ResponseTimeMiddleware extends Action.Simple {
    private final Logger.ALogger logger = Logger.of("play");

    @Override
    public CompletionStage<Result> call(Http.Request req) {
        Long rid = System.nanoTime();
        LocalProxy.setRequestId(String.valueOf(rid));

        logger.info(String.format("[%s] Received %s %s\n", rid, req.method(), req.path()));

        Long startTime = System.currentTimeMillis();
        CompletionStage<Result> result = delegate.call(req);
        Long elapsed = System.currentTimeMillis() - startTime;

        logger.info(String.format("[%s] Request done in %sms\n", rid, elapsed));
        return result;
    }
}
