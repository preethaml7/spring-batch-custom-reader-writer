package io.protopanda.processor;

import io.protopanda.model.USZipCode;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class USZipCodeProcessor implements ItemProcessor<USZipCode, USZipCode> {

    @Override
    public USZipCode process(final USZipCode USZipCode) {

        System.out.println(" <<----------  ITEM RECEIVED IN PROCESSOR ---------->> " + USZipCode.toString());

        return USZipCode;
    }
}
