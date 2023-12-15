### 스프링 시큐리티 인증이란?
* Spring Security에서 인증의 흐름
* 인증은 Spring Security 필터 체인의 일부로서 이루어짐
* 요청이 유입될 때마다 Spring Security가 그걸 가로채고 필터 체인 전체가 실행됨
* 인증의 시작
  * AuthenticationManager는 인증을 담당하는 인터페이스
  * 설명 : 전달된 객체를 인증하려 시도함
  * 리턴 : 완전히 채워진 Authentication 객체를(+부여된 권한도 함께)
  * 메서드 authenticate() : Authentication을 입력값으로 받고, 출력값도 역시 Authentication
* Spring Security에서 인증
  * [1] 자격증명 (사용자 id과 pw)
  * [2] 주체 (사용자에 관한 세부 정보)
  * [3] 권한 (주체가 가지고 있는 역할과 권한)

<br>

* authenticate() 메서드의 호출
  * 호출되기 전: Authentication에는 자격증명만
  * 인증 성공(=authenticate() 메서드가 성공적으로 호출) : Authentication에 자격증명 + 주체 + 권한

<br>

* AuthenticationManager는 특정한 요청의 인증 방법
  * AuthenticationManager는 수많은 AuthenticationProvider들과 상호작용\
  * 즉 특정한 인증 타입을 제공하는 다양한 AuthenticationProvider들이 있음
  * 앞에서 우리는 JwtAuthenticationProvider를 사용(JWT인증)
  * 그리고 AuthenticationProvider들이 대화하는 인터페이스가 바로 UserDetailsService
* UserDetailsService
  * 사용자 데이터를 로딩하기 위한 핵심 인터페이스
  * 메서드 loadUserByUsername(): 사용자 이름을 넣으면 / 데이터를 받고 / AuthenticationProvider에게 제공
  * AuthenticationProvider는 세부정보를 검증하고 그걸 자격증명과 비교하여 확인

<br>

* Spring Security에서는 다양한 타입의 AuthenticationProvider들이 동시에 작동하고, 
* 또한 동시에 작동하는 다수의 UserDetailsService 구현물이 있을 수도 있음
* 동일한 인증 요청에서 모두와 비교해서 확인할 수 있음
* 그래서 하나의 AuthenticationManager는 다수의 AuthenticationProvider와 대화할 수 있고, 그리고 그에 관련된 UserDetailsService의 구현물도 다수 있을 수 있음

<br>

### 인증에 성공이후 그 결과 저장
* 인증 결과 저장
  * SecurityContextHolder  > SecurityContext > Authentication > GrantedAuthority


<br>

### 스프링 시큐리티 인증 접근법
* **전역 보안**과 **메서드 보안**
* **전역 보안**
  * 전역 보안은 authorizeHttpRequests에서 설정할 수 있음
  * BasicAuthSecurityConfiguration에서 인증에 관련된 더 많은 내용을 설정할 수 있음
  * 예시. 특정한 url을 가져야 할 때 직접 url기준 인증을 설정할 수 있음
```
securityFilterChain메서드 수정
auth -> {
        auth
        .requestMatchers("/users").hasRole("USER")
        .requestMatchers("/admin/**").hasRole("ADMIN")
        .anyRequest().authenticated();
}
```
* 다양한 매처
  * hasRole, hasAuthority, hasAnyAuthority, isAuthenticated
  * 이러한 매처로 개인 인증 규칙을 만들 수 있음
  * 메서드 보안에 사용하기도 함

<br>

* **메서드 보안**
* BasicAuthSecurityConfiguration에 애너테이션 추가 '@EnableMethodSecurity'
* org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity로
* 메서드 보안을 활성화했다면 **@Pre**나 **@Post** 어노테이션 같은 것들을 사용할 수 있음
* 이전에 만은 Todos로 이동해서 메서드에 애너테이션 및 조건 추가
```java
    @GetMapping("/users/{username}/todos")
    @PreAuthorize("hasRole('USER') and #username == authenication.name")
    public Todo  retriedTodosForASpecificUser(@PathVariable String username){
        return TODO_LIST.get(0);
    }
```
* 패스 변수가 인증에 있는 이름과 매칭되어야 한다는 조건
* 리턴 객체를 확인하는 PostAuthorize() 
```java
    @GetMapping("/users/{username}/todos")
    @PreAuthorize("hasRole('USER') and #username == authenication.name")
    @PostAuthorize("returnObject.username() == 'hello'")
    public Todo  retriedTodosForASpecificUser(@PathVariable String username){
        return TODO_LIST.get(0);
    }
```
* 메서드 수준 보안에는 PreAuthorize와 PostAuthorize를 사용하는 것이 좋음

<br>
  
* **JSR-250** 어노테이션
* 사용하려면 BasicAuthSecurityConfiguration의 @EnableMethodSecurity 애너테이션에 (jsr250Enabled=true) 설정
* 그리고 사용하려는 메서드에 @RolesAllowed("ADMIN", "USER") 추가
```java
    @GetMapping("/users/{username}/todos")
    @PreAuthorize("hasRole('USER') and #username == authenication.name")
    @PostAuthorize("returnObject.username() == 'hello'")
    @RolesAllowed("ADMIN", "USER")
    public Todo  retriedTodosForASpecificUser(@PathVariable String username){
        return TODO_LIST.get(0);
    }
```

<br>
  
* 그 외의 어노테이션
  * @DenyAll
  * @PermitAll 
  * Spring이 제공하는 예전의 **@Secured** 어노테이션도 사용할 수 있음
    * 사용 방법 @EnableMethodSecurity(securedEnabled=true)
    * @Secured는 권한과 비교해서 확인
    * 권한을 말할 때는 ROLE_ADMIN, ROLE_USER 라고 해야함
```java
    @GetMapping("/users/{username}/todos")
    @PreAuthorize("hasRole('USER') and #username == authenication.name")
    @PostAuthorize("returnObject.username() == 'hello'")
    @RolesAllowed({"ADMIN", "USER"})
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public Todo  retriedTodosForASpecificUser(@PathVariable String username){
        return TODO_LIST.get(0);
    }
```

<br>

* @Pre와 @Post를 사용 권장
  * 유연성이 높아지고요, 요즘에 많이 쓰는 방식
* 표준을 준수해야 한다면 JSR-250 어노테이션을 사용














