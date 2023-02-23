package com.kafka.demovotingapi.adapter.kafka.consumer;

import com.kafka.demovotingapi.schema.Voting;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class VotingKafkaConsumer {

  @KafkaListener(
      groupId = "${spring.kafka.consumer.groupId}",
      topics = "${spring.kafka.consumer.topic}",
      containerFactory = "concurrentContainerFactory"
  )
  public void consume(final ConsumerRecord<String, Voting> consumerRecord, Acknowledgment ack) {
    try {
      log.info("m=consume l=RECEIVED message={} topic={} offset={}", consumerRecord.value(), consumerRecord.topic(), consumerRecord.offset());

      Voting voting = consumerRecord.value();
      // Persist

      log.info("m=consume l=voting name={}, date={}", voting.getName(), voting.getDate());

      ack.acknowledge();
    } catch (Exception e) {
      log.error("m=consume l=CONSUMER_FAIL e={}", e);
    }
  }

}
