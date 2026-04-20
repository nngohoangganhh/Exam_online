package com.hrm.project_spring.repository;

import com.hrm.project_spring.entity.Exam;
import com.hrm.project_spring.entity.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {

    @Query("""
        SELECT e FROM Resource e
        LEFT JOIN FETCH e.students
        LEFT JOIN FETCH e.createdBy
        WHERE e.id = :id
    """)
    Optional<Resource> findByIdWithStudents(@Param("id") Long id);

    @Query("SELECT e FROM Resource e JOIN e.students s WHERE s.id = :userId")
    Page<Resource> findByStudentId(@Param("userId") Long userId, Pageable pageable);

    long countByStatus(String status);
}
