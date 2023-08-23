package modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import controllers.ProposalController;
import services.JobService;
import services.ProposalService;
import util.adapter.MongoAdapter;

public class ProposalModule extends AbstractModule {
    @Provides
    public ProposalService provideProposalService(MongoAdapter mongoAdapter) {
        return new ProposalService(mongoAdapter);
    }

    @Provides
    public ProposalController provideProposalController(ProposalService proposalService, JobService jobService) {
        return new ProposalController(proposalService, jobService);
    }
}
