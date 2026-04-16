package com.hrm.project_spring.dto.test;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestRequest {

    @NotNull(message = "examId không được để trống")
    private Long examId;
    @NotBlank(message = "Title không được để trống")
    private String title;
    @NotNull
    private Integer durationMinutes;
    @NotNull
    private Integer totalScore;

    private LocalDateTime createdAt;


}
