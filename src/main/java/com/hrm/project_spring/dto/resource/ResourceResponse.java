package com.hrm.project_spring.dto.resource;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ResourceResponse {
    private Long id;
    private String title;
    private String description;
    private String thumbnail;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    private LocalDateTime createdat;
}
