package com.lhm.myapp.auth;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private long userNo;    // 회원번호

    private String userId;      // ID

    private String secret;      // 비밀번호

    private String name;        // 성명

    private String email;       // 이메일

    private String departCode;  // 부서명


}
