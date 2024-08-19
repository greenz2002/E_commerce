package com.example.demo.service;

import com.example.demo.dto.request.RoleRequest;
import com.example.demo.dto.response.RoleReponse;
import com.example.demo.maper.RoleMaper;
import com.example.demo.repository.PermissionRepository;
import com.example.demo.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private RoleMaper roleMaper;

    @Autowired
    private PermissionRepository permissionRepository;

    public List<RoleReponse> getAll() {
        return rolesRepository.findAll()
                .stream()
                .map(roleMaper::toRoleReponse)
                .toList();
    }

    public RoleReponse creatRole(RoleRequest request) {
        var roles = roleMaper.toRoles(request);
        var permission = permissionRepository.findAllById(request.getPermissions());
        roles.setPermissions(new HashSet<>(permission));

        roles = rolesRepository.save(roles);
        return roleMaper.toRoleReponse(roles);
    }

    public void delete(Integer id) {
        rolesRepository.deleteById(id);
    }
}
