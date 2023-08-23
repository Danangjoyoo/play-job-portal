package schema.response;

import models.sql.Job;

import java.util.ArrayList;
import java.util.List;

public class GetAllJobResponseSchema extends BaseResponseSchema {

    public class JobResponseSchema {
        public Long jobId;
        public String title;
        public Integer status;
        public JobResponseSchema(Long jobId, String title, Integer status) {
            this.jobId = jobId;
            this.title = title;
            this.status = status;
        }
    }

    public List<JobResponseSchema> jobs = new ArrayList<>();

    public GetAllJobResponseSchema(List<Job> jobs) {
        for (Job job : jobs) {
            this.jobs.add(
                new JobResponseSchema(
                    job.getJobId(), job.getTitle(), job.getStatus()
                )
            );
        }
    }
}
