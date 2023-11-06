package com.crud.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.crud.api.entity.IssResponse;
import com.crud.api.response.ErrorObject;
import com.crud.api.response.SuccessObject;

@RestController
@RequestMapping("/geoLocation")

public class RestApi {

    @GetMapping("")
    public ResponseEntity<Object> getFootBallDetails() {
        try {
            String uri = "http://api.open-notify.org/iss-now.json";
            RestTemplate restTemplate = new RestTemplate();
            IssResponse response = restTemplate.getForObject(uri, IssResponse.class);
            // We get te response as an IssResponse object
            IssResponse result = response;

            SuccessObject successObject = new SuccessObject(200, result, "Third Party API call");
            return new ResponseEntity<>(successObject, HttpStatus.OK);
        } catch (Exception error) {
            ErrorObject errorObject = new ErrorObject(409, "Bad Credentials", null, null);
            return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
        }

    }
}
