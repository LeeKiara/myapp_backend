package com.lhm.myapp.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Page<Project> findByStatus(String status,
                               Pageable pageable);

    Page<Project> findFirstByStatusOrderByCreatedTimeDesc(String status,
                                                          Pageable pageable);


}
