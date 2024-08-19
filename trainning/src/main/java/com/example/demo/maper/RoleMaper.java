package com.example.demo.maper;

import com.example.demo.dto.request.RoleRequest;
import com.example.demo.dto.response.RoleReponse;
import com.example.demo.entity.Roles;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface RoleMaper {
    @Mapping(target = "permissions", ignore = true)
    Roles toRoles(RoleRequest roleRequest);
    RoleReponse toRoleReponse(Roles roles);
}
