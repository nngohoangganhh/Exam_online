package com.hrm.project_spring.controller;

import com.hrm.project_spring.dto.common.ApiResponse;
import com.hrm.project_spring.dto.common.PageResponse;
import com.hrm.project_spring.dto.resource.ResourceDetailResponse;
import com.hrm.project_spring.dto.resource.ResourceRequest;
import com.hrm.project_spring.dto.resource.ResourceResponse;

import com.hrm.project_spring.service.ResourceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/exams")
@RestController
public class ResourceController {

    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService= resourceService ;
    }

    @PreAuthorize("hasAuthority('EXAM:READ')")
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ResourceResponse>>> getAllResource(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(
                ApiResponse.<PageResponse<ResourceResponse>>builder()
                        .success(true)
                        .status(200)
                        .message("lấy danh sách thành công")
                        .data(resourceService.getAllResource(pageNo, pageSize))
                        .build()
        );
    }
    @PreAuthorize("hasAuthority('EXAM:READ')")
    @GetMapping("/{id}")
    public ResponseEntity <ApiResponse<ResourceDetailResponse>>getResourceById (@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.<ResourceDetailResponse>builder()
                        .success(true)
                        .status(200)
                        .message("tìm kỳ thi theo id thành công")
                        .data(resourceService.getResourceById(id))
                        .build()
        );
    }

    @PreAuthorize("hasAuthority('EXAM:CREATE')")
    @PostMapping
    public ResponseEntity<ApiResponse<ResourceDetailResponse>> create(@RequestBody @Valid ResourceRequest request) {
           return ResponseEntity.ok(
                ApiResponse.<ResourceDetailResponse>builder()
                        .success(true)
                        .status(200)
                        .message(" Tạo kỳ thi thành công")
                        .data(resourceService.create(request))
                        .build()
        );
    }

    @PreAuthorize("hasAuthority('EXAM:UPDATE')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ResourceDetailResponse>> update(
            @PathVariable Long id,
            @RequestBody @Valid ResourceRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<ResourceDetailResponse>builder()
                        .success(true)
                        .status(200)
                        .message("tìm kỳ thi theo id thành công")
                        .data(resourceService.update(id,request))
                        .build()
        );
    }

    @PreAuthorize("hasAuthority('EXAM:DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteExam(@PathVariable Long id) {
        resourceService.deleteExam(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .success(true)
                .status(200)
                .message("Xóa thành công")
                .data(null)
                .build());
    }


//    @PreAuthorize("hasAuthority('EXAM:READ')")
//    @GetMapping("/{examId}/students")
//    public ResponseEntity<ApiResponse<List<StudentResponse>>> getStudentsByExamId(@PathVariable Long examId) {
//        return ResponseEntity.ok(
//                 ApiResponse.<List<StudentResponse>>builder()
//                        .success(true)
//                        .status(200)
//                        .message("lấy danh sách học sinh theo id kỳ thi thành công")
//                        // Fix: Wrap Set<StudentResponse> into List to avoid ClassCastException
//                        .data(new ArrayList<>(examService.getStudentsByExamId(examId)))
//                        .build()
//        );
//    }
//
//    @PreAuthorize("hasAuthority('EXAM:UPDATE')")
//    @PostMapping("/{examId}/students")
//    public ResponseEntity<ApiResponse<ResourceDetailResponse>> assignStudents(
//            @PathVariable Long examId,
//            @RequestBody @Valid AssignStudentsRequest request) {
//        return ResponseEntity.ok(ApiResponse.<ResourceDetailResponse>builder()
//                .success(true)
//                .status(200)
//                .message("Gán học sinh thành công")
//                .data(examService.assignStudentsToExam(examId, request.getStudentIds()))
//                .build());
//    }
//
//    @PreAuthorize("hasAuthority('EXAM:UPDATE')")
//    @DeleteMapping("/{examId}/students")
//    public ResponseEntity<ApiResponse<ResourceDetailResponse>> removeStudents(
//            @PathVariable Long examId,
//            @RequestBody @Valid AssignStudentsRequest request) {
//        return ResponseEntity.ok(ApiResponse.<ResourceDetailResponse>builder()
//                .success(true)
//                .status(200)
//                .message("Xóa học sinh thành công")
//                .data(examService.removeStudentsFromExam(examId, request.getStudentIds()))
//                .build());
//    }
}
