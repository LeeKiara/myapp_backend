package com.lhm.myapp.team;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectTeamMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ptid; // primary key

    @Column(nullable = false)
    private long pid;   // Project id

    @Column(nullable = false)
    private long mid;   // Member id

    private long createdTime; // 생성일

}
