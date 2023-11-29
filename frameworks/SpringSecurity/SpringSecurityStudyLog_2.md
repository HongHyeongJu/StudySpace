### CSRF 사이트 간 요청 위조
* 사이트 간 요청 위조로부터 보호하는 방법
  * 동기화 토큰 패턴 이용하기 : 요청마다 토큰을 생성하기. 사용자가 수행하는 작업 각각에 새 토큰 생성하기
    *  POST, PUT과 같은 업데이트 요청이 있을 때 이 토큰으로 인증하기. 애플리케이션에서 업데이트 작업을 할 때마다 이전 요청에서 생성된 토큰 사용하기
  * SameSite 쿠키를 이용하기

<br>

### 1. 동기화 토큰 패턴 이용
* 로그아웃 페이지 소스보기
  * form 태그 사용
  * /logout URL로 post 요청을 실행
  * post 요청이므로 토큰 확인해야 함
  * 생성된 토큰 : value="tXOsCuvAplPHVlIj_Ksv3B8IxE515DGAmxk4jmCEzlhOQevRjRfPPtikkWfqZGYXn4Yb6ik_6XcW0QGt-H8MvAbi_WgvJYnn"
````html
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Confirm Log Out?</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <link href="https://getbootstrap.com/docs/4.0/examples/signin/signin.css" rel="stylesheet" crossorigin="anonymous"/>
  </head>
  <body>
     <div class="container">
      <form class="form-signin" method="post" action="/logout">
        <h2 class="form-signin-heading">Are you sure you want to log out?</h2>
<input name="_csrf" type="hidden" value="tXOsCuvAplPHVlIj_Ksv3B8IxE515DGAmxk4jmCEzlhOQevRjRfPPtikkWfqZGYXn4Yb6ik_6XcW0QGt-H8MvAbi_WgvJYnn" />
        <button class="btn btn-lg btn-primary btn-block" type="submit">Log Out</button>
      </form>
    </div>
  </body>
</html>
````
<br>

* REST API에서는 어떻게 CSRF 토큰을 확인할까? (결론 : 상태가 없는 REST API를 사용한다면 CSRF를 사용 해제하는 게 좋습니다)
* 상태를 저장하지 않는 Rest API 같은 경우에는 CSRF가 필요하지 않음
* CSRF는 일반적으로 세션이나 세션 쿠키와 관련이 있음. 따라서 항상 필요하지는 않다
* 상태가 없는 REST API를 사용한다면 CSRF를 사용 해제하는 게 좋습니다

[CSRF 사용하기]
````java
package com.udemystrudy.learnspringsecurity.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoResource {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final List<Todo> TODO_LIST = List.of(new Todo("hello", "Learn AWS"),
                new Todo("hello", "Get AWS Certified"));


    @GetMapping("/todos")
    public List<Todo>  retriedAllTodos(){
        return TODO_LIST;
    }

    @GetMapping("/users/{username}/todos")
    public Todo  retriedTodosForASpecificUser(@PathVariable String username){
        return TODO_LIST.get(0);
    }

    @PostMapping("/users/{username}/todos")
    public void  creaiteTodosForASpecificUser(@PathVariable String username,
                                              @RequestBody Todo todo){
        logger.info("Creare {} for {}");
    }
}


record Todo (String username, String description) {}

================================
@RestController
public class SpringSecurityPlayResource {

    @GetMapping("/csrf-token")
    public CsrfToken retriedCsrtToken(HttpServletRequest request){
        return (CsrfToken) request.getAttribute("_csrf");
    }
}
````

### 2. SameSite 쿠키 이용하기
* Set-Cookie: SameSite=Strict로 설정해서 사용하면 쿠키는 해당 사이트로만 전송됨
* application.properties에서 
````
server.servlet.session.cookie.same-site=strict  // SpringBoot 2.6부터
````
<br>


#### [CSRF 사용하지 않도록 설정하기]
* Spring Security Filter Chain 전체를 설정하기
* 
* SpringBootWebSecurityConfiguration
````java
/**
 * {@link Configuration @Configuration} class securing servlet applications.
 *
 * @author Madhura Bhave
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = Type.SERVLET)
class SpringBootWebSecurityConfiguration {

	/**
	 * The default configuration for web security. It relies on Spring Security's
	 * content-negotiation strategy to determine what sort of authentication to use. If
	 * the user specifies their own {@link SecurityFilterChain} bean, this will back-off
	 * completely and the users should specify all the bits that they want to configure as
	 * part of the custom security configuration.
	 */
	@Configuration(proxyBeanMethods = false)
	@ConditionalOnDefaultWebSecurity
	static class SecurityFilterChainConfiguration {

		@Bean
		@Order(SecurityProperties.BASIC_AUTH_ORDER)
		SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
			http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
			http.formLogin(withDefaults());
			http.httpBasic(withDefaults());
			return http.build();
		}

	}...
````
<br>

````java
public enum SessionCreationPolicy {

	/**
	 * Always create an {@link HttpSession}
	 */
	ALWAYS,

	/**
	 * Spring Security will never create an {@link HttpSession}, but will use the
	 * {@link HttpSession} if it already exists
	 */
	NEVER,

	/**
	 * Spring Security will only create an {@link HttpSession} if required
	 */
	IF_REQUIRED,

	/**
	 * Spring Security will never create an {@link HttpSession} and it will never use it
	 * to obtain the {@link SecurityContext}
	 */
	STATELESS

}
````

<br>

* 즉, 세션을 생성하거나 사용하지 않고, 각 요청은 독립적으로 인증되어야  
```` java
public class BasicAuthSecurityConfiguration {

    @Bean
    SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
		//모든 요청 ('anyRequest()')에 대해 인증 ('authenticated()')이 필요함을 지정
		
		
        http.sessionManagement() .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //'sessionManagement()'는 세션 관리 전략을 설정
        //'sessionCreationPolicy(SessionCreationPolicy.STATELESS)'는 서버가 상태를 유지하지 않음을 의미
        //즉, 세션을 생성하거나 사용하지 않고, 각 요청은 독립적으로 인증되어야 함

//		http.formLogin(withDefaults());  //기본 로그인 폼 기능이 비활성화

		http.httpBasic(withDefaults()); 
		//'httpBasic()'는 HTTP 기본 인증을 활성화
		//'withDefaults()'는 기본 설정을 사용하겠ek
		//보호된 리소스에 접근 시 사용자 이름과 비밀번호를 묻는 기본 인증 팝업이 브라우저에 표시
		
        http.csrf().disable(); //CSRF(Cross-Site Request Forgery) 보호 기능을 비활성화
        //주로 API 서버나 JWT를 사용하는 경우와 같이 CSRF 공격에 대한 보호가 필요 없는 상황에서 사용
		
		return http.build();
		//HttpSecurity 구성을 완료하고, SecurityFilterChain을 반환함
	}

}
````