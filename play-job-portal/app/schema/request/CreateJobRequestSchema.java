package schema.request;

import com.fasterxml.jackson.databind.JsonNode;
import util.RequestMapper;

public class CreateJobRequestSchema extends BaseRequestSchema{
    public String title;
    public String details;
    public Integer salary;
    public Long userId;

    public CreateJobRequestSchema(RequestMapper requestMapper) {
        super(requestMapper);

        JsonNode json = requestMapper.getJson();

        this.title = json.get("title").textValue();
        this.details = json.get("details").textValue();
        this.salary = Integer.valueOf(json.get("salary").toString());
        this.userId = Long.valueOf(json.get("userId").toString());
    }
}
