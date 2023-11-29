package com.udemystrudy.learnspringsecurity.basic;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

public class BasicAuthSecurityConfiguration {

    @Bean
    SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
        http.sessionManagement() .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//		http.formLogin(withDefaults());  // 폼 로그인 사용 안하기 위해 주석처리
		http.httpBasic(withDefaults()); //구문은 브라우저에 기본 인증 팝업을 표시하도록 설정
        http.csrf().disable();
		return http.build();
	}

}
//    @Bean
//    SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated())
//            .sessionManagement(sessionManagement ->
//                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//            .httpBasic(withDefaults())
//            .csrf().disable();
//        return http.build();
//    }
