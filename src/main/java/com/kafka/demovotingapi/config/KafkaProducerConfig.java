package com.kafka.demovotingapi.config;

import java.util.Map;

import com.kafka.demovotingapi.schema.Voting;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import lombok.extern.slf4j.Slf4j;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;

@Configuration
@Slf4j
public class KafkaProducerConfig {

  private KafkaProperties kafkaProperties;

  public KafkaProducerConfig(KafkaProperties kafkaProperties) {
    this.kafkaProperties = kafkaProperties;
  }

  @Bean
  public ProducerFactory<String, Voting> configureProducer() {
    return new DefaultKafkaProducerFactory<>(Map.of(
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServer(),
        ProducerConfig.ACKS_CONFIG, kafkaProperties.getAcksConfig(),
        ProducerConfig.RETRIES_CONFIG, kafkaProperties.getRetriesConfig(),
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaProperties.getKeySerializer().getName(),
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaProperties.getValueSerializer().getName(),
        AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, kafkaProperties.getSchemaRegistryUrl()
    ));
  }

  @Bean
  public KafkaTemplate kafkaTemplate() {
    return new KafkaTemplate<>(configureProducer());
  }
}
