package com.example.demo.db;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table( name = "TEST_TABLE_BATCH")
public class TestEntity {
  
  @Id
  private String id;
  
  @Column(name = "TEST_PARAMETER")
  private String parameter;
  
  @Column(name = "CREATEDATETIME")
  private LocalDateTime localDateTime;
}
