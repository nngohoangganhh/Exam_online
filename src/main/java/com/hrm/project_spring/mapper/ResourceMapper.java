package com.hrm.project_spring.mapper;

import com.hrm.project_spring.dto.resource.ResourceDetailResponse;
import com.hrm.project_spring.dto.resource.ResourceResponse;
import com.hrm.project_spring.dto.user.UserResponseDto;
import com.hrm.project_spring.entity.Resource;
import com.hrm.project_spring.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResourceMapper {
//    public static ResourceResponse toListResponse(com.hrm.project_spring.entity.Resource resource) {
//        return ResourceResponse.builder()
//                .startTime(resource.getStartTime())
//                .endTime(resource.getEndTime())
//                .status(resource.getStatus())
//                .build();
//    }
//    public static ResourceDetailResponse toDetailResponse(com.hrm.project_spring.entity.Resource resource) {
//        return ResourceDetailResponse.builder()
//                .description(resource.getDescription())
//                .status(resource.getStatus())
//                .startTime(resource.getStartTime())
//                .endTime(resource.getEndTime())
//                .createdAt(resource.getCreatedAt())
//                .createdBy(toUser(resource.getCreatedBy()))

    /// /                .students(
    /// /                        resource.getStudents().stream()
    /// /                                .map(ResourceMapper::toUser)
    /// /                                .toList()
    /// /
//                      .build();
//    }
//    private static UserResponseDto toUser(User user) {
//        if (user == null) return null;
//        return UserResponseDto.builder()
//                .id(user.getId())
//                .username(user.getUsername())
//                .build();
//    }
//
    static ResourceResponse toListResponse(Resource resource) {
        return null;
    }

    static ResourceDetailResponse toDetailResponse(Resource resource) {
        return null;
    }

    UserResponseDto toUser(User user);


}