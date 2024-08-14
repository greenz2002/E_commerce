package com.example.demo.maper;

import com.example.demo.dto.request.PermissionRequest;
import com.example.demo.dto.response.PermissionReponse;
import com.example.demo.entity.Permission;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface PermissionMaper {
    Permission toPermission(PermissionRequest permissionRequest);
    PermissionReponse toPermissionReponse(Permission permission);
}
