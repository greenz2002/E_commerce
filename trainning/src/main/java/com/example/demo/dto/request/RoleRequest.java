package com.example.demo.dto.request;

import com.example.demo.entity.Permission;
import com.example.demo.entity.Roles;
import lombok.*;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class RoleRequest {
    private String name;
    private String descriptions;
    private Set<Integer> permissions;
}
