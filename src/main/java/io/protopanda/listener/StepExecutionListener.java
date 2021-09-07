package io.protopanda.listener;

import io.protopanda.model.USZipCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.annotation.OnSkipInProcess;
import org.springframework.batch.core.annotation.OnSkipInRead;
import org.springframework.batch.core.annotation.OnSkipInWrite;
import org.springframework.batch.core.annotation.OnWriteError;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

@Slf4j
public class StepExecutionListener {

    @OnSkipInRead
    public void onSkipInRead(Throwable t) {
        log.error("On Skip in Read Error : " + t.getMessage());
    }

    @OnSkipInWrite
    public void onSkipInWrite(USZipCode item, Throwable t) {
        log.error("Skipped in write due to : " + t.getMessage());
    }

    @OnSkipInProcess
    public void onSkipInProcess(USZipCode item, Throwable t) {
        log.error("Skipped in process due to: " + t.getMessage() + "NZImmigrantInfo =" + item.toString());
        log.error(printStackTrace(t));

    }

    @OnWriteError
    public void onWriteError(Exception exception, List<? extends USZipCode> items) {
        log.error("Error on write on " + items + " : " + exception.getMessage());
    }

    private String printStackTrace(Throwable exception) {
        if (null == exception) {
            return "";
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        String sStackTrace = sw.toString();
        return sStackTrace;
    }

}
