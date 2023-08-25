package com.lhm.myapp.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<List> findByProject_PidOrderByCreatedTimeDesc(long pid);

    List<Task> findByProject_Pid(long pid);

    @Query(value = "SELECT " +
            " t1.tid AS tid, " +
            " t1.title AS title, " +
            " t1.description AS description, " +
            " t1.start_date AS startDate, " +
            " t1.end_date AS endDate, " +
            " t1.mid AS mid, " +
            " t1.status AS status, " +
            " t2.username AS username " +
            " FROM Task t1 " +
            " LEFT JOIN Member t2 ON t1.mid = t2.mid " +
            " WHERE t1.project_pid = :pid", nativeQuery = true)
    List<TaskMemberProjection> findTaskMemberByPid(@Param("pid") long pid);

    @Query(value = "select count(*) AS countTask from task where mid = :mid", nativeQuery = true)
    TaskSummaryProjection getCountTask(@Param("mid") long mid);

    @Query(value = "select count(*) AS countTask from task where project_pid = :pid", nativeQuery = true)
    TaskSummaryProjection getCountTaskByPid(@Param("pid") long pid);


    void removeByTidIn(Collection<Long> tids);



}
