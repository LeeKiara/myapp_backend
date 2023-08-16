package com.lhm.myapp;

import com.lhm.myapp.auth.Member;
import com.lhm.myapp.auth.MemberRepository;
import com.lhm.myapp.project.Project;
import com.lhm.myapp.project.ProjectRepository;
import jakarta.transaction.Transactional;
import org.hibernate.annotations.Cascade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@SpringBootTest
public class ProjectRepositoryTests {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void addProjectTest() {

//        Optional<Member> result = this.memberRepository.findById(1L);
//        Member findMember = result.get();
//        System.out.println(findMember);
//
//        Set<Member> memberSet = new HashSet<>();
//        memberSet.add(findMember);

        Project project = Project.builder()
                .title("프로젝트1")
                .description("새롭게 시작합니다.")
                .startDate(new Date())
                .endDate(new Date())
                .status("1")
                .creatorUser("kiara")
                .createdTime(new Date().getTime())
                //.TeamMember(memberSet)
                .build();

        projectRepository.save(project);

    }

    @Test
    @Transactional
    public void addPrjMemberTest(){

//        Optional<Member> result1 = memberRepository.findById(1L);
//        Member member1 = result1.get();
//
////        Optional<Member> result2 = memberRepository.findById(3);
////        Member member2 = result2.get();
////
        Optional<Member> result = this.memberRepository.findById(1L);
        Member findMember = result.get();
        System.out.println(findMember);

        Optional<Project> prtResult = projectRepository.findById(1L);
        Project project = prtResult.get();
        System.out.println(project);

        project.addMember(findMember);
        System.out.println(project.getTeamMembers());

        projectRepository.save(project);





    }
}
