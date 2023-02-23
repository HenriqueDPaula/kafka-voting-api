package com.kafka.demovotingapi.config.kafka.consumer;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroSerializer;

@Configuration
@ConfigurationProperties(
    prefix = "spring.kafka.consumer"
)
@Data
@NoArgsConstructor
public class KafkaConsumerProperties {

  private String groupId;

  private String bootstrapServer;

  private String enableAutoCommit;

  private String autoOffsetReset;

  private String schemaRegistryUrl;

  private String specificAvroReader;

  private final Class<?> keySerializer = StringDeserializer.class;

  private final Class<?> valueSerializer = KafkaAvroDeserializer.class;
}
