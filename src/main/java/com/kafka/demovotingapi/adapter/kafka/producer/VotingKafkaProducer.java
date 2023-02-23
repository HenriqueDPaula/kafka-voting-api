package com.kafka.demovotingapi.adapter.kafka.producer;

import com.kafka.demovotingapi.entity.dto.VotingDTO;
import com.kafka.demovotingapi.schema.Voting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class VotingKafkaProducer extends KafkaAbstractProducer<Voting> {

  @Autowired
  public VotingKafkaProducer(KafkaTemplate<String, Voting> producer, @Value("${spring.kafka.producer.topic}") String topic,
                             @Value("${spring.kafka.producer.groupId}") String groupId) {
    super(producer, topic, groupId);
  }

  public void execute(VotingDTO votingDTO) {
    Voting voting = buildVoting(votingDTO);
    this.send(voting);
  }

  private Voting buildVoting(VotingDTO dto) {
    return Voting.newBuilder()
        .setName(dto.getName())
        .setDate(dto.setCreatedDate()).build();
  }
}
