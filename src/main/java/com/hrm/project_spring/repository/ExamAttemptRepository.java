package com.hrm.project_spring.repository;

import com.hrm.project_spring.entity.ExamAttempt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamAttemptRepository extends JpaRepository<ExamAttempt, Long> {
    Page<ExamAttempt> findByUserId(Long userId, Pageable pageable);
    Page<ExamAttempt> findByTestId(Long testId, Pageable pageable);
    Optional<ExamAttempt> findByIdAndUserId(Long attemptId, Long userId);
    Optional<ExamAttempt> findFirstByUserIdAndTestIdOrderByIdDesc(Long userId, Long testId);
    boolean existsByUserIdAndTestIdAndSubmitTimeIsNotNull(Long userId, Long testId);
    @Query("SELECT a FROM ExamAttempt a WHERE a.test.exam.id = :examId")
    Page<ExamAttempt> findByExamId(@Param("examId") Long examId, Pageable pageable);
    @Query("SELECT a FROM ExamAttempt a WHERE a.user.id = :userId AND a.test.exam.id = :examId")
    Page<ExamAttempt> findByUserIdAndExamId(@Param("userId") Long userId,
                                             @Param("examId") Long examId,
                                             Pageable pageable);
    long countByUserIdAndSubmitTimeIsNotNull(Long userId);
    long countByUserId(Long userId);
    @Query("SELECT AVG(a.score) FROM ExamAttempt a WHERE a.user.id = :userId AND a.submitTime IS NOT NULL")
    Double findAverageScoreByUserId(@Param("userId") Long userId);
}

