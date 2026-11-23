package com.jobipro.userservice.services;

import com.jobipro.common.UserRole;
import com.jobipro.common.dto.internal.InternalUserRequestDto;
import com.jobipro.common.dto.internal.InternalUserResponseDto;
import com.jobipro.common.dto.internal.InternalWorkerResponse;
import com.jobipro.common.exceptions.ResourceAlreadyExist;
import com.jobipro.common.exceptions.ResourceNotFound;
import com.jobipro.common.storage.StorageServiceContract;
import com.jobipro.userservice.abstracts.UserContract;
import com.jobipro.userservice.dtos.user.*;
import com.jobipro.common.dto.storage.PhotoUploadRequest;
import com.jobipro.common.dto.storage.UploadUrlResponse;
import com.jobipro.userservice.enteties.Sector;
import com.jobipro.userservice.enteties.User;
import com.jobipro.userservice.enteties.Worker;
import com.jobipro.userservice.enteties.WorkerSector;
import com.jobipro.userservice.helpers.ReturnMeResponseHelper;
import com.jobipro.userservice.mappers.UserMapper;
import com.jobipro.userservice.mappers.WorkerMapper;
import com.jobipro.userservice.repositories.SectorRepo;
import com.jobipro.userservice.repositories.UserRepo;
import com.jobipro.userservice.repositories.WorkerRepo;
import com.jobipro.userservice.repositories.WorkerSectorRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService implements UserContract {
    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final WorkerRepo workerRepo;
    private final WorkerSectorRepo workerSectorRepo;
    private final SectorRepo sectorRepo;
    private final StorageServiceContract storageServiceContract;
    private final ReturnMeResponseHelper returnMeResponseHelper;
    private final WorkerMapper workerMapper;
    private final KeycloakService keycloakService;

    @Override
    public MeResponse me(String keycloakId) {
        com.jobipro.userservice.enteties.User user = userRepo.findByKeycloakId(keycloakId).orElseThrow(() -> new ResourceNotFound("UserService not found"));
        Worker worker = workerRepo.findByUser(user).orElse(null);

        return returnMeResponseHelper.returnMeResponse(user, worker);
    }

    @Override
    @Transactional
    public MeResponse createUser(
            CreateUserRequest request,
            String keycloakId,
            String email,
            String fullName
    ) {
        if (userRepo.existsByKeycloakId(keycloakId)) {
            throw new ResourceAlreadyExist("UserService already exists");
        }

        com.jobipro.userservice.enteties.User user = userCreation(keycloakId, email, fullName);

        Worker worker = null;

        if (request.isWorker()) {

            user.setRole(UserRole.WORKER);
            userRepo.save(user);

            worker = workerCreation(request, user);

            for (Long sectorId : request.sectorsId()) {
                workerSectorCreation(worker, sectorId);
            }

            keycloakService.assignRole(
                    keycloakId,
                    UserRole.WORKER
            );
        }

        return returnMeResponseHelper.returnMeResponse(user, worker);
    }

    private void workerSectorCreation(Worker worker, Long sectorId) {
        Sector sector = sectorRepo.findById(sectorId)
                .orElseThrow(() -> new ResourceNotFound("SectorService not found"));

        WorkerSector workerSector = new WorkerSector();
        workerSector.setSector(sector);
        workerSector.setWorker(worker);
        workerSectorRepo.save(workerSector);

    }

    private Worker workerCreation(CreateUserRequest createUserRequest, User user) {
        Worker worker = new Worker();
        worker.setAddress(createUserRequest.address());
        worker.setCity(createUserRequest.city());
        worker.setCountryCode(createUserRequest.countryCode());
        worker.setPostalCode(createUserRequest.postalCode());
        worker.setLongitude(createUserRequest.longitude());
        worker.setLatitude(createUserRequest.latitude());
        worker.setUser(user);
        return workerRepo.save(worker);
    }

    private User userCreation(String keycloakId, String email, String fullName) {
        User user = new User();
        user.setKeycloakId(keycloakId);
        user.setEmail(email);
        user.setFullName(fullName);
        return userRepo.save(user);

    }


    @Override
    public UploadUrlResponse generateProfilePhotoUploadUrl(String keycloakId, PhotoUploadRequest request) {

        User user = userRepo.findByKeycloakId(keycloakId).orElseThrow(() -> new ResourceNotFound("UserService not found"));

        String extension = switch (request.contentType()) {
            case "image/jpeg" -> "jpg";
            case "image/png" -> "png";
            case "image/webp" -> "webp";
            default -> throw new IllegalArgumentException("Unsupported image type");
        };

        String objectKey =
                "users/" + user.getPublicId() + "/profile." + extension;

        String uploadUrl = storageServiceContract.generateUploadUrl(
                objectKey,
                request.contentType()
        );

        return new UploadUrlResponse(uploadUrl, objectKey);

    }

    @Override
    public MeResponse updateUser(String publicId, UpdateUserRequest updateUserRequest) {

        User user = userRepo.findByPublicId(publicId).orElseThrow(() -> new ResourceNotFound("UserService not found"));

        userMapper.updateRequestToUser(updateUserRequest, user);
        userRepo.save(user);

        Worker worker = workerRepo.findByUser(user).orElse(null);
        if (worker != null) {

            if (updateUserRequest.address() != null) {
                worker.setAddress(updateUserRequest.address());
            }

            if (updateUserRequest.city() != null) {
                worker.setCity(updateUserRequest.city());
            }

            if (updateUserRequest.postalCode() != null) {
                worker.setPostalCode(updateUserRequest.postalCode());
            }

            if (updateUserRequest.countryCode() != null) {
                worker.setCountryCode(updateUserRequest.countryCode());
            }

            if (updateUserRequest.latitude() != null) {
                worker.setLatitude(updateUserRequest.latitude());
            }

            if (updateUserRequest.longitude() != null) {
                worker.setLongitude(updateUserRequest.longitude());
            }

            if (updateUserRequest.description() != null) {
                worker.setDescription(updateUserRequest.description());
            }

            workerRepo.save(worker);
        }
        return returnMeResponseHelper.returnMeResponse(user, worker);
    }


    //Internal Client functions
    @Override
    public InternalUserResponseDto meInternal(String keycloakId) {

        User user = userRepo.findByKeycloakId(keycloakId)
                .orElseThrow(() ->
                        new ResourceNotFound("User not found")
                );

        InternalWorkerResponse worker = null;

        if (user.getRole() == UserRole.WORKER) {
            worker = workerRepo.findByUser(user)
                    .map(workerMapper::toInternalWorkerResponse)
                    .orElse(null);
        }

        return userMapper.toUserInternalDto(user, worker);
    }

    @Override
    public InternalUserResponseDto createInternalUser(InternalUserRequestDto internalCreateUserDto) {

        // 1. Create Keycloak account
        String keycloakId = keycloakService.createUser(
                internalCreateUserDto.email(),
                internalCreateUserDto.firstName(),
                internalCreateUserDto.lastName()
        );

        // 2. Assign role in Keycloak
        keycloakService.assignRole(
                keycloakId,
                internalCreateUserDto.role()
        );

        // 3. Create local UserService
        User user = new User();

        user.setKeycloakId(keycloakId);
        user.setFullName(internalCreateUserDto.firstName() + " " + internalCreateUserDto.lastName());
        user.setEmail(internalCreateUserDto.email());
        user.setPhone(internalCreateUserDto.phone());
        user.setRole(internalCreateUserDto.role());

        userRepo.save(user);

        return userMapper.toUserInternalDto(user, null);
    }


}
