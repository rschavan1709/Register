package com.neosoft.register.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.neosoft.register.model.Details;
import com.neosoft.register.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/card")
public class RegisterController {

    @Autowired
    RegisterService registerService;

    @PostMapping("/validCard")
    public ResponseEntity<String> verify(@RequestBody Details details) throws JsonProcessingException {
        return registerService.verify(details);
    }

    @PostMapping("/balanceCheck")
    public ResponseEntity<String> balanceCheck(@RequestBody Details details) throws JsonProcessingException {
        return registerService.verify(details);
    }


}
