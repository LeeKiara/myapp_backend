package com.lhm.myapp.auth;

import com.lhm.myapp.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<MemberProjection> findByEmail(String email);
}
