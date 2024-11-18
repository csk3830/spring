package com.ezen.spring.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthVO {
    private int id;
    private String email;
    private String auth;
}
