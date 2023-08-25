package com.lhm.myapp.task;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    TaskRepository repo;

    @Transactional
    @PostMapping("/api/deletePosts")
    public void deleteTaskList(List<Long> tidList) {

        for (Long value : tidList) {
            // 각 값에 대한 처리 로직을 추가
            System.out.println("Received value: " + value);
        }

        repo.removeByTidIn(tidList);
    }
}
