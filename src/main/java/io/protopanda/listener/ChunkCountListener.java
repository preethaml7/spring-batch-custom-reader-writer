package io.protopanda.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;

import java.text.MessageFormat;

@Slf4j
public class ChunkCountListener implements ChunkListener {

    private MessageFormat fmt = new MessageFormat("{0} items processed");

    private int loggingInterval = 1000;

    @Override
    public void beforeChunk(ChunkContext context) {
        // Nothing to do here
    }

    @Override
    public void afterChunk(ChunkContext context) {

        int count = context.getStepContext().getStepExecution().getReadCount();

        // If the number of records processed so far is a multiple of the logging
        // interval then output a log message.
        if (count > 0 && count % loggingInterval == 0) {
            log.info("++++++++++++++++++++++++++++++++++++++++++++++++++++");
            log.info(fmt.format(new Object[]{count}));
            log.info("++++++++++++++++++++++++++++++++++++++++++++++++++++");
        }
    }

    @Override
    public void afterChunkError(ChunkContext context) {
        // Nothing to do here
    }

}