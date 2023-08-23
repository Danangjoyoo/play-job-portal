package modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import controllers.JobController;
import models.repositories.JobRepository;
import services.JobService;
import util.adapter.ESAdapter;

public class JobModule extends AbstractModule {
    @Provides
    JobRepository provideJobRepository() {
        return new JobRepository();
    }

    @Provides
    JobService provideJobService(JobRepository jobRepository, ESAdapter esAdapter) {
        return new JobService(jobRepository, esAdapter);
    }

    @Provides
    JobController provideJobController(JobService jobService) {
        return new JobController(jobService);
    }
}
