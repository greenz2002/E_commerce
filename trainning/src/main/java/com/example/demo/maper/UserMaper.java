package com.example.demo.maper;

import com.example.demo.dto.request.UserCreateRequest;
import com.example.demo.dto.request.UserUpdateRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface UserMaper {
     User toUser(UserCreateRequest request);
     UserResponse userResponse(User user);
     void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
