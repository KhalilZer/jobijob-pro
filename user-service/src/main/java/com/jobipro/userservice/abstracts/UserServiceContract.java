package com.jobipro.userservice.abstracts;

import com.jobipro.userservice.dtos.user.LoginRequest;
import com.jobipro.userservice.dtos.user.LoginResponse;
import com.jobipro.userservice.dtos.user.RegisterRequest;
import com.jobipro.userservice.dtos.user.RegisterResponse;

public interface UserServiceContract {
    RegisterResponse register(RegisterRequest registerRequest);

    LoginResponse login(LoginRequest loginRequest);
}
