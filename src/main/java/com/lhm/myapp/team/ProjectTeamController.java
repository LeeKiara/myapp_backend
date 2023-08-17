package com.lhm.myapp.team;

import com.lhm.myapp.project.Project;
import com.lhm.myapp.project.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/project/member")
public class ProjectTeamController {

    @Autowired
    ProjectTeamMemberRepository repo;

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
}
