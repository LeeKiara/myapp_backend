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
    private long ownerId;   // 사용자 정보 key

    private long projectId; // 프로젝트 정보 key

    // Project 엔티티와 1:1 관계 매핑
    // project_no가 FK로 생성
//    @OneToOne
//    private Project project;
}
