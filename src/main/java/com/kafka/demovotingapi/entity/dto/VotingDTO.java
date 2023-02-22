package com.kafka.demovotingapi.entity.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VotingDTO {

  @NotBlank(message = "name must not be empty")
  private String name;
  private String votingDate;

  public String setCreatedDate() {
    this.votingDate = LocalDateTime.now().toString();
    return this.votingDate;
  }
}
