package com.jobipro.userservice.services;


import com.jobipro.common.dto.internal.InternalWorkerResponse;
import com.jobipro.common.exceptions.ResourceNotFound;
import com.jobipro.userservice.abstracts.WorkerContract;
import com.jobipro.userservice.enteties.Worker;
import com.jobipro.userservice.mappers.WorkerMapper;
import com.jobipro.userservice.repositories.WorkerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkerService implements WorkerContract {

    private final WorkerRepo workerRepo;
    private final WorkerMapper workerMapper;



    //For internal
    @Override
    public InternalWorkerResponse getWorkerById(String workerPublicId) {
        Worker worker = workerRepo.findByPublicId(workerPublicId).orElseThrow(() -> new ResourceNotFound("Worker Not found"));

        return workerMapper.toInternalWorkerResponse(worker);
    }
}
