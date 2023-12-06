package com.udemystrudy.learnspringsecurity.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;

@RestController
public class JwtAuthenticationResource {

    //JWT 토큰을 만들려면 인코더가 필요
    private JwtEncoder jwtEncoder;

    public  JwtAuthenticationResource(JwtEncoder jwtEncoder){
        this.jwtEncoder = jwtEncoder;
    }

    @PostMapping("/authenticate")
    public JwtResponse authenticate(Authentication authentication){

        return new JwtResponse(createToken(authentication));
    }


    //인증 객체를 받아서 JwtResponse를 생성하는 메서드
    private String createToken(Authentication authentication) {
        var claims = JwtClaimsSet.builder()
                .issuer("self")  //토큰을 발급한 엔터티를 나타냄
                .issuedAt(Instant.now())  // JWT 토큰의 발급 시각을 현재 시각으로
                .expiresAt(Instant.now().plusSeconds(60*15))  //JWT 토큰의 만료 시각을 현재 시각으로부터 15분 후로 설정
                .subject(authentication.getName())  //JWT 토큰의 주체(subject)를 인증(Authentication) 객체에서 가져온 사용자의 이름으로 설정
                .claim("scope", createScope(authentication)) //토큰 내에 포함된 데이터를 나타내는 부분(키-값 쌍으로 표현)
                .build(); // JwtClaimsSet 객체를 생성하고, 이 객체는 JWT 토큰의 내용을 나타내는데 사용
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    //createScope() 메서드에서 모든 권한을 받기
    private String createScope(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(a->a.getAuthority())
                .collect(Collectors.joining(" "));  //모든 권한을 공백으로 구분해서 취합

    }


}



record  JwtResponse(String token){

}

