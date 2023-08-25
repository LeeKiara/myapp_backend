package com.lhm.myapp.task;

import com.lhm.myapp.auth.Auth;
import com.lhm.myapp.auth.AuthProfile;
import com.lhm.myapp.project.Project;
import com.lhm.myapp.project.ProjectRepository;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.transaction.Transactional;
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

    @Autowired
    TaskService service;

    // Task 정보 조회(1건)
    // GET /project/task?tid=1
    @Auth
    @GetMapping(value = "/project/task")
    public ResponseEntity<Map<String, Object>> getTask(@RequestParam long tid,
                                  @RequestAttribute AuthProfile authProfile) {

        System.out.println("TaskController getTask call");
        System.out.println("입력값 확인 : "+tid);

        // Project id로 Tasks 정보 조회
        Optional<Task> task = repo.findById(tid);

        Map<String, Object> res = new HashMap<>();

        if (task.isPresent()) {
            res.put("data", task.get());
            res.put("message", "FOUND");

            if(authProfile.getId() == task.get().getMid()) {
                res.put("role", "modify");
            }

            return ResponseEntity.status(HttpStatus.OK).body(res);
        } else {
            res.put("data", null);
            res.put("role", null);
            res.put("message", "NOT_FOUND");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        }
    }

    // Task 리스트 조회(다건)
    // GET /project/tasks?pid=1
    @Auth
    @GetMapping(value = "/project/tasks")
    public List<Task> getTaskList(@RequestParam long pid,
                                  @RequestAttribute AuthProfile authProfile) {

        System.out.println("입력값 확인 : "+pid);

        // Project id로 Tasks 정보 조회
        List<Task> taskList = repo.findByProject_Pid(pid);

        return taskList;
    }

    // Task 리스트 조회(다건)
    // GET /project/tasks-member?pid=1
    @Auth
    @GetMapping(value = "/project/tasks-member")
    public List<TaskMemberProjection> getTaskListJoinMember(@RequestParam long pid,
                                  @RequestAttribute AuthProfile authProfile) {

        System.out.println("입력값 확인 : "+pid);

        // Project id로 Tasks 정보 조회
        List<TaskMemberProjection> taskList = repo.findTaskMemberByPid(pid);

        return taskList;
    }

    /*
       Task 정보 추가(DB : insert)
       POST /project/{pid}/task
     */
    @Auth
    @PostMapping(value = "/project/{pid}/task")
    public ResponseEntity<Map<String, Object>> AddTask(@PathVariable long pid,
                                                       @RequestBody Task task,
                                                       @RequestAttribute AuthProfile authProfile) {
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

        // TODO : 사전작업 - 해당 프로젝트 팀원으로 등록

        // 3. 추가 정보 등록
        task.setStatus("1");    // 상태 (1: 진행중, 2: 완료, 3: 지연)
        task.setCreatedTime(new Date().getTime());
        task.setMid(authProfile.getId());

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

    /*
       Task 정보 수정(DB : update)
       PUT /project/task/{tid}
     */
    @Auth
    @PutMapping(value = "/project/task/{tid}")
    public ResponseEntity<Map<String, Object>> modifyTask(@PathVariable Long tid,
                                                             @RequestBody Task task,
                                                             @RequestAttribute AuthProfile authProfile) {

        System.out.println("TaskController modifyTask call!");
        System.out.println("입력값 확인 : "+tid);

        // 저장된 데이터 조회
        Optional<Task> findedTask = repo.findById(tid);

        System.out.println("저장된 데이터 조회");
        System.out.println(findedTask);

        Map<String, Object> res = new HashMap<>();

        // 저장된 데이터가 없을 경우
        if (!findedTask.isPresent()) {
            res.put("data", null);
            res.put("message", "NOT_FOUND");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        }

        Task toModifyTask = findedTask.get();

        // TODO
        // 1. 입력값 검증 : 프로젝트명, 기간 등...

        // 2. 입력값으로 변경
        toModifyTask.setTitle(task.getTitle());
        toModifyTask.setDescription(task.getDescription());
        toModifyTask.setStartDate(task.getStartDate());
        toModifyTask.setEndDate(task.getEndDate());
//        toModifyTask.setStatus(task.getStatus());
        toModifyTask.setCreatedTime(new Date().getTime());

        // update
        Task savedTask = repo.save(toModifyTask);

        // 변경된 값을 리턴함
        res.put("data", savedTask);
        res.put("message", "SAVE_DONE");

        return ResponseEntity.ok().body(res);

    }

    /*
       Task 정보 삭제(DB : delete)
       DELETE /project/task/{tid}
     */
    @Auth
    @DeleteMapping(value = "/project/task/{tid}")
    public ResponseEntity removeTask(@PathVariable Long tid,
                                                          @RequestAttribute AuthProfile authProfile) {

        System.out.println("TaskController removeTask call!");
        System.out.println("입력값 확인 : "+tid);

        // 저장된 데이터 조회
        Optional<Task> findedTask = repo.findById(tid);

        System.out.println("저장된 데이터 조회");
        System.out.println(findedTask);

        Map<String, Object> res = new HashMap<>();

        // 저장된 데이터가 없을 경우
        if (!findedTask.isPresent()) {
            res.put("data", null);
            res.put("message", "NOT_FOUND");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        }

        // delete
        repo.deleteById(tid);

        return ResponseEntity.ok().build();

    }

    @GetMapping(value = "/project/task/count")
    public TaskSummaryProjection getCountTask(@RequestParam long mid) {

        System.out.println("\nTaskController..getCountTask 입력값 확인 : "+mid);

        // Project id로 Tasks 정보 조회
        TaskSummaryProjection cntTask = repo.getCountTask(mid);

        System.out.println(cntTask);
        System.out.println(cntTask.getCountTask());

        return cntTask;
    }

    // 프로젝트의 Task 갯수 조회
    // GET /project/{pid}/task/count
    @GetMapping(value = "/project/{pid}/task/count")
    public ResponseEntity<Map<String, Object>> getCountTaskByPid(@PathVariable long pid) {

        System.out.println("\nTaskController..getCountTaskByPid 입력값 확인 : "+pid);

        // Project id로 Tasks 정보 조회
        TaskSummaryProjection countTask = repo.getCountTaskByPid(pid);

        System.out.println(countTask.getCountTask());

        Map<String, Object> res = new HashMap<>();
        res.put("data", countTask.getCountTask());
        res.put("message", "OK");

        return ResponseEntity.ok().body(res);
    }

    @PostMapping("/project/task/remove")
    public ResponseEntity<String> removeTaskList(@RequestBody List<Long> tidList) {

        for (Long value : tidList) {
            // 각 값에 대한 처리 로직을 추가
            System.out.println("Received value: " + value);
        }

        service.deleteTaskList(tidList);

        return ResponseEntity.ok().build();
    }


}
