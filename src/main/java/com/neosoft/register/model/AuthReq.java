package com.neosoft.register.model;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AuthReq {
    private String userName;
    private String password;
}
