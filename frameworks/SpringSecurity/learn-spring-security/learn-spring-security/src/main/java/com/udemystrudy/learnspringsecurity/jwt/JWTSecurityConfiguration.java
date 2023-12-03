package com.udemystrudy.learnspringsecurity.jwt;

import com.nimbusds.jose.jwk.JWKSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
//import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import com.nimbusds.jose.jwk.RSAKey; //RSA객체 만들기
import com.nimbusds.jose.jwk.source.JWKSource;  //JWT source만들기


import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION;

@Configuration
public class JWTSecurityConfiguration {

    @Bean
    SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
        http.sessionManagement() .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.httpBasic(withDefaults()); //구문은 브라우저에 기본 인증 팝업을 표시하도록 설정

        http.csrf().disable(); //csrf 사용 안함

        http.headers().frameOptions().sameOrigin(); // 프레임 사용 설정

        http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);


		return http.build();
	}

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


    //키 페어 만들기 (디코딩 사전 작업)
    @Bean
    public KeyPair keyPair(){
        try {
            var keyPaitrGenerator = KeyPairGenerator.getInstance("RSA");
            keyPaitrGenerator.initialize(2048);
            return keyPaitrGenerator.generateKeyPair();
//        } catch (NoSuchAlgorithmException e) {
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    //RSA 객체 만들기
    @Bean
    public RSAKey rsaKey(KeyPair keyPair){

        return  new RSAKey
                .Builder((RSAPublicKey)keyPair.getPublic())
                .privateKey(keyPair().getPrivate())
                .keyID(UUID.randomUUID().toString())
                .build();
    }


    //JSON Web Key source 만들기  (JWKSet 만들고 이걸로 JWKSource 만듦)
    @Bean
    public JWKSource jwkSource(RSAKey rsaKey){
        var jwtSet = new JWKSet(rsaKey);
    }


//    @Bean
//    public JwtDecoder jwtDecoder(){
//        return Decoder()
//    }




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
