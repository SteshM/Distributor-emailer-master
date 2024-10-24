package com.example.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

@Service
public class RestExchanger {
    @Value("${mail.validation.api}")
	String abstractApi;

    public ResponseEntity<JsonNode> getValidation(String email){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<JsonNode> responseEntity = restTemplate.exchange(abstractApi+email,HttpMethod.GET,requestEntity,JsonNode.class );

        return responseEntity;
    }
}
