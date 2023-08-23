package schema.request;

import com.fasterxml.jackson.databind.JsonNode;
import util.RequestMapper;

public class CreateUserRequestSchema extends BaseRequestSchema{
    public String username;
    public String password;
    public String email;

    public CreateUserRequestSchema(RequestMapper requestMapper) {
        super(requestMapper);
        JsonNode json = requestMapper.getJson();
        this.username = json.get("username").textValue();
        this.password = json.get("password").textValue();
        this.email = json.get("email").textValue();
    }
}
