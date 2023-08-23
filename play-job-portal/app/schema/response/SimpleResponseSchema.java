package schema.response;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;

public class SimpleResponseSchema extends BaseResponseSchema {
    public Integer status;
    public String message;

    public SimpleResponseSchema(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public SimpleResponseSchema() {
        this(1, "success");
    }
}
