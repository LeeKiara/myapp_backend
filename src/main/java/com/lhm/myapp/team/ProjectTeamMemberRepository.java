package com.lhm.myapp.team;

import com.lhm.myapp.project.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTeamMemberRepository extends JpaRepository<ProjectTeamMember, Long> {


}
