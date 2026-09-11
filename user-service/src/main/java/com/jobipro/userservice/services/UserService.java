package com.jobipro.userservice.services;

import com.jobipro.userservice.abstracts.StorageServiceContract;
import com.jobipro.userservice.abstracts.UserServiceContract;
import com.jobipro.userservice.dtos.user.MeResponse;
import com.jobipro.userservice.dtos.user.CreateUserRequest;
import com.jobipro.userservice.dtos.user.storage.ProfilePhotoUploadRequest;
import com.jobipro.userservice.dtos.user.storage.UploadUrlResponse;
import com.jobipro.userservice.enteties.Sector;
import com.jobipro.userservice.enteties.User;
import com.jobipro.userservice.enteties.Worker;
import com.jobipro.userservice.enteties.WorkerSector;
import com.jobipro.userservice.enums.UserStatus;
import com.jobipro.userservice.exceptions.ResourceAlreadyExist;
import com.jobipro.userservice.exceptions.ResourceNotFound;
import com.jobipro.userservice.mappers.UserMapper;
import com.jobipro.userservice.repositories.SectorRepo;
import com.jobipro.userservice.repositories.UserRepo;
import com.jobipro.userservice.repositories.WorkerRepo;
import com.jobipro.userservice.repositories.WorkerSectorRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceContract {
    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final WorkerRepo workerRepo;
    private final WorkerSectorRepo workerSectorRepo;
    private final SectorRepo sectorRepo;
    private final StorageServiceContract storageServiceContract;

    @Override
    public MeResponse me(String keycloakId) {
        User user = userRepo.findByKeycloakId(keycloakId).orElseThrow(() -> new ResourceNotFound("User not found"));
        return userMapper.toResponse(user);
    }

    @Override
    @Transactional
    public MeResponse createUser(CreateUserRequest createUserRequest, String keycloakId, String email, String fullName) {
        //User creation

        if (userRepo.existsByKeycloakId(keycloakId)) {
            throw new ResourceAlreadyExist("User Already exist");
        }
        User user = userCreation(keycloakId, email, fullName);

        if (createUserRequest.isWorker()) {
            //Worker Creation
            Worker worker = workerCreation(createUserRequest, user);

            //Sectors_User Creation
            createUserRequest.sectorsId().forEach(sectorId -> {
                workerSectorCreation(worker, sectorId);
            });

        }

        return userMapper.toResponse(user);
    }

    private void workerSectorCreation(Worker worker, Long sectorId) {
        Sector sector = sectorRepo.findById(sectorId)
                .orElseThrow(() -> new ResourceNotFound("Sector not found"));

        WorkerSector workerSector = new WorkerSector();
        workerSector.setSector(sector);
        workerSector.setWorker(worker);
        workerSectorRepo.save(workerSector);

    }

    private Worker workerCreation(CreateUserRequest createUserRequest, User user) {
        Worker worker = new Worker();
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
    public UploadUrlResponse generateProfilePhotoUploadUrl(String keycloakId, ProfilePhotoUploadRequest request) {

        User user = userRepo.findByKeycloakId(keycloakId).orElseThrow(() -> new ResourceNotFound("User not found"));

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
}
