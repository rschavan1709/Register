package com.neosoft.register.model;

import lombok.*;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CardVerifyResponse {
    Map<String, EncryptedResponce> body;
}
