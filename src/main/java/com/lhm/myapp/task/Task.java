package com.lhm.myapp.task;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tid; // primary key

    @Column(nullable = false)
    private String title;   // Task 제목

    private String description; // Task 소개

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate; // 시작일

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;     // 종료일
    
    private String status;      // 상태 (1: 진행중, 2: 완료, 3: 지연)

    private long createdTime; // 생성일

    @Column(nullable = false)
    private String mid;   // member id

    @Column(nullable = false)
    private long pid;   // project id

}
