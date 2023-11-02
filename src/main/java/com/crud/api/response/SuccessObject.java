package com.crud.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor


public class SuccessObject {
    private Integer statusCode;
	private Object data;
    private String message;
}
