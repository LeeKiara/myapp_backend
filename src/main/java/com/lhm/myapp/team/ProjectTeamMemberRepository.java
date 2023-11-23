package com.lhm.myapp.team;


import com.lhm.myapp.auth.entity.MemberProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectTeamMemberRepository extends JpaRepository<ProjectTeamMember, Long> {

    //@Query(value="SQL 문법 :매개변수", nativeQuery = true)
//    @Query(value = "SELECT t1.name, t1.email " +
//            " FROM member t1 " +
//            " LEFT JOIN project_team_member t2 ON t1.mid = t2.mid " +
//            " WHERE t2.pid = :pid", nativeQuery = true)
//    List<Member> findTeamMemberByPid(long pid);

    @Query("SELECT m FROM Member m " +
            " LEFT JOIN ProjectTeamMember ptm ON m.mid = ptm.mid " +
            " WHERE ptm.pid = :pid " +
            "   AND ptm.mid = :mid ")
    MemberProjection findTeamMemberByPidAndByMid(@Param("pid") long pid, @Param("mid") long mid);

    @Query("SELECT m FROM Member m " +
            "LEFT JOIN ProjectTeamMember ptm ON m.mid = ptm.mid " +
            "WHERE ptm.pid = :pid")
    List<MemberProjection> findTeamMemberByPid(@Param("pid") long pid);

    Optional<ProjectTeamMember> findByPidAndMid(long pid, long mid);

    Page<ProjectTeamMember> findByPidOrderByCreatedTimeDesc(long pid,
                                                                 Pageable pageable);

//    long deleteByPidAndMid(long pid, long mid);
}
