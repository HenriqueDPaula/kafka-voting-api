package com.kafka.demovotingapi.controller;

import javax.validation.Valid;

import com.kafka.demovotingapi.adapter.kafka.VotingKafkaProducer;
import com.kafka.demovotingapi.entity.dto.VotingDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VotingController {

  @Autowired
  private VotingKafkaProducer votingKafkaProducer;

  @PostMapping(value = "/voting")
  public ResponseEntity<VotingDTO> vote(@RequestBody @Valid VotingDTO votingDTO) {
    this.votingKafkaProducer.execute(votingDTO);
    return new ResponseEntity<>(votingDTO, HttpStatus.OK);
  }
}
