package com.jobipro.userservice.controllers;


import com.jobipro.userservice.dtos.user.CreateUserRequest;
import com.jobipro.userservice.dtos.user.MeResponse;
import com.jobipro.userservice.dtos.user.storage.ProfilePhotoUploadRequest;
import com.jobipro.userservice.dtos.user.storage.UploadUrlResponse;
import com.jobipro.userservice.services.UserService;
import com.jobipro.userservice.shared.GlobalResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    final private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<GlobalResponse<MeResponse>> me(@AuthenticationPrincipal Jwt jwt) {
        String keycloakId = jwt.getSubject();

        return GlobalResponse.success(
                userService.me(keycloakId),
                "User found",
                HttpStatus.OK
        );
    }

    @PostMapping("/me/photo/upload-url")

    public ResponseEntity<GlobalResponse<UploadUrlResponse>> generateProfilePhotoUploadUrl(
            @Valid @RequestBody ProfilePhotoUploadRequest profilePhotoUploadRequest,
            @AuthenticationPrincipal Jwt jwt
    ) {
        return GlobalResponse.success(
                userService.generateProfilePhotoUploadUrl(jwt.getSubject(), profilePhotoUploadRequest),
                "Url generated Successfully",
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<GlobalResponse<MeResponse>> createUser(@Valid @RequestBody CreateUserRequest createUserRequest,
                                                                 @AuthenticationPrincipal Jwt jwt) {


        String email = jwt.getClaimAsString("email");
        String keycloakId = jwt.getSubject();
        String fullName = jwt.getClaimAsString("given_name").concat(" ") + jwt.getClaimAsString("family_name");

        MeResponse meResponse = userService.createUser(createUserRequest, keycloakId, email, fullName);

        return GlobalResponse.success(
                meResponse,
                "User Created Succefully",
                HttpStatus.CREATED

        );

    }
}
