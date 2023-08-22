package com.lhm.myapp.auth;

import com.lhm.myapp.auth.entity.Member;
import com.lhm.myapp.auth.entity.MemberProjection;
import com.lhm.myapp.auth.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;

// Http 요청이 컨트롤러 메세드에 도달하기 전에
// 사용자의 유효성을 검증하고, 유효할 경우 요청 속성에 인증값을 추가한다.
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    JwtUtil jwt;

    // 요청이 컨트롤러 메서드에 도달하기 전에 호출되며, false를 반환하면 요청 처리가 중단됨
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        // HTTP 요청을 처리하는 메서드인지 확인
        if(handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();

            // @Auth 어노테이션이 있는지 확인
            Auth auth = method.getAnnotation(Auth.class);

            // @Auth 어노테이션이 없으면 토큰 관련 처리를 별도로 하지 않음
            if(auth == null) {
                return true;
            }

            // Request Header에 authorization 헤더에 토큰을 조회
            String token = request.getHeader("Authorization");

//            System.out.println("------- AuthInterceptor token------");
//            System.out.println(token);

            // 인증 토큰이 없으면
            if(token == null || token.isEmpty()) {
                // 401: Unauthorized(미인가인데, 미인증이라는 의미로 사용)
                // 인증토큰이 없음
                response.setStatus(401);
                return false;
            }

            //-- 인증 토큰이 있으면 -------------------------------

            // 인증토큰 검증 및 페이로드(subject/claim) 객체화하기
            //  - 페이로드(payload) : 메시지 개념에서 서로 주고받는 데이터
            AuthProfile profile =
                    jwt.validateToken(token.replace("Bearer ", ""));
            if(profile == null) {
                // 401: Unauthorized
                // 인증토큰이 잘못 됨(시그니처, 페이로드, 알고리즘..)
                response.setStatus(401);
                return false;
            }

//            System.out.println("---- token 회원정보 ----");
//            System.out.println(profile);

            // 요청 속성(attribute)에 프로필 객체 추가하기
            request.setAttribute("authProfile", profile);
            return true;
        }

        return true;
    }
}
