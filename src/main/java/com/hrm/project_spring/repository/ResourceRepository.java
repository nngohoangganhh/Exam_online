package com.hrm.project_spring.repository;


import com.hrm.project_spring.entity.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    long countByStatus(String status);
    Page<Resource> findDistinctByEnrollmentsUserId(Long userId, Pageable pageable);
}
