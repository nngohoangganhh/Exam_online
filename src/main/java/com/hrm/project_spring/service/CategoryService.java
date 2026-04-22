package com.hrm.project_spring.service;


import com.hrm.project_spring.dto.category.CategoryRequest;
import com.hrm.project_spring.dto.category.CategoryResponse;
import com.hrm.project_spring.dto.common.PageResponse;
import com.hrm.project_spring.entity.Category;
import com.hrm.project_spring.entity.Resource;
import com.hrm.project_spring.repository.CategoryRepository;
import com.hrm.project_spring.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ResourceRepository resourceRepository;


    public PageResponse<CategoryResponse> getAllCategory(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Category> categories = categoryRepository.findAll(pageable);
        List<CategoryResponse> content = categories.getContent().stream()
                .map(this::mapTo)
                .collect(Collectors.toList());
        return PageResponse.<CategoryResponse>builder()
                .content(content)
                .pageNo(categories.getNumber())
                .pageSize(categories.getSize())
                .totalElements(categories.getTotalElements())
                .totalPages(categories.getTotalPages())
                .build();
    }
    
        public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Không tìm thấy id"));
        return mapTo(category);
    }

    public CategoryResponse createCategory ( CategoryRequest request) {
     if (categoryRepository.existsByName(request.getName())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Name không được để trống " + request.getName());
        }
     if (categoryRepository.existsByDescription(request.getDescription())){
         throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Description không được để trống "+request.getDescription());
     }
        Category category = Category.builder()
                .status(request.getStatus())
                .name(request.getName())
                .description(request.getDescription())
                .build();
     return mapTo(categoryRepository.save(category));
    }

    public CategoryResponse update(CategoryRequest request, Long id ){
        Category category = categoryRepository.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"không tìm thấy loại"));
        if (request.getResourceId() != null){
            Resource resource = resourceRepository.findById(request.getResourceId())
                    .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"không tìm thấy khóa học"));
            category.setResources((List<Resource>) resource);
        }
        category.setName(request.getName());
        category.setStatus(request.getStatus());
        category.setDescription(request.getDescription());
        Category update = categoryRepository.save(category);
        return mapTo(update);
    }

    public  void deleteCategory(Long id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Không tìm thấy id"));
         categoryRepository.delete(category);
    }


    
    
    
    

    
    public CategoryResponse mapTo(Category category){
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .status(category.getStatus())
                .description(category.getDescription())
                .build();
    }

}
