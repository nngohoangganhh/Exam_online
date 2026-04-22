package com.hrm.project_spring.dto.resource;

import com.hrm.project_spring.dto.user.UserResponseDto;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceDetailResponse {
    private Long id;
    private String title;
    private String description;
    private String thumbnail;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    private LocalDateTime createdAt;
    private UserResponseDto createdBy;

}
