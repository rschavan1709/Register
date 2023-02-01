package com.neosoft.register.model;


import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Response {
    private String success;
    private String error;
    private Card card;

}

