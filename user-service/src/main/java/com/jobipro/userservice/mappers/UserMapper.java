package com.jobipro.userservice.mappers;


import org.mapstruct.Mapper;

import com.jobipro.userservice.dtos.user.MeResponse;
import com.jobipro.userservice.enteties.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    MeResponse toResponse(User user);
}
