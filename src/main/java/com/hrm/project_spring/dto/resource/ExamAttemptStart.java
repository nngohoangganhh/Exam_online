package com.hrm.project_spring.dto.resource;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ExamAttemptStart {
     private Long id;
    private Long userId;
    private Long testId;
    private LocalDateTime startTime;

}
