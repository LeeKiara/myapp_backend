package com.lhm.myapp.team;

import com.lhm.myapp.project.Project;
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
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long teamNo; // primary key

    private long userNo;        // 회원번호
    private String departCode;  // 부서코드

    // Project 엔티티와 1:1 관계 매핑
    // project_no가 FK로 생성
    @OneToOne
    private Project project;
}
