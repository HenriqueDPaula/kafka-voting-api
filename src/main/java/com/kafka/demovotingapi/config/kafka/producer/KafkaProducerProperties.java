package com.kafka.demovotingapi.config.kafka.producer;

import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import lombok.NoArgsConstructor;

import io.confluent.kafka.serializers.KafkaAvroSerializer;

@Configuration
@ConfigurationProperties(
    prefix = "spring.kafka.producer"
)
@Data
@NoArgsConstructor
public class KafkaProducerProperties {

  private String bootstrapServer;
  private String acksConfig;
  private String retriesConfig;
  private final Class<?> keySerializer = StringSerializer.class;
  private final Class<?> valueSerializer = KafkaAvroSerializer.class;
  private String schemaRegistryUrl;
}
