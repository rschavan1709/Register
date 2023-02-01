package com.neosoft.register.model;


import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Card {

    private String cardNo;
    private long mobileNo;
    private String dob;
    private String username;
    private String cardType;
    private double balance;


}