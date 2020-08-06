package com.example.demo.batch;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class BatchController {
  
  private final BatchRepository batchRepository;
  
  @GetMapping("/test")
  public String test() {
    log.info(batchRepository.findById((long) 1).toString());
    return "Success";
//    return "fail";
  }

}
