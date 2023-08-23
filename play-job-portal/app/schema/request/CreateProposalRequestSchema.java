package schema.request;

import com.fasterxml.jackson.databind.JsonNode;
import util.RequestMapper;
import util.enums.ProposalStatusEnum;

public class CreateProposalRequestSchema extends BaseRequestSchema {
    public Long jobId;
    public String body;
    public ProposalStatusEnum status;

    public CreateProposalRequestSchema(RequestMapper requestMapper) {
        super(requestMapper);

        JsonNode json = requestMapper.getJson();
        jobId = json.get("job_id").asLong();
        body = json.get("body").asText();
        status = json.get("draft").asBoolean() ? ProposalStatusEnum.DRAFT:ProposalStatusEnum.SENT;
    }
}
