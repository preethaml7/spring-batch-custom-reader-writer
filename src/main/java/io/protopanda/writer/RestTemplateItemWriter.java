package io.protopanda.writer;

import io.protopanda.model.USZipCode;
import io.protopanda.repository.USZipCodeRestApiCallStatusDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
@StepScope
public class RestTemplateItemWriter implements ItemWriter<USZipCode> {

    private final USZipCodeRestApiCallStatusDAO restApiCallStatusDAO;
    private final RestTemplate restTemplate;

    @Override
    public void write(List<? extends USZipCode> USZipCodeList) {

        for (USZipCode usZipCode : USZipCodeList) {

            try {

                String url = "https://api.zippopotam.us/us/" + usZipCode.getZip();
                Instant startTime = Instant.now();
                String apiResponse = this.restTemplate.getForObject(url, String.class);
                Instant stopTime = Instant.now();

                long apiResponseTime = Duration.between(startTime, stopTime).toMillis();
                log.info("API RESPONSE TIME TAKEN ---> " + apiResponseTime);

                if (apiResponse != null) {
                    this.restApiCallStatusDAO.updateRestApiCallStatusInDB(usZipCode, "true", apiResponse, String.valueOf(apiResponseTime));
                } else {
                    this.restApiCallStatusDAO.updateRestApiCallStatusInDB(usZipCode, "false", "EMPTY", String.valueOf(apiResponseTime));
                }
            } catch (Exception e) {
                log.error("Exception: " + e.getMessage());
                this.restApiCallStatusDAO.updateRestApiCallStatusInDB(usZipCode, "false", "EMPTY", "EMPTY");
            }
        }
    }
}
