package com.hrm.project_spring.repository;

import com.hrm.project_spring.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryRepository extends JpaRepository< Category,Long> {

    Page<Category> findAll(Pageable pageable);

    boolean existsByName(String name);

    boolean existsByDescription(String description);
}
