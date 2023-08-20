package com.lhm.myapp.team;

import com.lhm.myapp.auth.entity.MemberProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/project/member")
public class ProjectTeamController {

    @Autowired
    ProjectTeamMemberRepository repo;

    /*
       프로젝트 팀 멤버 리스트 조회 : GET project/member
     */
    @GetMapping
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


}
