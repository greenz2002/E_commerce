package com.example.demo.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class AuthenicationRequest {
    private String username;
    private String password;

}
