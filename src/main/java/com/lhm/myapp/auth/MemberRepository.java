package com.lhm.myapp.auth;

import com.lhm.myapp.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
