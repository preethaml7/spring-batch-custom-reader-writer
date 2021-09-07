package io.protopanda.config;

import io.protopanda.listener.ChunkCountListener;
import io.protopanda.listener.LogStepExecutionListener;
import io.protopanda.listener.StepExecutionListener;
import io.protopanda.model.USZipCode;
import io.protopanda.processor.USZipCodeProcessor;
import io.protopanda.reader.CustomFlatFileItemReader;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class LoadDataStepConfig {

    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;
    private final USZipCodeProcessor itemProcessor;
    private final CustomFlatFileItemReader customFlatFileItemReader;

    @Bean
    @StepScope
    public JdbcBatchItemWriter<USZipCode> itemWriter() {

        JdbcBatchItemWriter<USZipCode> itemWriter = new JdbcBatchItemWriter<>();
        itemWriter.setDataSource(dataSource);
        itemWriter.setSql("INSERT INTO testdb.us_zipcodes (id, zip, lat, lng, city, state_id, state_name, zcta, parent_zcta, population, density, county_fips, county_name, county_weights, county_names_all, county_fips_all, imprecise, military, timezone, is_processed, api_response, api_response_time) VALUES (:id,:zip,:lat,:lng,:city,:state_id,:state_name,:zcta,:parent_zcta,:population,:density,:county_fips,:county_name,:county_weights,:county_names_all,:county_fips_all,:imprecise,:military,:timezone,:is_processed,:api_response,:api_response_time)");

        itemWriter.setItemSqlParameterSourceProvider
                (new BeanPropertyItemSqlParameterSourceProvider<>());
        itemWriter.afterPropertiesSet();

        return itemWriter;
    }

    @Bean
    public FlatFileItemReader<USZipCode> getItemReader() throws Exception {

        FlatFileItemReader<USZipCode> flatFileItemReader = new FlatFileItemReaderBuilder<USZipCode>().name("flatFileItemReader")
                .resource(new ClassPathResource("us-zipcodes.csv")).delimited()
                .names(new String[]{"zip", "lat", "lng", "city", "state_id", "state_name", "zcta", "parent_zcta", "population", "density", "county_fips", "county_name", "county_weights", "county_names_all", "county_fips_all", "imprecise", "military", "timezone"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {
                    {
                        setTargetType(USZipCode.class);
                    }
                }).build();

        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.afterPropertiesSet();
        flatFileItemReader.open(new ExecutionContext());
        return flatFileItemReader;
    }

    @Bean
    public Step loadDataStep() {
        return stepBuilderFactory.get("loadDataStep")
                .<USZipCode, USZipCode>chunk(7000)
                .reader(customFlatFileItemReader)
                .processor(itemProcessor)
                .writer(itemWriter())
                .listener(new ChunkCountListener())
                .listener(new StepExecutionListener())
                .listener(new LogStepExecutionListener())
                .build();
    }
}