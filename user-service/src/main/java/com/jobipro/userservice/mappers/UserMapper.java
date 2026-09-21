package com.jobipro.userservice.mappers;

import com.jobipro.common.dto.internal.InternalUserResponseDto;
import com.jobipro.userservice.dtos.user.MeResponse;
import com.jobipro.userservice.dtos.user.UpdateUserRequest;
import com.jobipro.userservice.dtos.user.UserResponse;
import com.jobipro.userservice.dtos.worker.WorkerResponse;
import com.jobipro.userservice.enteties.User;
import com.jobipro.userservice.enteties.Worker;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "userProfile", source = "user")
    @Mapping(target = "workerProfile", source = "worker")
    @Mapping(target = "isWorker", expression = "java(worker != null)")
    default MeResponse toResponse(
            User user,
            Worker worker,
            int profileCompletion,
            String photoUrl
    ) {
        return new MeResponse(
                toUserResponse(user, photoUrl),
                worker != null,
                worker != null ? toWorkerResponse(worker) : null,
                profileCompletion
        );
    }

    @Mapping(target = "photoUrl", source = "photoUrl")
    UserResponse toUserResponse(User user, String photoUrl);

    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
    )
    void updateRequestToUser(
            UpdateUserRequest updateUserRequest,
            @MappingTarget User user
    );


    @Mapping(target = "sectors", source = "sectors")
    WorkerResponse toWorkerResponse(Worker worker);


    InternalUserResponseDto toUserInternalDto(User user);
}