package com.jobipro.taskservice.dtos;

import com.jobipro.common.dto.internal.InternalCompanyResponse;
import com.jobipro.common.dto.internal.InternalSiteResponse;

public record TaskCreationContext(
        InternalCompanyResponse company,
        InternalSiteResponse site
) {
}