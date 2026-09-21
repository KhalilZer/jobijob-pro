package com.jobipro.userservice.helpers;

import com.jobipro.userservice.abstracts.StorageServiceContract;
import com.jobipro.userservice.dtos.user.MeResponse;
import com.jobipro.userservice.enteties.User;
import com.jobipro.userservice.enteties.Worker;
import com.jobipro.userservice.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReturnMeResponseHelper {

    private final UserMapper userMapper;
    private final StorageServiceContract storageServiceContract;
    private final ProfileCompletionHelper profileCompletionHelper;

    public MeResponse returnMeResponse(User user, Worker worker) {
        String photoUrl = "";

        if (user.getPhoto() != null && !user.getPhoto().isBlank()) {
            photoUrl = storageServiceContract.generateDownloadUrl(user.getPhoto());
        }
        return userMapper.toResponse(user, worker, profileCompletionHelper.calculate(user, worker), photoUrl);

    }
}
