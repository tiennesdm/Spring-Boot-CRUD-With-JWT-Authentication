package com.crud.api.response;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ErrorObject {
    private Integer statusCode;
	
	private String message;
	
	private Date timestamp;
	private Exception errorDetail;
	public void setErrorDetail(Exception error) {
		this.errorDetail = error;
	}
    
}
