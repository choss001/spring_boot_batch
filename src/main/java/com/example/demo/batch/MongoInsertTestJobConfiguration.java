package com.example.demo.batch;

import java.util.HashMap;
import java.util.Map;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import com.example.demo.db.SmartChoiceEntity;
import com.example.demo.db.SmartChoiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class MongoInsertTestJobConfiguration {
  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;
  private final SmartChoiceRepository smartChoiceRepository;
  private final MongoTemplate mongoTemplate;

  private int chunkSize = 10;

  @Bean
  public Job mongoInsertTestJob() {
    return jobBuilderFactory.get("mongoInsertTestJob").start(mongoInsertTestStep()).build();
  }

  @Bean
  public Step mongoInsertTestStep() {
    return stepBuilderFactory.get("mongoInsertTestStep")
        .<SmartChoiceEntity, SmartChoiceEntity>chunk(chunkSize)
        .reader(mongoItemReader())
        .writer(mongoItemWriter()).build();
  }

  @Bean
  public ItemReader<SmartChoiceEntity> mongoItemReader(){
    MongoItemReader<SmartChoiceEntity> mongoItemReader = new MongoItemReader<>();
    mongoItemReader.setTemplate(mongoTemplate);
    mongoItemReader.setCollection("SmartChoice");
    mongoItemReader.setQuery("{}");
    Map<String, Sort.Direction> sort = new HashMap<>();
    sort.put("status", Sort.Direction.ASC);
    mongoItemReader.setSort(sort);
    mongoItemReader.setTargetType(SmartChoiceEntity.class);
      
    try {
      
      log.info(">>>>>>>>>>>>>>>>mongoItemReader = {}", mongoItemReader.read().toString());
    }catch(Exception Exce) {
      
    }
    return mongoItemReader;
  }
  
  
  @Bean 
  public ItemWriter<SmartChoiceEntity> mongoItemWriter(){
    return list -> {
      for (SmartChoiceEntity temp: list) {
        log.info("Write Curren SmartChoise = {}", temp);
      }
    };
  }
}
