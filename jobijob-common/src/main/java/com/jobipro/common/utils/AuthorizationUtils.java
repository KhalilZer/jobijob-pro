package com.jobipro.common.utils;

import com.jobipro.common.UserRole;
import com.jobipro.common.exceptions.ForbiddenException;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public final class AuthorizationUtils {
    private AuthorizationUtils() {
    }


    public static void requireOwnership(
            String ownerId,
            String currentUserId
    ) {
        if (!Objects.equals(ownerId, currentUserId)) {
            throw new ForbiddenException(
                    "You don't have permission to perform this action"
            );
        }
    }

    public static void requireOwnerOrSiteManager(
            String companyOwnerId,
            String siteManagerId,
            String currentUserId
    ) {
        boolean isOwner =
                Objects.equals(companyOwnerId, currentUserId);

        boolean isSiteManager =
                Objects.equals(siteManagerId, currentUserId);

        if (!isOwner && !isSiteManager) {
            throw new ForbiddenException(
                    "You must be the company owner or site manager"
            );
        }
    }
}
