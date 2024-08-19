package com.example.demo.dto.response;

import com.example.demo.entity.Roles;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserResponse {
    private String id;
    private String username;
    private String password;
    private String email;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    private Set<RoleReponse> roles;
}
