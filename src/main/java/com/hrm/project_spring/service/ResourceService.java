package com.hrm.project_spring.service;

import com.hrm.project_spring.dto.common.PageResponse;
import com.hrm.project_spring.dto.resource.ResourceRequest;
import com.hrm.project_spring.dto.resource.ResourceResponse;
import com.hrm.project_spring.dto.resource.ResourceDetailResponse;

import com.hrm.project_spring.entity.Resource;
import com.hrm.project_spring.entity.User;
import com.hrm.project_spring.mapper.ResourceMapper;
import com.hrm.project_spring.repository.ResourceRepository;
import com.hrm.project_spring.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ResourceService {

    private final ResourceRepository resourceRepository;
    private final UserRepository userRepository;
    private final ResourceMapper resourceMapper;

    private static final String ROLE_STUDENT = "STUDENT";

    public PageResponse<ResourceResponse> getAllResource(int pageNo, int pageSize) {
        Page<Resource> page = resourceRepository.findAll(PageRequest.of(pageNo, pageSize));
        List<ResourceResponse> data = page.getContent()
                .stream()
                .map(ResourceMapper::toListResponse)
                .toList();
        return PageResponse.<ResourceResponse>builder()
                .content(data)
                .pageNo(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }
    public ResourceDetailResponse getResourceById(Long id) {
        com.hrm.project_spring.entity.Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exam not found"));
        return ResourceMapper.toDetailResponse(resource);
    }

    public ResourceDetailResponse create(ResourceRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
        Resource resource = Resource.builder()
                .title(request.getDescription())
                .status(request.getStatus())
                .description(request.getDescription())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .status(request.getStatus())
                .createdBy(user)
                .createdAt(LocalDateTime.now())
                .build();
        return ResourceMapper.toDetailResponse(resourceRepository.save(resource));
    }
    public ResourceDetailResponse update(Long id, ResourceRequest request) {

        com.hrm.project_spring.entity.Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Resource not found"));
        resource.setDescription(request.getDescription());
        resource.setStartTime(request.getStartTime());
        resource.setEndTime(request.getEndTime());
        resource.setStatus(request.getStatus());
        return ResourceMapper.toDetailResponse(resourceRepository.save(resource));
    }
    public void deleteExam(Long id) {
        if (!resourceRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Exam not found");
        }
        resourceRepository.deleteById(id);
    }

//
//    public ResourceDetailResponse assignStudentsToExam(Long examId, Set<Long> studentIds) {
//        com.hrm.project_spring.entity.ResourceResponse resource = resourceRepository.findByIdWithStudents(examId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exam not found"));
//
//        Set<User> students = getValidStudents(studentIds);
//        students.removeAll(resource.getStudents()); // tránh duplicate
//        resource.getStudents().addAll(students);
//        return ResourceMapper.toDetailResponse(resource);
//    }
//
//    public ResourceDetailResponse removeStudentsFromExam(Long examId, Set<Long> studentIds) {
//        Exam resource = resourceRepository.findByIdWithStudents(examId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exam not found"));
//        resource.getStudents().removeIf(u -> studentIds.contains(u.getId()));
//        return ResourceMapper.toDetailResponse(resource);
//    }
//
//    public Set<StudentResponse> getStudentsByExamId(Long examId) {
//        Exam resource = resourceRepository.findByIdWithStudents(examId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exam not found"));
//        return resource.getStudents().stream()
//                .map(u -> StudentResponse.builder()
//                        .id(u.getId())
//                        .username(u.getUsername())
//                        .build())
//                .collect(Collectors.toSet());
//    }
//
//    private Set<User> getValidStudents(Set<Long> ids) {
//        // Lấy tất cả user tồn tại, chỉ giữ những user có role STUDENT
//        Set<User> users = userRepository.findAllById(ids).stream()
//                        .filter(u -> u.getRoles().stream()
//                        .anyMatch(r -> ROLE_STUDENT.equals(r.getCode())))
//                        .collect(Collectors.toSet());
//        if (users.isEmpty() && !ids.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
//                "Không tìm thấy user hợp lệ có role STUDENT");
//        }
//        return users;
//    }
//
//    private void validate(ExamRequest request) {
//        if (request.getStartTime().isAfter(request.getEndTime())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start phải trước End");
//        }
//        if (request.getName() == null || request.getName().trim().isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tên không được trống");
//        }
//    }
}
