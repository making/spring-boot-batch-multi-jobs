package com.example;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * Created by makit on 西暦17/01/27.
 */
@Configuration
class JobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public JobConfiguration(JobBuilderFactory jobBuilderFactory,
                            StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("Tasklet has run");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    Step step2() {
        return stepBuilderFactory.get("step2").<String, String>chunk(3)
                .reader(new ListItemReader<>(
                        Arrays.asList("1", "2", "3", "4", "5", "6")))
                .processor(item -> String.valueOf(Integer.parseInt(item) * -1))
                .writer(items -> {
                    for (String item : items) {
                        System.out.println(">> " + item);
                    }
                }).build();
    }

    @Bean
    Job job() {
        return this.jobBuilderFactory.get("job").incrementer(new RunIdIncrementer())
                .start(step1()).next(step2()).build();
    }
}
