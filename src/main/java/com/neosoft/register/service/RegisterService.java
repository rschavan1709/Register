package com.neosoft.register.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.neosoft.register.model.Details;
import org.springframework.http.ResponseEntity;

public interface RegisterService {
    ResponseEntity<String> verify(Details details) throws JsonProcessingException;
}
