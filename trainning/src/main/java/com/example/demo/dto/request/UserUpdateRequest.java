package com.example.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserUpdateRequest {
    private String username;
    private String password;
    private String email;
    private LocalDateTime updated_at;

    public LocalDateTime getUpdated_at() {
        setUpdated_at(LocalDateTime.now());
        return updated_at;
    }
}
