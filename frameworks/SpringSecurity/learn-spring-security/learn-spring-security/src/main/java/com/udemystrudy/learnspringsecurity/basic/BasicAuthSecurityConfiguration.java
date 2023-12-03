package com.udemystrudy.learnspringsecurity.basic;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION;

//@Configuration
public class BasicAuthSecurityConfiguration {

    @Bean
    SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
        http.sessionManagement() .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//		http.formLogin(withDefaults());  // 폼 로그인 사용 안하기 위해 주석처리
		http.httpBasic(withDefaults()); //구문은 브라우저에 기본 인증 팝업을 표시하도록 설정
        http.csrf().disable();

        http.headers().frameOptions().sameOrigin(); // 프레임 사용 설정

		return http.build();
	}

//    @Bean
//    public UserDetailsService userDetailsService() {
//        // 각 사용자에 대한 UserDetails 객체 생성
//        UserDetails user1 = User.withUsername("hello")
//                                .password("{noop}world")
//                                .roles("USER")
//                                .build();
//
//        UserDetails user2 = User.withUsername("lemon")
//                                .password("{noop}candy")
//                                .roles("ADMIN")
//                                .build();
//
//        UserDetails user3 = User.withUsername("black")
//                                .password("{noop}pink")
//                                .roles("PLAYER")
//                                .build();
//
//        // InMemoryUserDetailsManager에 UserDetails 객체들을 전달
//        return new InMemoryUserDetailsManager(user1, user2, user3);
//    }


    @Bean
    public DataSource dataSource(){
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript(DEFAULT_USER_SCHEMA_DDL_LOCATION)
                .build();

    }


    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource){

        var user1 = User.withUsername("hello")
//                                .password("{noop}world")
                                .password("world")
                                .passwordEncoder(str -> passwordEncoder().encode(str))
                                .roles("USER")
                                .build();

        var user2 = User.withUsername("lemon")
//                                .password("{noop}candy")
                                .password("candy")
                                .passwordEncoder(str -> passwordEncoder().encode(str))
                                .roles("ADMIN")
                                .build();

        var user3 = User.withUsername("black")
//                                .password("{noop}pink")
                                .password("pink")
                                .passwordEncoder(str -> passwordEncoder().encode(str))
                                .roles("PLAYER")
                                .build();

        var jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.createUser(user1);
        jdbcUserDetailsManager.createUser(user2);
        jdbcUserDetailsManager.createUser(user3);

        return jdbcUserDetailsManager;

    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
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
