# 유데미 Spring Security 6 초보에서 마스터 되기 최신강의! (JWT , OAUTH2 포함)
## ch.04 - AuthenticationProvider (인증 제공자) 설정과 구현

<br>

### AuthenticationProvider 를 생성해야하는 이유
* Spring Security가 제공하는 DaoAuthenticationProvider도 충분히 필요한 많은 기능을 갖고있지만, 특별한 요구사항(인증 논리)가 필요할 수 있음
* 그래서 맞춤 AuthenticationProvider를 만들어야 함

<br>

### AuthenticationProvider 메서드의 이해
* 2가지 추상 메서드가 있음
  * ```Authentication authenticate(Authentication authentication) throws AuthenticationException;```
    * 역할 : 실제 인증 과정 수행, 인증 정보가 유효하면, 인증된 사용자를 나타내는 새로운 Authentication 객체를 반환함
    * 매개변수 : Authentication 타입의 인증 요청 정보. 일반적으로 사용자의 아이디와 비밀번호 등의 인증 정보가 담겨 있음
    * 반환 값 : 인증이 성공하면, 인증된 사용자의 정보를 담고 있는 Authentication 객체를 반환합니다. 인증에 실패하면, 예외를 발생시킴
  * ```boolean supports(Class<?> authentication);```
    * 역할 : AuthenticationProvider가 특정 Authentication 타입의 객체를 처리할 수 있는지 여부를 결정
    * 매개변수 : 인증 타입을 나타내는 클래스 객체. Authentication 인터페이스를 구현한 클래스 타입이 전달됨
    * 반환 값 : 주어진 인증 타입을 처리할 수 있으면 true, 처리할 수 없으면 false
    * 이 메서드를 사용하려면 Spring Security프레임워크에 AuthenticationProviders의 도움으로 지원하고 싶은 인증의 종류를 알려줘야함
      * ex) public abstract class AbstractUserDetailsAuthenticationProvider의 supports메서드 내부
        * return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
        * UsernamePasswordAuthenticationToken 이라고 명시하고 있음
* UsernamePasswordAuthenticationToken
  * 언제든 사용자를 인증할 때 유저 이름과 비밀번호의 도움으로 사용할 수 있음
* TestingAuthenticationToken
  * 이 토큰은 많은 보안을 할애하고 싶지 않은 unit testing에서 사용하고 싶을 때 언제든 사용할 수 있음
  * 이 토큰은 TestingAuthenticationProvider라는 인증 제공자에서 사용되고 있음
  * TestingAuthenticationToken 클래스에 가보면 authenticate 메서드에 아무런 인증 로직이 없음!

<br>


### 소제목
* 내용
* 내용
* 내용
* 내용
* 내용
* 내용

<br>


### 소제목
* 내용
* 내용
* 내용
* 내용
* 내용
* 내용

<br>


### 소제목
* 내용
* 내용
* 내용
* 내용
* 내용
* 내용

<br>


### 소제목
* 내용
* 내용
* 내용
* 내용
* 내용
* 내용

<br>


### 소제목
* 내용
* 내용
* 내용
* 내용
* 내용
* 내용

<br>














