package com.hrm.project_spring.mapper;

import com.hrm.project_spring.dto.exam.ExamDetailResponse;
import com.hrm.project_spring.dto.exam.ExamListResponse;
import com.hrm.project_spring.dto.user.UserResponseDto;
import com.hrm.project_spring.entity.Resource;
import com.hrm.project_spring.entity.User;


public class ResourceMapper {
    public static ExamListResponse toListResponse(Resource resource) {
        return ExamListResponse.builder()
                .id(resource.getId())
                .name(resource.getName())
                .startTime(resource.getStartTime())
                .endTime(resource.getEndTime())
                .status(resource.getStatus())
                .build();
    }

    public static ExamDetailResponse toDetailResponse(Resource resource) {
        return ExamDetailResponse.builder()
                .id(exam.getId())
                .name(exam.getName())
                .description(exam.getDescription())
                .status(exam.getStatus())
                .startTime(exam.getStartTime())
                .endTime(exam.getEndTime())
                .createdAt(exam.getCreatedAt())
                .createdBy(toUser(exam.getCreatedBy()))
//                .students(
//                        exam.getStudents().stream()
//                                .map(ResourceMapper::toUser)
//                                .toList()
//                )
                .build();
    }
    private static UserResponseDto toUser(User user) {
        if (user == null) return null;
        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }
}