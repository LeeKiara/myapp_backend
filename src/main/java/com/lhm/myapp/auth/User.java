package com.lhm.myapp.auth;

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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long no;    // 회원번호

    @Column(unique = true)
    private String userId;      // ID

    private String secret;      // 비밀번호

    private String name;        // 성명

    private String email;       // 이메일

    private String departCode;  // 부서명


}
