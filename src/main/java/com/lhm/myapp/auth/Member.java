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
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long mid;    // id

    @Column(unique = true, nullable = false)
    private String username;      // Member ID

    @Column(nullable = false)
    private String secret;      // 암호화된 비밀번호

    private String name;        // 성명

    @Column(nullable = false)
    private String email;       // 이메일

}
