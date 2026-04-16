package com.hrm.project_spring.dto.permission;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionRequest {
    @NotBlank(message = "Permission code is required")
    private String code;

    @NotBlank(message = "Action is required")
    private String action;

    @NotBlank(message = "Permission name is required")
    private String name;

    private String description;

    @NotNull(message = "Feature ID is required")
    private Long featureId;
}
