package com.example.demo.controller;

import com.example.demo.dto.request.RoleRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.RoleReponse;
import com.example.demo.service.RoleService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@Slf4j
public class RoleController {

    @Autowired
    RoleService roleService;

    @PostMapping("/add")
    ApiResponse<RoleReponse> creatUser(@Valid @RequestBody RoleRequest request) {
        return ApiResponse.<RoleReponse>builder()
                .result(roleService.creatRole(request))
                .build();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") Integer id) {
        roleService.delete(id);
    }

    @GetMapping
    public ApiResponse<List<RoleReponse>> getAll() {
        return ApiResponse.<List<RoleReponse>>builder()
                .result(roleService.getAll())
                .build();
    }
}
