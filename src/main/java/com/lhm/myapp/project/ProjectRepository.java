package com.lhm.myapp.project;

import com.lhm.myapp.task.TaskMemberProjection;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
//    Page<Project> findByPm_mid(long pm_mid, Pageable pageable);
    Page<Project> findByStatus(String status,
                               Pageable pageable);

    Page<Project> findFirstByStatusOrderByCreatedTimeDesc(String status,
                                                          Pageable pageable);

    Page<Project> findByTitle(String title, Pageable pageable);

    Page<Project> findByCreatorUser(long creatorUser, Pageable pageable);

    // 내가 참여한 프로젝트 정보 조회
    @Query(value = "SELECT  " +
                    " t2.title AS title, " +
                    " t2.description AS description, " +
                    " t2.start_date As startDate, " +
                    " t2.end_date AS endDate, " +
                    " t2.creator_user AS creatorUser, " +
                    " t2.status, " +
                    " t1.pid, " +
                    " t1.mid " +
                    " FROM project_team_member t1 " +
                    " LEFT JOIN project t2 ON t1.pid = t2.pid " +
                    "  WHERE t1.mid = :mid", nativeQuery = true)
    List<ProjectProjection> findProjectByMid(@Param("mid") long mid);




}
