package com.jobipro.taskservice.mappers;

import com.jobipro.common.dto.internal.InternalCompanyResponse;
import com.jobipro.common.dto.internal.InternalSiteResponse;
import com.jobipro.common.dto.internal.InternalUserResponseDto;
import com.jobipro.taskservice.dtos.TaskCreateDto;
import com.jobipro.taskservice.dtos.TaskResponseDto;
import com.jobipro.taskservice.dtos.TaskUpdateDto;
import com.jobipro.taskservice.enteties.Task;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publicId", ignore = true)
    @Mapping(target = "createdById", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Task fromDtoToTask(TaskCreateDto taskCreateDto);


    @Mapping(target = "publicId", source = "task.publicId")
    @Mapping(target = "description", source = "task.description")
    @Mapping(target = "status", source = "task.status")
    @Mapping(target = "company", source = "company")
    @Mapping(target = "site", source = "site")
    @Mapping(target = "assignments", ignore = true)
    TaskResponseDto fromTaskToResponse(
            Task task,
            InternalCompanyResponse company,
            InternalSiteResponse site,
            InternalUserResponseDto createdBy,
            List<String> photoUrls
    );


    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publicId", ignore = true)
    @Mapping(target = "companyId", ignore = true)
    @Mapping(target = "siteId", ignore = true)
    @Mapping(target = "createdById", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateTaskFromDto(
            TaskUpdateDto taskUpdateDto,
            @MappingTarget Task task
    );
}