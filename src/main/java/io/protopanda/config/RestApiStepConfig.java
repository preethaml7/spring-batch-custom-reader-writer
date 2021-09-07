package io.protopanda.config;

import io.protopanda.listener.ChunkCountListener;
import io.protopanda.listener.LogStepExecutionListener;
import io.protopanda.listener.StepExecutionListener;
import io.protopanda.mapper.USZipCodeRowMapper;
import io.protopanda.model.USZipCode;
import io.protopanda.partitioner.ColumnRangePartitioner;
import io.protopanda.processor.USZipCodeProcessor;
import io.protopanda.writer.RestTemplateItemWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@EnableBatchProcessing
public class RestApiStepConfig {

    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;
    private final RestTemplateItemWriter itemWriter;

    @Bean
    @StepScope
    public JdbcPagingItemReader<USZipCode> itemReader(
            @Value("#{stepExecutionContext['minValue']}") Long minValue,
            @Value("#{stepExecutionContext['maxValue']}") Long maxValue) {

        System.out.println("reading " + minValue + " to " + maxValue);

        Map<String, Order> sortKeys = new HashMap<>();
        sortKeys.put("id", Order.ASCENDING);

        MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
        queryProvider.setSelectClause("id, zip, lat, lng, city, state_id,state_name, zcta, parent_zcta, population, density, county_fips, county_name, county_weights, county_names_all, county_fips_all, imprecise, military, timezone, api_response, api_response_time");
        queryProvider.setFromClause("FROM us_zipcodes");
        queryProvider.setWhereClause("WHERE id >= " + minValue + " AND id < " + maxValue);
        queryProvider.setSortKeys(sortKeys);

        JdbcPagingItemReader<USZipCode> reader = new JdbcPagingItemReader<>();
        reader.setDataSource(dataSource);
        reader.setFetchSize(7000);
        reader.setRowMapper(new USZipCodeRowMapper());
        reader.setQueryProvider(queryProvider);

        return reader;
    }


    // Master step
    @Bean
    public Step restApiStep() {
        return stepBuilderFactory.get("restApiStep")
                .partitioner(slaveStep().getName(), partitioner())
                .step(slaveStep())
                .gridSize(70)
                .taskExecutor(asyncTaskExecutor())
                .build();
    }

    // Slave step
    @Bean
    public Step slaveStep() {
        return stepBuilderFactory.get("slaveStep")
                .<USZipCode, USZipCode>chunk(1000)
                .reader(itemReader(null, null))
                .processor(itemProcessor())
                .writer(itemWriter)
                .listener(new ChunkCountListener())
                .listener(new StepExecutionListener())
                .listener(new LogStepExecutionListener())
                .build();
    }

    @Bean
    public ColumnRangePartitioner partitioner() {

        ColumnRangePartitioner columnRangePartitioner = new ColumnRangePartitioner();
        columnRangePartitioner.setColumn("id");
        columnRangePartitioner.setDataSource(dataSource);
        columnRangePartitioner.setTable("testdb.us_zipcodes");
        return columnRangePartitioner;

    }

    @Bean
    public TaskExecutor asyncTaskExecutor() {
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
        taskExecutor.setConcurrencyLimit(70);
        return taskExecutor;
    }

    @Bean
    public USZipCodeProcessor itemProcessor() {
        return new USZipCodeProcessor();
    }

}