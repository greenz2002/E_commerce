package com.example.demo.maper;

import com.example.demo.dto.request.UserCreateRequest;
import com.example.demo.dto.request.UserUpdateRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface UserMaper {
     Users toUser(UserCreateRequest request);
     UserResponse userResponse(Users users);
     void updateUser(@MappingTarget Users users, UserUpdateRequest request);
}
