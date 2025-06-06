package com.sale.user.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class TestDto {

    @Id @GeneratedValue
    private String username;
    private int age;
}
