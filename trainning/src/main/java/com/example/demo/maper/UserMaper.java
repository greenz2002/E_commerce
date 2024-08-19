package com.example.demo.maper;

import com.example.demo.dto.request.UserCreateRequest;
import com.example.demo.dto.request.UserUpdateRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface UserMaper {
     @Mapping(target = "roles" , ignore = true)
     Users toUser(UserCreateRequest request);
     UserResponse userResponse(Users users);
     @Mapping(target = "roles" , ignore = true)
     void updateUser(@MappingTarget Users users, UserUpdateRequest request);
}
