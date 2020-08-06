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
import org.springframework.batch.item.data.MongoItemWriter;
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
    
    return mongoItemReader;
  }
  
//  @Bean
//  public ItemProcessor<SmartChoiceEntity, SmartChoiceEntity> mongoItemProcessor(){
//    SmartChoiceEntity smartChoiceEntity = new SmartChoiceEntity();
//    smartChoiceEntity.setNum(1000);
//    smartChoiceEntity.setImage("/smc/resources/upload/2018/7/3/SM-G925.jpg");
//    smartChoiceEntity.setDevice_nm("갤럭시 S6엣지 (32GB) ");
//    smartChoiceEntity.setDevice_info("제조사 : 삼성전자  |  모델명 : SM-G925  |  출시일 : 2015년 04월");
//    smartChoiceEntity.setHigh_price("161,100원");
//    smartChoiceEntity.setMiddle_price("132,100원");
//    smartChoiceEntity.setRow_price("103,100원");
//    for(int i = 1; i <= 10000000; i++) {
//      
//    }
//    smartChoiceEntity.setId()
//
//    
//  }
  
  
  @Bean 
  public ItemWriter<SmartChoiceEntity> mongoItemWriter(){
    MongoItemWriter<SmartChoiceEntity> mongoItemWriter = new MongoItemWriter<>();
    mongoItemWriter.setTemplate(mongoTemplate);
    mongoItemWriter.setCollection("batchTest");
    return mongoItemWriter;
  }
}
