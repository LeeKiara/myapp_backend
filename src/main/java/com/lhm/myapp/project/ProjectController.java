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
    @GetMapping(value = "/{pid}")
    public ResponseEntity<Map<String, Object>> getProject(@PathVariable Long pid) {
        System.out.println("입력값 확인 : " + pid);

        Optional<Project> project = repo.findById(pid);

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
    public ResponseEntity<Map<String, Object>> addProject(@RequestBody Project reqProject) {

        System.out.println("입력값 확인 : "+reqProject);

        // TODO list
        // 1. 입력값 검증 : 프로젝트명, 기간 등...


        // TODO list : pm_mid는 로그인 정보로 변경
        Long projectMemberId = 1L;

        // 추가 정보 등록
        reqProject.setStatus("1"); // 진행중
        reqProject.setPm_mid(projectMemberId);
        reqProject.setCreatedTime(new Date().getTime());

        // 2. Project DB insert
        Project savedProject = repo.save(reqProject);

        repo.save(savedProject);

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
    @PutMapping(value = "/{pid}")
    public ResponseEntity<Map<String, Object>> modifyProject(@PathVariable Long pid, @RequestBody  Project project) {

        System.out.println("1.입력값 확인 : "+pid);
        System.out.println("2.입력값 확인 : "+project);

        // 저장된 데이터 조회
        Optional<Project> findedProject = repo.findById(pid);

        System.out.println("저장된 데이터 조회");
//        System.out.println(findedProject);

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

        // TODO list : pm_mid는 로그인 정보로 변경
        Long projectMemberId = 1L;

        toModifyProject.setTitle(project.getTitle());
        toModifyProject.setDescription(project.getDescription());
        toModifyProject.setStartDate(project.getStartDate());
        toModifyProject.setEndDate(project.getEndDate());
        if(project.getImage() != null) {
            toModifyProject.setImage(project.getImage());
        }
        toModifyProject.setStatus(project.getStatus());
        toModifyProject.setPm_mid(projectMemberId);
        toModifyProject.setCreatedTime(new Date().getTime());
//        toModifyProject.setUserNo(1);

        // update
        Project savedProject = repo.save(toModifyProject);

        // 변경된 값을 리턴함
        res.put("data", savedProject);
        res.put("message", "SAVE_DONE");

        return ResponseEntity.ok().body(res);

    }

   /*
     프로젝트 정보 삭제(DB : delete)
   */
    @DeleteMapping(value = "/{pid}")
    public ResponseEntity<Map<String, Object>> removeProject(@PathVariable Long pid) {

        System.out.println("1.입력값 확인 : "+pid);

        // project 데이터 조회
        Optional<Project> findedProject = repo.findById(pid);

        System.out.println("저장된 데이터 조회");
        System.out.println(findedProject);

        Map<String, Object> res = new HashMap<>();

        // 저장된 데이터가 없을 경우
        if (!findedProject.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // 외부 입력 파라메터 검증 (프로젝트 id가 동일한지 확인)
        if (findedProject.get().getPid() != pid) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // TODO : 프로젝트의 Task가 존재할 경우 삭제할 수 없도록 수정 또는 해당 TASK 모두 삭제
        // - Project와 Task 의 관계매핑 고려 : OneToMany => (Task에서 FK 생성여부)

        // Project 데이터 삭제
        repo.deleteById(pid);

        return ResponseEntity.ok().build();

    }

}
