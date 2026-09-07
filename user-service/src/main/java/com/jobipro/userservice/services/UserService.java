package com.jobipro.userservice.services;

import com.jobipro.userservice.abstracts.UserServiceContract;
import com.jobipro.userservice.dtos.user.LoginRequest;
import com.jobipro.userservice.dtos.user.LoginResponse;
import com.jobipro.userservice.dtos.user.RegisterRequest;
import com.jobipro.userservice.dtos.user.RegisterResponse;
import com.jobipro.userservice.enteties.User;
import com.jobipro.userservice.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceContract {
    final private UserRepo userRepo;


    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {

        return null;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        return null;
    }
}
