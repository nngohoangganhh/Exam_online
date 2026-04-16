package com.hrm.project_spring.dto.permission;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionResponse {
    private Long id;
    private String code;
    private String action;
    private String name;
    private String description;
    private Long featureId;
    private String featureName;
    private String featureCode;
}
