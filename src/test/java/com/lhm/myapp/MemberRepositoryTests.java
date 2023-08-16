package com.lhm.myapp;

import com.lhm.myapp.auth.Member;
import com.lhm.myapp.auth.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberRepositoryTests {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void addMemberTest() {

        Member member = Member.builder()
                .username("kiara2")
                .secret("$2a$12$rJXe178BhS85g42F5F9LKO3gCempit4Nog5YE0tNWldqCzxaZjBq6")
                .name("kiara2")
                .email("kiara2@gmail.com")
                .build();

        memberRepository.save(member);

    }
}
