package com.example.demo.controller;

import com.example.demo.dto.request.PermissionRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.PermissionReponse;
import com.example.demo.service.PermissionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@Slf4j
public class PermissionController {
    @Autowired
    PermissionService permissionService;

    @PostMapping("/add")
    ApiResponse<PermissionReponse> creatUser(@Valid @RequestBody PermissionRequest request) {
        return ApiResponse.<PermissionReponse>builder()
                .result(permissionService.create(request))
                .build();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") Integer id) {
        permissionService.delete(id);
    }

    @GetMapping
    public ApiResponse<List<PermissionReponse>> getAll() {
        return ApiResponse.<List<PermissionReponse>>builder()
                .result(permissionService.getAll())
                .build();
    }

}
