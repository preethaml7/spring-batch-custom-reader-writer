package io.protopanda.config;

import io.protopanda.listener.JobCompletionNotificationListener;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final Step loadDataStep;
    private final Step restApiStep;


    @Bean
    public Job createJob(JobCompletionNotificationListener jobCompletionNotificationListener) {
        return jobBuilderFactory.get("LoadUSZipCodeJob")
                .incrementer(new RunIdIncrementer())
                .listener(jobCompletionNotificationListener).flow(loadDataStep)
                .next(restApiStep)
                .end().build();
    }
}
