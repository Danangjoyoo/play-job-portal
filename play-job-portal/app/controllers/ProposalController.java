package controllers;

import jakarta.inject.Inject;
import middlewares.AuthenticationMiddleware;
import middlewares.ErrorHandlerMiddleware;
import middlewares.GeneralMiddleware;
import middlewares.ResponseTimeMiddleware;
import models.mongo.Proposal;
import models.sql.Job;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import schema.request.CreateProposalRequestSchema;
import schema.response.SimpleResponseSchema;
import services.JobService;
import services.ProposalService;
import util.LocalProxy;
import util.exceptions.InvalidException;

import java.util.List;
import java.util.Map;

public class ProposalController extends Controller {
    private final ProposalService proposalService;
    private final JobService jobService;

    @Inject
    public ProposalController(ProposalService proposalService, JobService jobService) {
        this.proposalService = proposalService;
        this.jobService = jobService;
    }

    @With({
        GeneralMiddleware.class,
        ResponseTimeMiddleware.class,
        ErrorHandlerMiddleware.class,
        AuthenticationMiddleware.class
    })
    public Result createProposal() throws InvalidException {
        CreateProposalRequestSchema proposalSchema = new CreateProposalRequestSchema(LocalProxy.getRequestMapper());

        // check job availability
        Job job = jobService.getSingleJob(proposalSchema.jobId);
        if (job == null) throw new InvalidException(400, "job is not exist");

        // insert to mongo
        Proposal proposal = new Proposal(
                proposalSchema.jobId,
                LocalProxy.getCurrentUser().getUserId(),
                proposalSchema.body,
                proposalSchema.status
        );

        proposalService.insertOne(proposal.asDocument());
        return ok(new SimpleResponseSchema().toJson());
    }

    @With({
        GeneralMiddleware.class,
        ResponseTimeMiddleware.class,
        ErrorHandlerMiddleware.class,
        AuthenticationMiddleware.class
    })
    public Result getProposalofUser() {
        Map<String, String> queryParams = LocalProxy.getRequestMapper().getQueryParams();
        Integer page = Integer.valueOf(queryParams.getOrDefault("page", "1"));
        Integer row = Integer.valueOf(queryParams.getOrDefault("row", "10"));

        List<Proposal> proposals = proposalService.getProposalsOfUser(
                LocalProxy.getCurrentUser().getUserId(), page, row
        );

        return ok(Json.toJson(proposals));
    }
}
