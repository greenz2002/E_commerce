package com.example.demo.controller;

import com.example.demo.dto.request.AuthenicationRequest;
import com.example.demo.dto.request.UserCreateRequest;
import com.example.demo.dto.request.UserUpdateRequest;
import com.example.demo.dto.request.introspecRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.AuthenticationResponse;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.dto.response.introspecResponse;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable("id") Long id) {
        return userService.getByID(id);
    }

    @PostMapping("/add")
    ApiResponse<User> creatUser(@Valid @RequestBody UserCreateRequest request) {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createUser(request));
        return apiResponse;
    }

    @PutMapping("/update/{id}")
    public UserResponse updateUser(@RequestBody UserUpdateRequest request, @PathVariable("id") Long id) {
        return userService.updateUser(request, id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> login(@RequestBody AuthenicationRequest request) {
        var  result = userService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/introspec")
    ApiResponse<introspecResponse> login(@RequestBody introspecRequest request) throws ParseException, JOSEException {
        var  result = userService.introspec(request);
        return ApiResponse.<introspecResponse>builder()
                .result(result)
                .build();
    }
}
