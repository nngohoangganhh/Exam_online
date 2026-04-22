package com.hrm.project_spring.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.PrintWriter;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {

    @NotNull(message = "resoureceID không được trống")
    private Long resourceId;
    @NotBlank(message = " tên không được để trống")
    private String name;
    private String status;
    @NotNull(message = " mô tả không được để trống")
    private String description;


}