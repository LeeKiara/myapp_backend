package com.lhm.myapp.project;

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
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long projectId; // primary key

    @Column(nullable = false)
    private String title;   // 프로젝트 제목

    private String description; // 프로젝트 소개

    @Column(length = 8)
    private String startDate;   // 시작일(yyyyMMdd)

    @Column(length = 8)
    private String endDate;     // 종료일(yyyyMMdd)

    // 컬럼크기 1024byte * 1024 = 1mb * 20 = 20mb
    @Column(length = 1024 * 1024 * 20)
    private String image;       // 대표 이미지 (longtext)

    private String status;      // 상태 (1: 진행중, 2: 완료, 3: 지연)

    private String creatorName;   // Project Manager

    private long createdTime; // 생성일

    // User 엔티티 키
    private long user_id;

}
