package com.hrm.project_spring.controller;


import com.hrm.project_spring.dto.category.CategoryRequest;
import com.hrm.project_spring.dto.category.CategoryResponse;
import com.hrm.project_spring.dto.common.ApiResponse;
import com.hrm.project_spring.dto.common.PageResponse;
import com.hrm.project_spring.dto.user.UserResponseDto;
import com.hrm.project_spring.entity.Category;
import com.hrm.project_spring.service.CategoryService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;


    @PreAuthorize("hasAuthority('CATEGORY:READ')")
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<CategoryResponse>>>  getAllCategory(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(
                ApiResponse.<PageResponse<CategoryResponse>>builder()
                        .success(true)
                        .status(200)
                        .message("Lấy danh sách thành công")
                        .data(categoryService.getAllCategory(pageNo,pageSize))
                        .build()
        );
    }
    @PreAuthorize("hasAuthority('CATEGORY:READ')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>>  getCategoryById (@PathVariable Long id){
        return ResponseEntity.ok(
                ApiResponse.<CategoryResponse>builder()
                        .success(true)
                        .status(200)
                        .message("Tìm kiếm id thành công")
                        .data(categoryService.getCategoryById(id))
                        .build());
    }
    @PreAuthorize("hasAuthority('CATEGORY:CREATE')")
    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory( @Valid @RequestBody CategoryRequest request){
        return ResponseEntity.ok(
                ApiResponse.<CategoryResponse>builder()
                        .success(true)
                        .status(200)
                        .message("Tạo thành công ")
                        .data(categoryService.createCategory(request))
                        .build());
    }
    @PreAuthorize("hasAuthority('CATEGORY:EDIT')")
    @PutMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(@PathVariable Long id,@Valid @RequestBody CategoryRequest request){
        return ResponseEntity.ok(
                ApiResponse.<CategoryResponse>builder()
                        .success(true)
                        .status(200)
                        .message("Sửa thành công")
                        .data(categoryService.update(request, id))
                        .build());
    }
    @PreAuthorize("hasAuthority('CATEGORY:DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }







}
