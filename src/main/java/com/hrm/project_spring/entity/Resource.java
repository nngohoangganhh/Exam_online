package com.hrm.project_spring.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "exams")
public class Resource extends BaseEntity{
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToMany
//    @JoinTable(
//    name = "exam_students",
//    joinColumns = @JoinColumn(name = "exam_id"),
//    inverseJoinColumns = @JoinColumn(name = "user_id"))
//    private Set<User> students = new HashSet<>();
//
//    private String name;
//    private String description;
//
//    @Column(name = "start_time")
//    private LocalDateTime startTime;
//
//    @Column(name = "end_time")
//    private LocalDateTime endTime;
//
//    @ManyToOne
//    @JoinColumn(name = "created_by")
//    private User createdBy;
//
//    private String status;
//    // Quan hệ 1 kỳ thi có nhiều đề thi
//    @OneToMany(mappedBy = "exam", fetch = FetchType.LAZY)
//    @Builder.Default
//    private List<Test> tests = new ArrayList<>();
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String description;
    private String thumbnail;

    @Column(name = "status")
    private String status;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "categories_id")
    private Category category;

    @OneToMany(mappedBy = "resource")
    private List<Section> sections;
}




