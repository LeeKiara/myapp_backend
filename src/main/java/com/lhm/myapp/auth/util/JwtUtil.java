package com.lhm.myapp.auth.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lhm.myapp.auth.AuthProfile;
import com.lhm.myapp.auth.entity.Member;

import java.util.Date;

public class JwtUtil {

    // 임의의 서명 값 - 키파일 등을 읽어서 처리가능
    public String secret = "your-secret";

    // Token 만료시간 : 7일 (단위 : MillSecond)
    public final long TOKEN_TIMEOUT = 1000 * 60 * 60 * 24 * 7;

    // JWT 토큰 생성
    public String createToken(long id, String username, String mname) {

        // 만료시간 : Site 특성에 따라 설정
        // - 개인정보처리시스템 : 1시간 내외, 일반 쇼핑몰 : 1시간 ~ 3시간 등
        Date now = new Date();
        Date exp = new Date(now.getTime() + TOKEN_TIMEOUT);

        // 암호 알고리즘
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                // sub: 토큰 소유자
                .withSubject(String.valueOf(id))
                .withClaim("username", username)
                .withClaim("nickname", mname)
                .withIssuedAt(now)
                .withExpiresAt(exp)
                .sign(algorithm);
    }

    public AuthProfile validateToken(String token) {

        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 검증 객체 생성
        JWTVerifier verifier = JWT.require(algorithm).build();

        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            // 토큰 검증 제대로 된 상황
            // 토큰 페이로드(데이터, subject/claim)를 조회
            Long id = Long.valueOf(decodedJWT.getSubject());
            String nickname = decodedJWT
                    .getClaim("nickname").asString();
            String username = decodedJWT
                    .getClaim("username").asString();

           return AuthProfile.builder()
                   .id(id)
                   .username(username)
                   .nickname(nickname)
                   .build();

        } catch (JWTVerificationException e) {
            // 토큰 검증 오류 상황
            return null;
        }
    }
}
