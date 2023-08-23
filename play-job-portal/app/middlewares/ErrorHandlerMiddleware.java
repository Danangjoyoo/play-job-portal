package middlewares;

import com.fasterxml.jackson.databind.JsonNode;
import play.Logger;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import schema.response.SimpleResponseSchema;
import util.exceptions.InvalidException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class ErrorHandlerMiddleware extends Action.Simple {
    private final Logger.ALogger logger = Logger.of("play");

    @Override
    public CompletionStage<Result> call(Http.Request req) {
        try {
            return delegate.call(req);
        } catch (Throwable e) {
            if (e instanceof InvalidException invalidException) {
                return CompletableFuture.completedFuture(invalidException.asResult());
            }
            logger.error(e.getMessage());
            JsonNode json = new SimpleResponseSchema(0, e.getMessage()).toJson();
            Result result = Results.badRequest(json);
            return CompletableFuture.completedFuture(result);
        }
    }
}
