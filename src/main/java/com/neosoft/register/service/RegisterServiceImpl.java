package com.neosoft.register.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neosoft.register.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class RegisterServiceImpl implements RegisterService {

    public String secretkey="hello";

    @Override
    public ResponseEntity<String> verify(Details details) throws JsonProcessingException {
        AuthReq authReq=new AuthReq();
        CardRequest cardRequest=new CardRequest();
        HeaderReq headerReq=new HeaderReq();
        authReq.setUserName("Ridhhi");
        authReq.setPassword("ri1709");
        headerReq.setAuthentication(authReq);
        cardRequest.setHeader(headerReq);
        ObjectMapper objectMapper=new ObjectMapper();
        String str=objectMapper.writeValueAsString(details);
        String  encryptedRequestString=Encryption.aesEncrypt(str,secretkey);
        EncryptedRequest encryptedRequest=new EncryptedRequest();
        encryptedRequest.setEncryptedRequest(encryptedRequestString);
        Map<String,EncryptedRequest> body1=new HashMap<>();
        String cardNo=details.getCardNo();
        if(cardNo == null) {
            body1.put("validCard", encryptedRequest);
        }else {
            body1.put("balanceCheck", encryptedRequest);
        }
        cardRequest.setBody(body1);
        String jsonStr =objectMapper.writeValueAsString(cardRequest);
        System.out.println(jsonStr);
        String uri = "http://10.0.102.185:8080/card/sendData";
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = new HttpEntity(jsonStr);
        try {
            ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
            CardVerifyResponse encryptedResponse=objectMapper.readValue(result.getBody(),CardVerifyResponse.class);
            String key=String.join("",encryptedResponse.getBody().keySet());
            EncryptedResponce encryptedResponse1= encryptedResponse.getBody().get(key);
            String encryptedString=Decryption.aesDecrypt(encryptedResponse1.getEncryptedResponce(),secretkey);
            return ResponseEntity.ok().body(encryptedString);
        }
        catch (Exception exception){
           return ResponseEntity.internalServerError().body(exception.getMessage());
        }
//        return ResponseEntity.ok("success");

    }
}
