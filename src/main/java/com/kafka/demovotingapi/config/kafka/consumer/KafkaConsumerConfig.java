package com.kafka.demovotingapi.config.kafka.consumer;

import java.util.Map;

import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

  @Autowired
  private KafkaConsumerProperties kafkaConsumerProperties;

  @Bean
  public ConsumerFactory<String, SpecificRecord> consumerFactory() {
    return new DefaultKafkaConsumerFactory<>(
        Map.of(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConsumerProperties.getBootstrapServer(),
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaConsumerProperties.getKeySerializer().getName(),
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaConsumerProperties.getValueSerializer().getName(),
            ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, kafkaConsumerProperties.getEnableAutoCommit(),
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaConsumerProperties.getAutoOffsetReset(),
            KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, kafkaConsumerProperties.getSpecificAvroReader(),
            KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, kafkaConsumerProperties.getSchemaRegistryUrl()
        )
    );
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, SpecificRecord> concurrentContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, SpecificRecord> listener = new ConcurrentKafkaListenerContainerFactory<>();

    listener.setConsumerFactory(consumerFactory());
    listener.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
    listener.getContainerProperties().setSyncCommits(Boolean.TRUE);

    return listener;
  }

}
