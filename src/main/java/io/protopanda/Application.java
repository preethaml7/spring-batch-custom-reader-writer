package io.protopanda;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.Instant;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class Application implements ApplicationRunner {

    private final JobLauncher jobLauncher;
    private final Job job;

    private Instant startTime;
    private Instant stopTime;


    public static void main(String[] args) {

        System.exit(SpringApplication.exit(SpringApplication.run(Application.class, args)));
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        startTime = Instant.now();

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("JOB-START-TIME", java.util.Calendar.getInstance().getTime().toString())
                .toJobParameters();

        jobLauncher.run(job, jobParameters);

        stopTime = Instant.now();

        long timeElapsed = Duration.between(startTime, stopTime).toMillis() / 1000;
        log.info("<<-------------  BATCH JOB EXECUTION TIME  ------------->> " + timeElapsed + " seconds");

    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
