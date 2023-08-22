package com.lhm.myapp.auth.entity;

import com.lhm.myapp.auth.Auth;
import com.lhm.myapp.auth.AuthProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    MemberRepository repo;

    // 이메일 정보로 사용자 정보 가져오기
    @PutMapping(value = "/{email}")
    public ResponseEntity<Map<String, Object>> getMember(@PathVariable String email) {

        System.out.println("입력값 확인 : " + email);

        Optional<MemberProjection> member = repo.findByEmail(email);

        Map<String, Object> res = new HashMap<>();

        if (member.isPresent()) {
            res.put("data", member.get());
            res.put("message", "FOUND");

            return ResponseEntity.status(HttpStatus.OK).body(res);
        } else {
            res.put("data", null);
            res.put("message", "NOT_FOUND");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        }

    }

    @Auth
    @GetMapping(value="/getUserInfo")
    public ResponseEntity<AuthProfile> getAuthProfile(@RequestAttribute AuthProfile authProfile) {

        System.out.println("authProfile :"+authProfile);

        return ResponseEntity.status(HttpStatus.OK).body(authProfile);

    }
}
