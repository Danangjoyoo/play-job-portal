package schema.request;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import util.RequestMapper;

public class BaseRequestSchema {
    private final RequestMapper requestMapper;

    public BaseRequestSchema(RequestMapper requestMapper) {
        this.requestMapper = requestMapper;
    }

    public JsonNode toJson() {
        return Json.toJson(this);
    }
}
