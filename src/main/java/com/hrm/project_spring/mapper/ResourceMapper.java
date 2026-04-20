package com.hrm.project_spring.mapper;

import com.hrm.project_spring.dto.exam.ExamDetailResponse;
import com.hrm.project_spring.dto.exam.ExamListResponse;
import com.hrm.project_spring.dto.user.UserResponseDto;
import com.hrm.project_spring.entity.Resource;
import com.hrm.project_spring.entity.User;


public class ResourceMapper {
    public static ExamListResponse toListResponse(Resource resource) {
        return ExamListResponse.builder()

                .startTime(resource.getStartTime())
                .endTime(resource.getEndTime())
                .status(resource.getStatus())
                .build();
    }

    public static ExamDetailResponse toDetailResponse(Resource resource) {
        return ExamDetailResponse.builder()


                .description(resource.getDescription())
                .status(resource.getStatus())
                .startTime(resource.getStartTime())
                .endTime(resource.getEndTime())
                .createdAt(resource.getCreatedAt())
                .createdBy(toUser(resource.getCreatedBy()))
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