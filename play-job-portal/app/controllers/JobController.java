package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.inject.Inject;
import middlewares.AuthenticationMiddleware;
import middlewares.ErrorHandlerMiddleware;
import middlewares.GeneralMiddleware;
import middlewares.ResponseTimeMiddleware;
import models.sql.Job;
import play.Logger;
import play.libs.Json;
import play.mvc.*;
import schema.request.CreateJobRequestSchema;
import schema.response.GetAllJobResponseSchema;
import schema.response.JobOfUserResponseSchema;
import schema.response.SimpleResponseSchema;
import services.JobService;
import util.LocalProxy;
import util.RequestMapper;

import java.util.List;
import java.util.Map;


public class JobController extends Controller {
    private final Logger.ALogger logger = Logger.of("play");
    private final JobService jobService;

    @Inject
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @With({
        GeneralMiddleware.class,
        ResponseTimeMiddleware.class,
        ErrorHandlerMiddleware.class,
        AuthenticationMiddleware.class
    })
    public Result getAllJobs() {
        Map<String, String> queryParams = LocalProxy.getRequestMapper().getQueryParams();
        Integer page = Integer.valueOf(queryParams.getOrDefault("page", "1"));
        Integer row = Integer.valueOf(queryParams.getOrDefault("row", "10"));

        // get jobs
        List<Job> jobs = jobService.getAllJobs(page, row);

        // wrap result
        JsonNode result = new GetAllJobResponseSchema(jobs).toJson();

        return ok(result);
    }

    @With({
        GeneralMiddleware.class,
        ResponseTimeMiddleware.class,
        ErrorHandlerMiddleware.class,
        AuthenticationMiddleware.class
    })
    public Result createJob(Http.Request request) {
        RequestMapper requestMapper = new RequestMapper(request);
        CreateJobRequestSchema jobSchema = new CreateJobRequestSchema(requestMapper);

        // create job
        Job job = jobService.createJob(jobSchema);

        // wrap result
        JsonNode result = Json.toJson(job);

        return ok(result);
    }

    @With({
        GeneralMiddleware.class,
        ErrorHandlerMiddleware.class,
        AuthenticationMiddleware.class
    })
    public Result getSpecificJob(Long jobId) {
        Job job = jobService.getSingleJob(jobId);

        // wrap
        JsonNode result = Json.toJson(job);

        return ok(result);
    }

    @With({
        GeneralMiddleware.class,
        ErrorHandlerMiddleware.class,
        AuthenticationMiddleware.class
    })
    public Result updateJobStatus(Http.Request request, Long jobId) {
        RequestMapper requestMapper = new RequestMapper(request);
        Integer status = requestMapper.getJson().get("status").asInt();

        jobService.setJobStatus(jobId, status);

        return ok(new SimpleResponseSchema().toJson());
    }

    @With({
        GeneralMiddleware.class,
        ResponseTimeMiddleware.class,
        ErrorHandlerMiddleware.class,
        AuthenticationMiddleware.class
    })
    public Result getPostedJobByUser(Long userId) {
        JsonNode result;
        // using SQL
        List<Object[]> jobs = jobService.getJobOfUser(userId);
        result = new JobOfUserResponseSchema(jobs).toJson();

        // using es
        result = jobService.getJobOfUserES(userId);

        return ok(result);
    }
}
