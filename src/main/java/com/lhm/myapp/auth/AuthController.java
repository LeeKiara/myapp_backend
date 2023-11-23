package com.lhm.myapp.auth;

import com.lhm.myapp.auth.entity.Member;
import com.lhm.myapp.auth.entity.MemberProjection;
import com.lhm.myapp.auth.entity.MemberRepository;
import com.lhm.myapp.auth.request.SignupRequest;
import com.lhm.myapp.auth.util.HashUtil;
import com.lhm.myapp.auth.util.JwtUtil;
import com.lhm.myapp.project.Project;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private MemberRepository repo;

    // AuthController 와 HashUtil은 중간수준의 결합도(coupling)
    // HashUtil 객체를 메서드에서 생성 높은수준의 결합도(coupling)
    @Autowired
    private HashUtil hash;

    @Autowired
    private JwtUtil jwt;

    @Value("${app.cookie.domain}")
    private String cookieDomain;

    @Value("${app.login.url}")
    private String loginUrl;

    @Value("${app.home.url}")
    private String homeUrl;

    // 회원가입
    @PostMapping(value = "/signup")
    public ResponseEntity signUp(@RequestBody SignupRequest req) {
        System.out.println(req);

        // TODO
        //  1. Validation (입력값 검증)
        // - usernmae, password 값이 제대로 들어왔는지...
        // - username,email 중복 여부

        System.out.println("req.getEmail()");
        System.out.println(req.getEmail());

        if(req.getEmail() == null || req.getEmail().isEmpty()) {
            // 응답 객체 생성
            Map<String, Object> res = new HashMap<>();
            res.put("data", null);
            res.put("message", "[email] field is required");

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(res);
        }

        // 이메일로 사용자 정보 조회
        if(req.getEmail()!= null && repo.findByEmail(req.getEmail()).isPresent()) {
            // 맵에 해당 이메일이 있음
            // 이미 있는 데이터를 클라이언트(브라우저) 보냈거나
            // 클라이언트에서 중복 데이터를 보냈거나..
            Map<String, Object> res = new HashMap<>();
            res.put("data", null);
            res.put("message", "동일한 정보가 이미 존재합니다.");

            return ResponseEntity.status(HttpStatus.CONFLICT).body(res);
        }

        System.out.println("email validation done !!!");

        // 2. Buisness Logic(데이터 처리)
        Member toSaveMember =
                Member.builder()
                        .username(req.getUsername())
                        .secret(hash.createHash(req.getPassword()))
                        .mname(req.getMname())
                        .email(req.getEmail())
                        .build();

        System.out.println("toSaveMember");
        System.out.println(toSaveMember);

        // 3. Member 정보를 insert
        Member savedMember = repo.save(toSaveMember);

        // 3. Response -> 201: created
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMember);
    }

    // 로그인 요청을 받아 인증처리 후 쿠키 응답 및 웹페이지로 이동
    @PostMapping(value = "/signin")
    public ResponseEntity signIn(
            @RequestParam String username,
            @RequestParam String password,
            HttpServletResponse res) {

        System.out.println("입력값 확인(username) : "+username);
        System.out.println("입력값 확인(password) : "+password);

        // 1. 입력값 검증
        if(username.isEmpty() || password.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .location(ServletUriComponentsBuilder
                            .fromHttpUrl(loginUrl+"?return-status=400")
                            .build().toUri())
                    .build();
        }

        // 1. username으로 사용자 정보 확인
        Optional<Member> Member = repo.findByUsername(username);

        // username에 해당하는 정보가 없을 경우, 401(Unauthorized) status 리턴
        // status 401(Unauthorized) : 인증정보가 잘못되었습니다.
        if(!Member.isPresent()) {
            System.out.println("username에 해당하는 회원정보 없음 :: Return 401(Unauthorized) status 리턴");
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .location(ServletUriComponentsBuilder
                            .fromHttpUrl(loginUrl+"?return-status=401")
                            .build().toUri())
                    .build();
        }

        // 2. 비밀번호 확인 : 사용자가 입력한 패스워드와 db에 저장된 암호화된 패스워드와 암호화된 패스워들끼리 비교하여 검증
        // - verifyHash(평문 패스워드, 해쉬알고리즘으로 암호화된 패스워드)
        System.out.println("password : "+password);
        System.out.println("Member.get().getSecret() : "+Member.get().getSecret());
        boolean isVerified = hash.verifyHash(password, Member.get().getSecret());

        // 3. 일치하지 않으면, 401(Unauthorized) status 리턴
        if(!isVerified) {
            System.out.println("비밀번호 일치하지 않음 :: Return 401(Unauthorized) status 리턴 후 페이지 이동");
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

            // 비밀번호 일치하지 않으면 로그인 페이지 이동
            return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .location(ServletUriComponentsBuilder
                            .fromHttpUrl(loginUrl+"?return-status=401")
                            .build().toUri())
                    .build();
        }

        // ------------- 여기까지 왔으면, 인증이 성공된 거니 회원정보를 조회한다.

        // 4. 회원정보 조회 후 인증키 생성(JWT)
        Member sucessLogin = Member.get();

        System.out.println("회원정보 조회 : "+sucessLogin);

        String token = jwt.createToken(
                sucessLogin.getMid(),
                sucessLogin.getUsername(),
                sucessLogin.getMname());

        System.out.println("token 조회 : "+token);

        // TODO : domain과 리다이렉트 페이지 외부 환경에서 setting
        // 5. cookie와 헤더를 생성한후 리다이렉트
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        cookie.setMaxAge((int) (jwt.TOKEN_TIMEOUT / 1000L)); // 만료시간 (7일)
        cookie.setDomain(cookieDomain); // 쿠키를 사용할 수 있는 도메인

        // 6. Response Header에 쿠키 추가
        res.addCookie(cookie);

        System.out.println("homeUrl : "+homeUrl);

        // 7. 로그인 후 페이지 이동
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(ServletUriComponentsBuilder
                        .fromHttpUrl(homeUrl)
                        .build().toUri())
                .build();
    }

    @PostMapping(value = "/test")
    public void test(
            @RequestParam String username,
            @RequestParam String password) {

        System.out.println("입력값 확인(username) : "+username);
        System.out.println("입력값 확인(password) : "+password);

    }

    @GetMapping(value = "/test2")
    public void test2(
            @RequestParam String username,
            @RequestParam String password) {

        System.out.println("입력값 확인(username) : "+username);
        System.out.println("입력값 확인(password) : "+password);

    }

}
