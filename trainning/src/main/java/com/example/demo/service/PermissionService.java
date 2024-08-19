package com.example.demo.service;

import com.example.demo.dto.request.PermissionRequest;
import com.example.demo.dto.response.PermissionReponse;
import com.example.demo.entity.Permission;
import com.example.demo.maper.PermissionMaper;
import com.example.demo.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    PermissionMaper permissionMaper;

    public PermissionReponse create(PermissionRequest request) {
        Permission permission = permissionMaper.toPermission(request);
        permission = permissionRepository.save(permission);
        return permissionMaper.toPermissionReponse(permission);
    }

    public List<PermissionReponse> getAll() {
        return permissionRepository.findAll()
                .stream()
                .map(permissionMaper::toPermissionReponse)
                .toList();
    }

    public void delete(Integer id) {
        permissionRepository.deleteById(id);
    }
}
