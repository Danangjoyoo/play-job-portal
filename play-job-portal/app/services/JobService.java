package services;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import models.sql.Job;
import models.repositories.JobRepository;
import play.libs.Json;
import schema.request.CreateJobRequestSchema;
import util.BaseService;
import util.adapter.ESAdapter;

import java.util.List;
import java.util.Map;

@Singleton
public class JobService extends BaseService {

    private final JobRepository jobRepository;
    private final ESAdapter esAdapter;

    @Inject
    public JobService(JobRepository jobRepository, ESAdapter esAdapter) {
        this.jobRepository = jobRepository;
        this.esAdapter = esAdapter;
    }

    public List<Job> getAllJobs(Integer page, Integer row) {
        return jobRepository.selectAll(page, row);
    }

    public Job getSingleJob(Long jobId) {
        return jobRepository.selectOne(jobId);
    }

    public Job createJob(CreateJobRequestSchema jobSchema) {
        return jobRepository.insertOne(jobSchema.title, jobSchema.details, jobSchema.salary, jobSchema.userId);
    }

    public void setJobStatus(Long jobId, Integer status) {
        jobRepository.updateJobStatus(jobId, status);
    }

    public List<Object[]> getJobOfUser(Long userId) {
        return jobRepository.selectJobsofUser(userId);
    }

    public JsonNode getJobOfUserES(Long userId) {
        JsonNode result = this.esAdapter.get(
                "job_index",
                Json.toJson(Map.of(
                        "query", Map.of(
                                "bool", Map.of(
                                        "filter", List.of(
                                                Map.of(
                                                        "term", Map.of(
                                                                "user_id", userId
                                                        )
                                                )
                                        )
                                )
                        )
                ))
        );
        return result;
    }
}
