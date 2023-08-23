package schema.response;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.mvc.Result;

public abstract class BaseResponseSchema {
    public JsonNode toJson() {
        return Json.toJson(this);
    }
}
