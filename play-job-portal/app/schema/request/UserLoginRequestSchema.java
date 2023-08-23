package schema.request;

import com.fasterxml.jackson.databind.JsonNode;
import util.RequestMapper;

public class UserLoginRequestSchema extends BaseRequestSchema {
    public String username;
    public String password;
    public UserLoginRequestSchema(RequestMapper requestMapper) {
        super(requestMapper);

        JsonNode json = requestMapper.getJson();
        this.username = json.get("username").textValue();
        this.password = json.get("password").textValue();
    }
}
