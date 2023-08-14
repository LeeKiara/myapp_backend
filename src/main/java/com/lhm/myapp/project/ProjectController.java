package com.lhm.myapp.project;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    ProjectRepository repo;

    @GetMapping(value = "/{projectId}")
    public ResponseEntity<Map<String, Object>> getProject(@PathVariable Long projectId) {
        System.out.println("입력값 확인 : " + projectId);

        Optional<Project> project = repo.findById(projectId);

        Map<String, Object> res = new HashMap<>();

        if (project.isPresent()) {
            res.put("data", project.get());
            res.put("message", "FOUND");

            return ResponseEntity.status(HttpStatus.OK).body(res);
        } else {
            res.put("data", null);
            res.put("message", "NOT_FOUND");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> addProject(@RequestBody  Project project) {

        System.out.println("입력값 확인 : "+project);

        // To do list
        // 1. 입력값 검증 : 프로젝트명, 기간 등...

        // 날짜 Format의 "-" 제거 (startDate=2023-08-11 -> 20230811)
        String startDate = project.getStartDate().replaceAll("-","");
        project.setStartDate(startDate);

        String endDate = project.getEndDate().replaceAll("-","");
        project.setEndDate(endDate);

        // 서버에서 추가 정보 등록
        // To do list : User의 id, 성명으로 교체
        project.setStatus("1"); // 진행중
        project.setCreatorName("ADMIN");
        project.setCreatedTime(new Date().getTime());
        project.setUser_id(1);

        Project savedProject = repo.save(project);

        System.out.println("savedProject 확인 : "+savedProject);

        if(savedProject != null) {
            Map<String, Object> res =new HashMap<>();
            res.put("data", savedProject);
            res.put("message", "created");

            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        }

        return ResponseEntity.ok().build();

    }

    // GET /project/paging?page=0&size=10
    @GetMapping(value = "/paging")
    public Page<Project> getProjectPaging(@RequestParam int page, @RequestParam int size) {

        System.out.println("page :"+page);
        System.out.println("size :"+size);

        Sort sort = Sort.by("createdTime").descending();

        PageRequest pageRequest = PageRequest.of(page,size,sort);

        return repo.findAll(pageRequest);

    }

    // GET /project/paging/searchByStatus?page=0&size=10
    @GetMapping(value = "/paging/searchByStatus")
    public Page<Project> getProjectPagingSearchByStatus(@RequestParam int page, @RequestParam int size,
                                                        @RequestParam String status) {

        System.out.println("page :"+page);
        System.out.println("size :"+size);
        System.out.println("status :"+status);

        Sort sort = Sort.by("createdTime").descending();

        PageRequest pageRequest = PageRequest.of(page,size,sort);

        return repo.findByStatus(status, pageRequest);

    }
}
