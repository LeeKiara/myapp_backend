package com.lhm.myapp.project;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long pid; // primary key

    @Column(nullable = false)
    private String title;   // 프로젝트 제목

    @Column(length = 1000)
    private String description; // 프로젝트 소개

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate; // 시작일

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;     // 종료일

    // 컬럼크기 1024byte * 1024 = 1mb * 20 = 20mb
    @Column(length = 1024 * 1024 * 20)
    private String image;       // 대표 이미지 (longtext)

    private String status;      // 상태 (1: 진행중, 2: 완료, 3: 지연)

    @Column(nullable = false)
    private long creatorUser;   // 생성자 id

    private long createdTime; // 생성일

}
