package com.example.demo.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserCreateRequest {
    @NotBlank( message = "USERNAME_INVAILD")
    private String username;
    @NotBlank(message = "PASSWORD_INVAILD2")
    @Size(max = 9, message = "PASSWORD_INVAILD1")
    private String password;
    private String email;
    private LocalDateTime created_at;
    private Set<String> roles;
    public LocalDateTime getCreated_at() {
        setCreated_at(LocalDateTime.now());
        return created_at;
    }
//    public LocalDateTime getUpdated_at() {
//        setUpdated_at(LocalDateTime.now());
//        return updated_at;
//    }
}
