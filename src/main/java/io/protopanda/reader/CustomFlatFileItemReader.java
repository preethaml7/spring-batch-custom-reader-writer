package io.protopanda.reader;

import io.protopanda.model.USZipCode;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("flatFileItemReader")
@StepScope
@RequiredArgsConstructor
public class CustomFlatFileItemReader implements ItemReader<USZipCode> {

    private final FlatFileItemReader<USZipCode> itemReader;

    @Override
    public USZipCode read() throws Exception {
        return itemReader.read();
    }

}