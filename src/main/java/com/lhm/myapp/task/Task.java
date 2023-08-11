package com.lhm.myapp.task;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long taskNo; // primary key

    @Column(nullable = false)
    private String title;   // Task 제목

    private String description; // Task 소개

    @Column(length = 8)
    private String startDate;   // 시작일(yyyyMMdd)

    @Column(length = 8)
    private String endData;     // 종료일(yyyyMMdd)

    private String status;      // 상태 (1: 진행중, 2: 완료, 3: 지연)

    private String creatorName;   // Project Manager

    private long createdTime; // 생성일

    // Project 엔티티 키
    private long projectId;

}
