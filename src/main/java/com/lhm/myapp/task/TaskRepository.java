package com.lhm.myapp.task;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<List> findByProject_PidOrderByCreatedTimeDesc(long pid);

    List<Task> findByProject_Pid(long pid);


}
