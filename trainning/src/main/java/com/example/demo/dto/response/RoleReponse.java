package com.example.demo.dto.response;

import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class RoleReponse {
    private Integer id;
    private String name;
    private String descriptions;
    private Set<PermissionReponse> permissions;
}
