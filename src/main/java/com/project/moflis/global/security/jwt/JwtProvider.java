package com.project.moflis.global.security.jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;

@Component
public class JwtProvider {

    @Value("${jwt.secretKey}")
    private String secretKeyCode;

    private SecretKey secretKey;

    public SecretKey getSecretKey() {
        if (secretKey == null) {
            String encoding = Base64.getEncoder().encodeToString(secretKeyCode.getBytes());
            secretKey = Keys.hmacShaKeyFor(encoding.getBytes());
        }
        return secretKey;
    }

    //토큰은 문자열로 만들어짐 그래서 return을 String으로 함
    private String genToken(Map<String, Object> map, int seconds) { // 초는 토큰의 유효시간 설정
        //인자 map = 개인정보를 받음
        //현재 날짜를 구하고 시간을 얻어낸다
        long now = new Date().getTime();

        //현재 날짜(시간) 보단 이후
        //토큰 유효시간 생성
        Date accessTokenExpiresIn = new Date(now + 1000L * seconds);

        //그냥 생성자로 생성해서 만들면 인자들이 뭐가 뭔지 몰라서 빌드 패턴을
        // 쓰는게 가독성이 좋다
        JwtBuilder jwtBuilder = Jwts.builder()
                .subject("user")//토큰이름 지정
                .expiration(accessTokenExpiresIn);

        // map 담겨있는 개인정보를 뺴서 set에 담음
        Set<String> keys = map.keySet();

        //속도가 더 빠름 iterator는 커서가 존재해서 바로 찾을수있다
        // 담겨있는 개인정보를 토큰으로 만듦
        Iterator<String> it = keys.iterator();
        while (it.hasNext()) {
            String key = it.next();
            Object value = map.get(key);
            jwtBuilder.claim(key, value);
        }
        //토큰을 만들어서 반환
        return jwtBuilder.signWith(getSecretKey()).compact();
    }

    public String getAccessToken(Map<String, Object> map) {
        return genToken(map, 60 * 60); //1시간
    }

    public String getRefreshToken(Map<String, Object> map) {
        return genToken(map, 60 * 60 * 24 * 100); //100일
    }

    //토큰 유효기간 확인
    public boolean verify(String token) {
        boolean value = true;
        try {
            //여기에 사용한 secretKey가 인자로 들어가야함
            Jwts.parser().verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token);//기간만료 시 예외발생!!
        } catch (Exception e) {
            value = false;
        }
        return value;
    }

    //토근에 담긴 사용자정보(claims)를 반환
    public Map<String, Object> getClaims(String token) {
        //이렇게 하면 토큰안에 있는 정보를 맵으로 뺴서 줌
        return Jwts.parser().verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


}