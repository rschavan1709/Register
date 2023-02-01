package com.neosoft.register.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CardRequest {

    @JsonProperty("header")
    private HeaderReq header;

    @JsonProperty("body")
    private Map<String,EncryptedRequest> body;
}
