package com.jobipro.taskservice.mappers;

import com.jobipro.common.dto.internal.InternalUserResponseDto;
import com.jobipro.common.dto.internal.InternalWorkerResponse;
import com.jobipro.taskservice.dtos.assignment.AssignmentResponseDto;
import com.jobipro.taskservice.enteties.TaskAssignment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AssignmentMapper {

    @Mapping(target = "worker", source = "worker")
    @Mapping(target = "initiatedBy", source = "user")
    @Mapping(target = "publicId", source = "taskAssignment.publicId")
    AssignmentResponseDto toResponse(TaskAssignment taskAssignment,
                                     InternalWorkerResponse worker,
                                     InternalUserResponseDto user);
}
