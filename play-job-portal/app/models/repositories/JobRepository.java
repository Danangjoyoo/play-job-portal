package models.repositories;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import models.sql.Job;
import util.BaseRepository;

import java.util.List;

@Singleton
public class JobRepository extends BaseRepository {

    @Inject
    public  JobRepository() {}

    public List<Job> selectAll(Integer page, Integer row) {
        List<Job> jobs = this.getEntityManager()
                .createQuery("SELECT j FROM Job j ORDER BY j.title asc limit :limit offset :offset", Job.class)
                .setParameter("limit", row)
                .setParameter("offset", (page - 1) * row)
                .getResultList();
        return jobs;
    }

    public Job selectOne(Long jobId) {
        return this.getEntityManager().find(Job.class, jobId);
    }

    public Job insertOne(String title, String details, Integer salary, Long userId) {
        Job job = new Job();

        job.setTitle(title);
        job.setDetails(details);
        job.setSalary(salary);
        job.setUserId(userId);

        this.persist(job);

        return  job;
    }

    public void updateJobStatus(Long jobId, Integer status) {
        Job job = this.selectOne(jobId);
        job.setStatus(status);

        this.persist();
    }

    public List<Object[]> selectJobsofUser(Long userId) {
        String query = """
                SELECT
                    j.jobId,
                    j.title,
                    j.status,
                    u.userId,
                    u.username,
                    u.email
                FROM Job j
                JOIN User u on u.userId = j.userId
                WHERE u.userId = :userId
                ORDER BY j.createdAt asc
                """;
        List<Object[]> jobs = this.getEntityManager()
                .createQuery(query, Object[].class)
                .setParameter("userId", userId)
                .getResultList();
        return jobs;
    }
}
