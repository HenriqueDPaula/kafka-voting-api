package com.kafka.demovotingapi.adapter.kafka.producer;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KafkaAbstractProducer<E> {

  private final KafkaTemplate<String, E> kafkaTemplate;
  private final String topic;
  private final String groupId;

  public KafkaAbstractProducer(KafkaTemplate<String, E> kafkaTemplate, String topic, String groupId) {
    this.kafkaTemplate = kafkaTemplate;
    this.topic = topic;
    this.groupId = groupId;
  }

  /**
   * Send to kafka with listener
   * @param e abstract message
   */
  public void send(E e) {

    ListenableFuture<SendResult<String, E>> future = kafkaTemplate.send(createProducerRecord(e));
    future.addCallback(new ListenableFutureCallback<>() {

      @Override
      public void onSuccess(final SendResult<String, E> message) {
        log.info("m=send l=SUCCESS_SEND_MESSAGE message: {}, offset: {}", message, message.getRecordMetadata().offset());
      }

      @Override
      public void onFailure(final Throwable throwable) {
        log.error("m=send l=FAILED_SEND_MESSAGE message: {}, e={}", e, throwable);
      }
    });
  }

  private ProducerRecord<String, E> createProducerRecord(E e) {
    return new ProducerRecord<>(topic, groupId, e);
  }
}
