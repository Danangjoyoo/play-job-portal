package util.exceptions;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.mvc.Result;

import static play.mvc.Results.status;

import java.util.Map;

public class InvalidException extends Throwable {
    private final Integer statusCode;
    private String message;
    private final JsonNode payload;

    public InvalidException(Integer statusCode, Object payload) {
        this.statusCode = statusCode;
        this.payload = Json.toJson(payload);
    }
    public InvalidException(Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.payload = Json.toJson(Map.of("message", message));
    }

    public Result asResult() {
        return status(this.statusCode, this.payload);
    }
}
