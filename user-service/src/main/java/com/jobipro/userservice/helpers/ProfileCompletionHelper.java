package com.jobipro.userservice.helpers;

import com.jobipro.userservice.enteties.User;
import com.jobipro.userservice.enteties.Worker;
import org.springframework.stereotype.Component;

@Component
public class ProfileCompletionHelper {
    public int calculate(User user, Worker worker) {

        int total = 0;
        int completed = 0;

        total++;
        if (hasValue(user.getFullName())) completed++;

        total++;
        if (user.getBirthDate() != null) completed++;

        total++;
        if (hasValue(user.getPhone())) completed++;

        total++;
        if (hasValue(user.getPhoto())) completed++;

        if (worker != null) {

            total++;
            if (hasValue(worker.getDescription())) completed++;

            total++;
            if (hasValue(worker.getCity())) completed++;

            total++;
            if (worker.getWorkerSectors() != null
                    && !worker.getWorkerSectors().isEmpty()) {
                completed++;
            }
        }

        return total == 0
                ? 0
                : (completed * 100) / total;
    }

    private boolean hasValue(String value) {
        return value != null && !value.isBlank();
    }
}
