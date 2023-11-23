package com.lhm.myapp.project;

import com.lhm.myapp.auth.Auth;
import com.lhm.myapp.auth.AuthProfile;
import com.lhm.myapp.auth.entity.Member;
import com.lhm.myapp.auth.entity.MemberProjection;
import com.lhm.myapp.auth.entity.MemberRepository;
import com.lhm.myapp.team.ProjectTeamMemberRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Tag(name="프로젝트 관리 처리 API")
@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    ProjectRepository repo;

    @Autowired
    MemberRepository memberRepo;

    @Autowired
    ProjectTeamMemberRepository projectTeamMemberRepo;

    // 프로젝트id로 프로젝트 정보 가져오기
    // GET /project/1
    @Operation(summary = "프로젝트 id로 프로젝트 정보 가져오기", security = { @SecurityRequirement(name = "bearer-key") })
    @Auth
    @GetMapping(value = "/{pid}")
    public ResponseEntity<Map<String, Object>> getProject(@PathVariable Long pid,
                                                          @RequestAttribute AuthProfile authProfile) {
        System.out.println("입력값 확인 : " + pid);

        Optional<Project> project = repo.findById(pid);
        Optional<Member> member = memberRepo.findById(project.get().getCreatorUser());

        System.out.println("member");
        System.out.println(member);

        Map<String, Object> res = new HashMap<>();

        if (project.isPresent()) {
            res.put("data", project.get());
            res.put("data2", member.get());
            res.put("message", "FOUND");

            // 로그인한 유저가 프로젝트 생성자일 경우
            if(authProfile.getId() == project.get().getCreatorUser()) {
                res.put("role", "modify");
                res.put("role-project", "CRUD");
            }

            return ResponseEntity.status(HttpStatus.OK).body(res);
        } else {
            res.put("data", null);
            res.put("data2", null);
            res.put("message", "NOT_FOUND");
            res.put("role", null);
            res.put("role-project", "");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        }
    }

    /*
       프로젝트 정보 등록(DB : insert)
       POST /project
     */
    @Operation(summary = "프로젝트 정보 등록", security = { @SecurityRequirement(name = "bearer-key") })
    @Auth
    @PostMapping
    public ResponseEntity<Map<String, Object>> addProject(@RequestBody Project reqProject,
                                                          @RequestAttribute AuthProfile authProfile) {

        System.out.println("입력값 확인 : "+reqProject);

        // TODO list
        // 1. 입력값 검증 : 프로젝트명, 기간 등...

        // 추가 정보 등록
        reqProject.setStatus("1"); // 진행중
        reqProject.setCreatorUser(authProfile.getId()); // 생성자 id : 로그인 id
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

    // 프로젝트 전체 조회(리스트)
    // GET /project/list-all
    @Operation(summary = "프로젝트 전체 조회(리스트)", security = { @SecurityRequirement(name = "bearer-key") })
    @Auth
    @GetMapping(value = "/list-all")
    public List<Project> getProjectList(@RequestAttribute AuthProfile authProfile) {

        System.out.println("\n <<< ProjectController getProjectList >>> ");

        Sort sort = Sort.by("createdTime").descending();

        return repo.findAll(sort);
    }

    // 상태값에 따른 프로젝트 조회(리스트)
    // GET /project/list-status?status=1
    @Operation(summary = "상태값에 따른 프로젝트 조회(리스트)", security = { @SecurityRequirement(name = "bearer-key") })
    @Auth
    @GetMapping(value = "/list-status")
    public List<Project> getProjectListByStatus(@RequestParam String status,
                                                @RequestAttribute AuthProfile authProfile) {

        System.out.println("\n <<< ProjectController getProjectListByStatus >>> ");
        System.out.println("입력값 확인 : "+status);

        return repo.findByStatusOrderByCreatedTimeDesc(status);
    }

    // 내가 참여한 프로젝트 전체 조회(리스트)
    // GET /project/list-myproject
    @Operation(summary = "내가 참여한 프로젝트 전체 조회(리스트)", security = { @SecurityRequirement(name = "bearer-key") })
    @Auth
    @GetMapping(value = "/list-myproject")
    public List<ProjectProjection> getMyProjectList(@RequestAttribute AuthProfile authProfile) {

        System.out.println("\n <<< ProjectController getProjectList >>> ");
        System.out.println("입력값 확인 : " + authProfile.getId());

        long mid = authProfile.getId();

        return repo.findProjectByMid(mid);
    }

    // 프로젝트 전체 조회(페이지 단위)
    // GET /project/paging?page=0&size=10
    @Operation(summary = "프로젝트 전체 조회(페이지 단위)", security = { @SecurityRequirement(name = "bearer-key") })
    @Auth
    @GetMapping(value = "/paging")
    public Page<Project> getProjectPaging(@RequestParam int page, @RequestParam int size,
                                          @RequestAttribute AuthProfile authProfile) {

        System.out.println("ProjectController getProjectPaging call");
        System.out.println("page :"+page);
        System.out.println("size :"+size);
        System.out.println("authProfile :"+authProfile);

        Sort sort = Sort.by("createdTime").descending();

        PageRequest pageRequest = PageRequest.of(page,size,sort);

        return repo.findAll(pageRequest);

    }

    // 내가 생성한 프로젝트 조회 (key:creatorUser)
    // GET /project/paging/myproject
    @Operation(summary = "내가 생성한 프로젝트 조회", security = { @SecurityRequirement(name = "bearer-key") })
    @Auth
    @GetMapping(value = "/paging/myproject")
    public Page<Project> getMyProject(@RequestParam int page, @RequestParam int size,
                                                            @RequestAttribute AuthProfile authProfile) {

        System.out.println("ProjectController getMyProject call");
        System.out.println("page :"+page);
        System.out.println("size :"+size);
        System.out.println("authProfile :"+authProfile);
        System.out.println("authProfile.getId() :"+authProfile.getId());

        Sort sort = Sort.by("createdTime").descending();

        PageRequest pageRequest = PageRequest.of(page,size,sort);

        return repo.findByCreatorUser(authProfile.getId(), pageRequest);
    }

    // 내가 참여한 프로젝트 조회
    // GET /project/join
    @Operation(summary = "내가 참여한 프로젝트 조회", security = { @SecurityRequirement(name = "bearer-key") })
    @Auth
    @GetMapping(value = "/join")
    public List<ProjectProjection> getJoinProject(@RequestAttribute AuthProfile authProfile) {

        System.out.println("\n<<< ProjectController getJoinProject call >>>");

        long mid = authProfile.getId();

        List<ProjectProjection> joinProjects = repo.findProjectByMid(mid);

        return joinProjects;
    }

    // 프로젝트 상태값으로 프로젝트 정보 조회
    // GET /project/paging/searchByStatus?page=0&size=10
    @Operation(summary = "프로젝트 상태값으로 프로젝트 정보 조회", security = { @SecurityRequirement(name = "bearer-key") })
    @Auth
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
    @Operation(summary = "프로젝트 정보 수정", security = { @SecurityRequirement(name = "bearer-key") })
    @Auth
    @PutMapping(value = "/{pid}")
    public ResponseEntity<Map<String, Object>> modifyProject(@PathVariable Long pid, @RequestBody  Project project,
                                                             @RequestAttribute AuthProfile authProfile) {

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

        toModifyProject.setTitle(project.getTitle());
        toModifyProject.setDescription(project.getDescription());
        toModifyProject.setStartDate(project.getStartDate());
        toModifyProject.setEndDate(project.getEndDate());
        if(project.getImage() != null) {
            toModifyProject.setImage(project.getImage());
        }
        toModifyProject.setStatus(project.getStatus());
        toModifyProject.setCreatorUser(authProfile.getId());
        toModifyProject.setCreatedTime(new Date().getTime());

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
    @Operation(summary = "프로젝트 정보 삭제", security = { @SecurityRequirement(name = "bearer-key") })
    @Auth
    @DeleteMapping(value = "/{pid}")
    public ResponseEntity<Map<String, Object>> removeProject(@PathVariable Long pid,
                                                             @RequestAttribute AuthProfile authProfile) {

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

    /*-------------------------------------------------------------------------------------------------------------
         프로젝트 :
         - 조회/생성 : 회원전체
         - 수정/삭제 : 관리자

        프로젝트 팀멤버 구성
         - 조회 : 회원전체
         - 등록/ 수정/삭제 : 관리자

        작업
        - 조회 : 회원전체
        - 등록/ 수정/삭제 : 관리자와 팀멤버 구성원

        * 관리자는 프로젝트를 생성하면 관리자 role을 부여 받게 된다.

        예)
        ○ pm1이 프로젝트를 생성한 후 role은 다음과 같다.
        role-project : CRUD
        role-tmember : CRUD
        role-task : CRUD

        ○ projectA 팀멤버에 포함된 user1의 role
        role-project : R
        role-tmember : R
        role-task : CRUD (UD는 본인이 등록한 task만 가능)

        ○ 아무 프로젝트에도 포함되는 않은 user2의 role
        role-project : R
        role-tmember : R
        role-task : R
     ------------------------------------------------------------------------------------------------------------*/
    @Operation(summary = "프로젝트/팀원/작업 권한 조회", security = { @SecurityRequirement(name = "bearer-key") })    @Auth
    @GetMapping(value = "/{pid}/role")
    public ResponseEntity<Map<String, Object>> getProjectRole(@PathVariable Long pid,
                                                          @RequestAttribute AuthProfile authProfile) {
        System.out.println("입력값 확인 : " + pid);

        // 프로젝트 정보 조회
        Optional<Project> project = repo.findById(pid);
        // 회원정보 조회(key: 프로젝트 생성자)
        Optional<Member> member = memberRepo.findById(project.get().getCreatorUser());

        System.out.println("member");
        System.out.println(member);

        // 팀 멤버 조회(key: 프로젝트 pid)
        List<MemberProjection> projectTeamMember = projectTeamMemberRepo.findTeamMemberByPid(pid);

        boolean isAdminUser = false;

        // 로그인한 유저가 프로젝트 생성자일 경우 => 관리자로 역할 부여
        if(authProfile.getId() == project.get().getCreatorUser()) {
            isAdminUser = true;
        }

        Map<String, Object> res = new HashMap<>();

        // 디폴트권한
        res.put("role-project", "R");
        res.put("role-tmember", "R");
        res.put("role-task",    "R");

        if(isAdminUser) {
            res.put("role-project", "CRUD");
            res.put("role-tmember", "CRUD");
            res.put("role-task",    "CRUD");
        } else {
            for (MemberProjection tmp : projectTeamMember) {
                if(authProfile.getId() == tmp.getMid()) {
                    res.put("role-task", "CRUD");
                    break;
                }
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(res);

    }

}
