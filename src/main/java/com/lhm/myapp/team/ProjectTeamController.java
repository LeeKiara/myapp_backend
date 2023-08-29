package com.lhm.myapp.team;

import com.lhm.myapp.auth.entity.MemberProjection;
import com.lhm.myapp.project.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/project/member")
public class ProjectTeamController {

    @Autowired
    ProjectTeamMemberRepository repo;

    /*
       프로젝트 팀 멤버 조회 : GET project/member
     */
    @GetMapping
    public MemberProjection getProjectTeam(@RequestParam long pid, @RequestParam long mid) {

        System.out.println("입력값 확인 (pid) : "+pid);
        System.out.println("입력값 확인 (mid) : "+mid);

        // Native-Query를 이용한 방법
        MemberProjection temMember = repo.findTeamMemberByPidAndByMid(pid, mid);

        System.out.println(" getProjectTeamList ");
        System.out.println(temMember);

        return temMember;
    }

    /*
   프로젝트 팀 멤버 리스트 조회 : GET project/member/list
 */
    @GetMapping(value="/list")
    public List<MemberProjection> getProjectTeamList(@RequestParam long pid) {

        // Native-Query를 이용한 방법
        List<MemberProjection> list = repo.findTeamMemberByPid(pid);

        System.out.println(" getProjectTeamList list");
        System.out.println(list);
        return list;

    }

    /*
       프로젝트 팀 멤버 등록(DB : insert)
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> addProjectTeamMember(@RequestBody ProjectTeamMember projectTeamMember) {

        System.out.println("입력값 확인 : "+projectTeamMember);

        // TODO list
        // 1. 입력값 검증 :

        // 추가 정보 등록
        projectTeamMember.setCreatedTime(new Date().getTime());

        // 2. ProjectTeamMember Data insert
        ProjectTeamMember savedProjectTeamMember = repo.save(projectTeamMember);

        repo.save(savedProjectTeamMember);

        System.out.println("savedProject 확인 : "+savedProjectTeamMember);

        if(savedProjectTeamMember != null) {
            Map<String, Object> res =new HashMap<>();
            res.put("data", savedProjectTeamMember);
            res.put("message", "created");

            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        }

        return ResponseEntity.ok().build();

    }

    // 프로젝트 팀원 정보 조회 (페이지 단위로)
    // GET /project/member/paging?page=0&size=10&pid=1
    @GetMapping(value = "/paging")
    public Page<ProjectTeamMember> getProjectTeamPaging(@RequestParam int page, @RequestParam int size,
                                          @RequestParam Long pid) {

        System.out.println("page :"+page);
        System.out.println("size :"+size);
        System.out.println("pid :"+pid);

        Sort sort = Sort.by("createdTime").descending();

        PageRequest pageRequest = PageRequest.of(page,size,sort);

        return repo.findByPidOrderByCreatedTimeDesc(pid, pageRequest);

    }

    /*
      팀 멤버 정보 삭제(DB : delete)
      RequestBody: pid,mid
    */
    @DeleteMapping
    public ResponseEntity<Map<String, Object>> removeProject(@RequestBody ProjectTeamMember projectTeamMember) {

        long pid = projectTeamMember.getPid();
        long mid = projectTeamMember.getMid();

        System.out.println("1.입력값 확인 pid : "+pid);
        System.out.println("1.입력값 확인 mid : "+mid);

        // 팀 멤버 데이터 조회
        Optional<ProjectTeamMember> findedTeamMember = repo.findByPidAndMid(pid, mid);

        System.out.println("findedTeamMember");
        System.out.println(findedTeamMember);

        Map<String, Object> res = new HashMap<>();

        // 저장된 데이터가 없을 경우
        if (!findedTeamMember.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // TODO : 프로젝트의 Task가 존재할 경우 삭제처리 방법 필요

        // 팀 멤버 데이터 삭제 (key : 프로젝트id, 회원id)
        repo.deleteById(findedTeamMember.get().getPtid());

        return ResponseEntity.ok().build();

    }

}
