# 유데미 Spring Security 6 초보에서 마스터 되기 최신강의! (JWT , OAUTH2 포함)
## ch.02 - 기본 보안 설정



<br>

### 기본 보안을 위한 준비
* 개발할 서비스를 나누기
  * 보안이 필요 없는 서비스 : 자격 증명이 없어도 모든 사람이 이 서비스에 덥근할 수 있음
    * /contact
    * /notice
  * 보안이 필요한 서비스 : 적절하게 검증되고 인증된 유저들만이 접슨할 수 있는 비공개/보안 서비스
    * /myAccount 등..
    * 백엔드에서 유저 정보를 불러오는 것을 도왖며 이 정보는 UI 어플리케이션에 표시됨

<br>

### URL을 보호하는 코드
* 모든 URL을 디폴트로 보호하는 것은 Spring Security 프레임워크 안의 SpringBootWebSecurityConfiguration이라는 클래스 - defaultSecurityFilterChain 메소드
* 
```java
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = Type.SERVLET)
class SpringBootWebSecurityConfiguration {

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnDefaultWebSecurity
	static class SecurityFilterChainConfiguration {

		@Bean  //SecurityFilterChain타입의 객체를 반환하는 것을 Spring 맥락에서 Bean으로 유지하겠다는 뜻
		@Order(SecurityProperties.BASIC_AUTH_ORDER)
		SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {  
            //HttpSecurity를 매개변수로 받음
          
            //HttpSecurity를 이용해서 authorizeHttpRequests라는 메소드를 호출하므로써 웹 애플리케이션이 들어오는 모든 요청이 증명되어야 함을 정의함
			http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
            
            
			http.formLogin(withDefaults());  // formLogin : UI 애플리케이션을 통해 오는 요청
			http.httpBasic(withDefaults());  // Rest API를 통해 들어오는 요청
          
			return http.build();
		}

	}

}

```

```
The default configuration for web security. 
It relies on Spring Security's content-negotiation strategy to determine 
what sort of authentication to use. 
If the user specifies their own SecurityFilterChain bean, 
this will back-off completely and the users should specify all the bits 
that they want to configure as part of the custom security configuration.
```

* 프로젝트에서 자체 보안 요구사항 정의하기
  * config 패키지 생성
    * 그 안에 ProjectSecurityConfig 클래스 생성
    * 이 클래스 위에 @Configuration 정의
```java
@Configuration
public class ProjectSecurityConfig{
    
	@Bean
      /*@Order(SecurityProperties.BASIC_AUTH_ORDER)*/  //@Order은 필요 없음
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
		http.formLogin(withDefaults());
		http.httpBasic(withDefaults());        
        
		return http.build();
	}
    
    
}
```
* SecurityFilterChain bean 이 웹 애플리케이션에서 자체 맞춤 요구사항을 정의하고자 할 때 매우 중요함!

<br>


### 주요 참고 사항
* Spring Security 6.1 과 Spring Boot 3.1.0 버전을 시작으로 Spring Security 프레임워크 팀은 API, 웹 경로 등의 보안을 구성하기 위해 람다(Lambda) DSL 스타일 사용을 권장함
* 따라서 프레임워크 내의 몇몇 메소드를 사용하지 못하도록 설정되었음! (내가 스프링시큐리티6 공부를 시작하게 된 이유)
* 이러한 사용되지 않는 메소드들은 앞으로 2-3년 내에 출시될 예정인 Spring Security 7에서 제거될 예정이라고 함 
* (개발자들이 코드를 이전할 수 있는 충분한 시간을 제공하기 위해서) 
* 그러나 이 변동 사항은 기본 개념을 변경하는 것은 아님
* 일반 Java 구성을 사용하는 대신 -> 이제 람다 DSL을 사용하게 되었음
* 두 스타일 간의 차이를 보여주는 예제 코드
  * 람다 DSL 미적용(이전)
```
http.authorizeHttpRequest()
    .requestMatchers("/myAccount","/myBalance","/myCards").authenticated()
    .requestMarchers("/notices","/contact").parmitAll()
    .and().formLogin()
    .and().httpBasic();
```
  * 람다 DSL 적용
```
http.authorizeHttpRequest( (requests)-> requests.requestMatchers("/myAccount","/myBalance","/myCards").authenticated()
                                                .requestMarchers("/notices","/contact").parmitAll()
                                                .formLogin(Customizer.withDefaults())
                                                .httpBasic(withDefaults())  
                                                 );
```
* 그러나 이 강의는 람다 DSL 대신 일반 Java 형식의 설정이 사용되었으므로 나는 변경해서 따라해보기로 함
* 물론 강사님의 GitHub 저장소의 코드에 Lambda DSL 형식을 사용하도록 업데이트되었으므로 막힐때 참고하기
* 람다 DSL가 사용되는 이유
  * 자동 들여쓰기가 구성을 더 읽기 쉽게 만듦
  * 설정 옵션을 연결할 때 .and()를 사용할 필요가 없음
  * Spring Security DSL은 스프링 인테그레이션과 스프링 클라우드 게이트웨이와 같은 다른 스프링 DSL들과 유사한 설정 방식을 가지므로
* Spring Security 팀이 변경사항에 대해 발표한 공식 안내
  * https://docs.spring.io/spring-security/reference/migration-7/configuration.html

<br>


### 커스텀 필터 요구사항에 따라 코드 수정하기
* SecurityFilterChain bean을 활용해서 맞춤형 보안 설정을 정의하기
* requestMatches 메서드 : API경로를 무제한으로 받아들임
* authenticated 메서드 : API를 보호함
* permitAll 메서드 : API가 보호되지 않도록 함
* 삭제하기
  * 현재 제공된 코드의 anyrequest().authenticate(); : 모든 요청을 디폴트로 보호한다는 뜻
* 위의 메서드를 토대로 SecurityFilterChain bean의 도움을 받아서 강의 요구사항을 보안 맞춤형 URL정의하기
```java
@Configuration
public class ProjectSecurityConfig{
    
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequest( (requests)-> requests.requestMatchers("/myAccount","/myBalance","/myCards").authenticated()
                                                .requestMarchers("/notices","/contact").parmitAll()
                                                .formLogin(Customizer.withDefaults())
                                                .httpBasic(withDefaults()
                                                ));
		return http.build();
	}
    
    
}
```

<br>


### 모든 요청 거부 처리
* 운영상 추천하지는 않음
* denyAll()를, anyRequest()와 함께 호출하기
```
http.authorizeHttpRequests((requests) -> requests.anyRequest().denyAll() ) // 모든 요청에 대해 접근을 거부
        .formLogin(Customizer.withDefaults())
        .httpBasic(Customizer.withDefaults());

```

* 403 에서 : 권한 부여 거부 에러

<br>


### 모든 요청 허용 처리
* anyRequest()와 함께 permitAll() 호출하기
```
http.authorizeHttpRequests((requests) -> requests.anyRequest().permitAll() ) // 모든 요청 허용
        .formLogin(Customizer.withDefaults())
        .httpBasic(Customizer.withDefaults());

```
* 내용
* 내용
* 내용
* 내용

<br>

### SecurityFilterChain 빈을 환경별로 다르게 활성화 하기
* 운영 상태에서는 보안적용
* 비운영 상태에서는 원활한 개발을 위해 비운영 환경에 맞도록
```java
@Configuration
public class SecurityConfig {

    // 운영 환경에서 활성화되는 SecurityFilterChain
    @Bean
    @Profile("prod")
    public SecurityFilterChain prodSecurityFilterChain(HttpSecurity http) throws Exception {
        // 운영 환경에 맞는 보안 설정
        http
            // 운영 환경 보안 설정 내용
        ;
        return http.build();
    }

    // 비운영 환경에서 활성화되는 SecurityFilterChain
    @Bean
    @Profile("!prod")
    public SecurityFilterChain nonProdSecurityFilterChain(HttpSecurity http) throws Exception {
        // 비운영 환경에 맞는 보안 설정
        http
            // 비운영 환경 보안 설정 내용
        ;
        return http.build();
    }
}

```

* application.properties 파일에서 다음과 같이 운영 환경 프로필을 활성화 하기
```
spring.profiles.active=prod
```

<br>

* yml 파일 내에서 관리하고 싶을 때
```yml
spring:
  profiles:
    active: dev # 기본으로 활성화할 프로필 설정

---
spring:
  config:
    activate:
      on-profile: dev
  # 개발 환경에 특화된 설정들
  datasource:
    url: jdbc:mysql://localhost:3306/devdb
    username: devuser
    password: devpass

---
spring:
  config:
    activate:
      on-profile: prod
  # 운영 환경에 특화된 설정들
  datasource:
    url: jdbc:mysql://prod-db-host:3306/proddb
    username: produser
    password: prodpass
```

* SecurityConfig 클래스 설정
```java

@Configuration
public class SecurityConfig {

    // 운영 환경용 보안 설정
    @Bean
    @Profile("prod")
    public SecurityFilterChain prodSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            // 운영 환경용 보안 설정 구성
            .authorizeRequests()
                .anyRequest().authenticated()
            .and()
                .formLogin()
            .and()
                .httpBasic();
        return http.build();
    }

    // 개발 환경용 보안 설정
    @Bean
    @Profile("dev")
    public SecurityFilterChain devSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            // 개발 환경에서는 모든 요청에 대해 접근을 허용
            .authorizeRequests()
                .anyRequest().permitAll()
            .and()
                .csrf().disable(); // CSRF 보호 기능 비활성화
        return http.build();
    }
}
```



