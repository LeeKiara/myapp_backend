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

    // 프로젝트id로 프로젝트 정보 가져오기
    @GetMapping(value = "/{projectid}")
    public ResponseEntity<Map<String, Object>> getProject(@PathVariable Long projectid) {
        System.out.println("입력값 확인 : " + projectid);

        Optional<Project> project = repo.findById(projectid);

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

    /*
       프로젝트 정보 등록(DB : insert)
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> addProject(@RequestBody  Project project) {

        System.out.println("입력값 확인 : "+project);

        // TODO list
        // 1. 입력값 검증 : 프로젝트명, 기간 등...

        // 날짜 Format의 "-" 제거 (startDate=2023-08-11 -> 20230811)
//        String startDate = project.getStartDate().replaceAll("-","");
//        project.setStartDate(startDate);
//
//        String endDate = project.getEndDate().replaceAll("-","");
//        project.setEndDate(endDate);

        // 서버에서 추가 정보 등록
        // TODO list : User의 id, 성명으로 교체
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

    // 프로젝트 정보 조회 (페이지 단위로)
    // GET /project/paging?page=0&size=10
    @GetMapping(value = "/paging")
    public Page<Project> getProjectPaging(@RequestParam int page, @RequestParam int size) {

        System.out.println("page :"+page);
        System.out.println("size :"+size);

        Sort sort = Sort.by("createdTime").descending();

        PageRequest pageRequest = PageRequest.of(page,size,sort);

        return repo.findAll(pageRequest);

    }

    // 프로젝트 상태값으로 프로젝트 정보 조회
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

    /*
       프로젝트 정보 수정(DB : update)
     */
    @PutMapping(value = "/{projectid}")
    public ResponseEntity<Map<String, Object>> modifyProject(@PathVariable Long projectid, @RequestBody  Project project) {

        System.out.println("1.입력값 확인 : "+projectid);
        System.out.println("2.입력값 확인 : "+project);

        // 저장된 데이터 조회
        Optional<Project> findedProject = repo.findById(projectid);

        System.out.println("저장된 데이터 조회");
        System.out.println(findedProject);

        Map<String, Object> res = new HashMap<>();

        // 저장된 데이터가 없을 경우
        if (!findedProject.isPresent()) {
            res.put("data", null);
            res.put("message", "NOT_FOUND");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        }

        Project toModifyProject = findedProject.get();

        // TODO
        // 1. 입력값 검증 : 프로젝트명, 기간 등...

        // TODO list : 로그인 정보로 변경
        toModifyProject.setTitle(project.getTitle());
        toModifyProject.setDescription(project.getDescription());
        toModifyProject.setStartDate(project.getStartDate());
        toModifyProject.setEndDate(project.getEndDate());
        toModifyProject.setImage(project.getImage());
        toModifyProject.setStatus(project.getStatus());
        toModifyProject.setCreatorName("ADMIN");
        toModifyProject.setCreatedTime(new Date().getTime());
        toModifyProject.setUser_id(1);

        // update
        repo.save(toModifyProject);

        return ResponseEntity.ok().build();

    }
}
