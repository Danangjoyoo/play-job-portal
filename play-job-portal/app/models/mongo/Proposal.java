package models.mongo;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bson.types.ObjectId;
import util.enums.ProposalStatusEnum;

import java.util.Date;

@Getter
@Setter
public class Proposal {
    private ObjectId objectId;
    private Long jobId;
    private Long userId;
    private String body;
    private Date createdAt;
    private ProposalStatusEnum status;

    public Proposal(Long jobId, Long userId, String body, ProposalStatusEnum status) {
        this.jobId = jobId;
        this.userId = userId;
        this.body = body;
        this.createdAt = new Date();
        this.status = status;
    }

    public Proposal(ObjectId objectId, Long jobId, Long userId, String body, Date createdAt, String status) {
        this.objectId = objectId;
        this.jobId = jobId;
        this.userId = userId;
        this.body = body;
        this.createdAt = createdAt;
        this.status = ProposalStatusEnum.valueOf(status);
    }

    public Document asDocument() {
        return new Document("job_id", jobId)
                .append("user_id", userId)
                .append("body", body)
                .append("created_at", createdAt)
                .append("status", status);
    }

    public static Proposal load(Document document) {
        return new Proposal(
                document.getObjectId("_id"),
                document.get("job_id", Long.class),
                document.get("user_id", Long.class),
                document.get("body", String.class),
                document.get("created_at", Date.class),
                document.get("status", String.class)
        );
    }
}
