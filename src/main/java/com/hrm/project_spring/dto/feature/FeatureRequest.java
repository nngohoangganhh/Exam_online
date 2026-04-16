package com.hrm.project_spring.dto.feature;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeatureRequest {
    @NotBlank(message = "Feature code is required")
    private String code;

    @NotBlank(message = "Feature name is required")
    private String name;

    private String description;
}
