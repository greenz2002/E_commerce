package com.example.demo.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class AuthenticationResponse {
    private String token;
    private boolean authentication;
}
