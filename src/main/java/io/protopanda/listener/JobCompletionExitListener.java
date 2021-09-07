package io.protopanda.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JobCompletionExitListener extends JobExecutionListenerSupport {

    @Override
    public void afterJob(JobExecution jobExecution) {

        if (jobExecution == null)
            return;

        ExitStatus es = jobExecution.getExitStatus();

        if (es != null && "FAILED".equals(es.getExitCode())) {
            log.error("Failed job execution:" + jobExecution + " exitStatus:" + es);
            System.exit(-1);
        }

        System.exit(0);
    }

}
