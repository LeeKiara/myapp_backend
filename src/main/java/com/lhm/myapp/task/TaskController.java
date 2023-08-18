package com.lhm.myapp.task;

import com.lhm.myapp.project.Project;
import com.lhm.myapp.project.ProjectRepository;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
//@RequestMapping("/project/tasks")
public class TaskController {

    @Autowired
    TaskRepository repo;

    @Autowired
    ProjectRepository projectRepo;

    // GET /project/tasks?pid=1
    @GetMapping(value = "/project/tasks")
    public List<Task> getTaskList(@RequestParam long pid) {

        System.out.println("입력값 확인 : "+pid);

        // Project id로 Tasks 정보 조회
        List<Task> taskList = repo.findByProject_Pid(pid);

        return taskList;
    }

    @PostMapping(value = "/project/{pid}/task")
    public ResponseEntity<Map<String, Object>> AddTask(@PathVariable long pid,
                                                       @RequestBody Task task) {
        System.out.println("입력값 확인 - pid : "+pid +", task : "+task);

        // TODO list
        // 1. 입력값 검증 :

        // 2. Project 정보 조회
        Optional<Project> project = projectRepo.findById(pid);
        // task를 등록하기 위해서는 Project가 생성되어 있어야 함.
        // Project가 존재하지 않을경우, Bad Request로 리턴하고 종료
        if(!project.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        // TODO list : pm_mid는 로그인 정보로 변경
        Long memberId = 1L;

        // 3. 추가 정보 등록
        task.setStatus("1");    // 상태 (1: 진행중, 2: 완료, 3: 지연)
        task.setCreatedTime(new Date().getTime());
        task.setMid(memberId);

        // 3. task와 연결된 프로젝트 정보 추가
        task.setProject(project.get());

        // 4. Task DB insert
        Task savedTask = repo.save(task);

        System.out.println("savedTask 확인 : "+savedTask);

        if(savedTask != null) {
            Map<String, Object> res =new HashMap<>();
            res.put("data", savedTask);
            res.put("message", "created");

            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        }

        return ResponseEntity.ok().build();

    }

}
