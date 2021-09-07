package io.protopanda.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.annotation.OnReadError;
import org.springframework.batch.core.annotation.OnSkipInWrite;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Log4JSkipListener {

    @OnReadError
    public void errorOnRead(Exception exception) {

        log.error("errorOnRead:" + exception.getMessage());
    }

    @OnSkipInWrite
    public void errorOnWrite(Object o, Throwable error) {

        log.error("Error writing record = " + o);
        log.error("errorOnWrite:" + error.getMessage() + " OBJECT:" + o);
    }

}
