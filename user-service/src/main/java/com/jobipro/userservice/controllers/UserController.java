package com.jobipro.userservice.controllers;


import com.jobipro.common.dto.internal.InternalUserRequestDto;
import com.jobipro.common.dto.internal.InternalUserResponseDto;
import com.jobipro.common.response.GlobalResponse;
import com.jobipro.userservice.dtos.user.*;
import com.jobipro.userservice.dtos.user.storage.ProfilePhotoUploadRequest;
import com.jobipro.userservice.dtos.user.storage.UploadUrlResponse;
import com.jobipro.userservice.services.UserService;
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
                "User Created Successfully",
                HttpStatus.CREATED

        );

    }

    @PatchMapping("/{publicId}")
    public ResponseEntity<GlobalResponse<MeResponse>> updateUser(@Valid @RequestBody UpdateUserRequest updateUserRequest,
                                                                 @PathVariable String publicId) {
        return GlobalResponse.success(
                userService.updateUser(publicId, updateUserRequest),
                "User Updated Successfully",
                HttpStatus.CREATED
        );
    }

    //Internal Client Endpoints

    @GetMapping("/internal/me")
    public ResponseEntity<GlobalResponse<InternalUserResponseDto>> getMeInternal(@AuthenticationPrincipal Jwt jwt) {

        return GlobalResponse.success(
                userService.meInternal(jwt.getSubject()),
                "User Updated Successfully",
                HttpStatus.CREATED
        );
    }

    @PostMapping("/internal/users")
    public ResponseEntity<GlobalResponse<InternalUserResponseDto>> createInternalUser(
            @RequestBody InternalUserRequestDto createInternalUser
    ) {
        return GlobalResponse.success(
                userService.createInternalUser(createInternalUser),
                "User Created Successfully",
                HttpStatus.CREATED
        );
    }


}
