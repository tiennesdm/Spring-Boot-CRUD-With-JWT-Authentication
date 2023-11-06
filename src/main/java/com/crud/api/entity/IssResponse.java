package com.crud.api.entity;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public
class IssResponse {
    // Here the iss_position property in hte response is in cammel case,
    // with the @JsonProperty annotation we tell the parser to pass that 
    // property to the annotated field issPosition
    @JsonProperty("iss_position")
    private IssPosition issPosition;
    private String message;
    private Timestamp timestamp;

    // getters & setters
}
