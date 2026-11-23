package com.jobipro.taskservice.contracts;

import com.jobipro.taskservice.dtos.assignment.AssignmentCreateDto;
import com.jobipro.taskservice.dtos.assignment.AssignmentResponseDto;
import com.jobipro.taskservice.dtos.assignment.AssignmentUpdateDto;

public interface AssignmentContract {
    AssignmentResponseDto invitWorker(String taskPublicId, AssignmentCreateDto assignmentCreateDto);

    AssignmentResponseDto requestByWorker(String taskPublicId);

    AssignmentResponseDto updateAssignment(String publicId, AssignmentUpdateDto assignmentUpdateDto);

}
