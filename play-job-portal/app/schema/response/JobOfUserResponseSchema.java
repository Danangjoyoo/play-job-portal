package schema.response;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class JobOfUserResponseSchema extends BaseResponseSchema {

    @AllArgsConstructor
    public class JobResponseSchema {
        public Long jobId;
        public String title;
        public Integer status;
        public Long userId;
        public String username;
        public String email;
    }

    public List<JobResponseSchema> jobs = new ArrayList<>();

    public JobOfUserResponseSchema(List<Object[]> jobs) {
        for (Object[] job : jobs) {
            this.jobs.add(
                new JobResponseSchema(
                    (Long) job[0],
                    (String) job[1],
                    (Integer) job[2],
                    (Long) job[3],
                    (String) job[4],
                    (String) job[5]
                )
            );
        }
    }
}
