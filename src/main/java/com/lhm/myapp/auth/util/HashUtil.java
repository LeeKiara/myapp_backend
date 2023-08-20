package com.lhm.myapp.auth.util;

import at.favre.lib.crypto.bcrypt.BCrypt;

// 해쉬 암호화 관련 유틸리티 클래스
public class HashUtil {

    // 해쉬 암호화 (Bcrypt Hash 적용)
    public String createHash(String cipherText) {
        // hash create 할때는 salt를 랜덤으로 생성해서 저장
        //  - hash: $버전$라운드횟수$22바이트salt+해시문자열
        return BCrypt
                .withDefaults()
                .hashToString(12, cipherText.toCharArray());
    }

    // 암호화 되지 않은 문자열과 해쉬화된 비밀번호가 일치하는지 확인
    public boolean verifyHash(String plaintext, String hash) {
        // hash를 verifying 할 때는 이미 있는 salt값으로
        // ciphertext를 결합하여 hash와 맞는지 확인
        return BCrypt
                .verifyer()
                .verify(plaintext.toCharArray(), hash)
                .verified;
    }



}
