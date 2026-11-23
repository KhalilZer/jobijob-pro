package com.jobipro.userservice.mappers;

import com.jobipro.common.dto.internal.InternalWorkerResponse;
import com.jobipro.userservice.enteties.Worker;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WorkerMapper {

    @Mapping(target = "fullName", source = "user.fullName")
    @Mapping(target = "email", source = "user.email")
    InternalWorkerResponse toInternalWorkerResponse(Worker worker);
}
