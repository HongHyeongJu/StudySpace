# 유데미 Spring Security 6 초보에서 마스터 되기 최신강의! (JWT , OAUTH2 포함)
## ch.01 - 소개 및 개요

<br>

### Spring Security로 기본 앱 보안 강화
* Spring Security 의존성을 추가하는 것 만으로도 기존에 접근 가능한 url 입력시 로그인 창이 생김
* 단지 첫번째 요청때만 자격증명함. 새로고침시에는 효과가 없음

<br>

### 애플리케이션 속성 파일 안에 있는 정적 인증 정보 설정
* 애플리케이션 설정 파일에서 설정하기
* 설정 파일의 키워드를 명확히 모를때는 Spring 공식 웹페이지를 참고하기


<br>


### Spring Security를 배워야 하는 이유
* Spring Security 프레임워크에 의존하면 보안에 집중하는 코드를 사용할 수 있게 되므로써 비즈니스 로직을 작성하는데 집중할 수 있음
* Spring Security 프레임워크를 사용할 때마다 최소한의 구성으로 애플리케이션을 쉽게 보호할 수 있음
* CSRF나 CORS와 같은 흔한 보안 취약점도 다루기 때문에 최신 패치를 활용하여서 새로운 취약점을 보완할 수 있음

<br>


### 서블릿과 필터
* 어떤 java 웹 어플리케이션이든지 요청을 받은 후 그것을 HTTP 프로토콜로 전송함
* 브라우저들은 HTTP 프로토콜만 이해할 수 있기 때문에
* 서블릿 컨테이너(웹서버 ex-Apache Tomcat)는 Java코드와 브러우저 사이의 중재자
  * 하는 일
    * 브라우저로부터 받은 HTTP 메세지를 ServletRequest object 로 변환함
    * 그리고 이 동일한 object 를 웹 애플리케이션에 사용하고 있는 Java 코드 프레임워크에 제공함
    * 브라우저에 다시 요청을 보내려고 할 때 서블릿은 HTTP ServletResponse object를 가져다가 브라우저가 이해할 수 있는 HTTP 메시지로 변환함
* 이렇게 서블릿은 웹 어플리케이션에서 중요한 역할을 하지만 복잡하기 때문에 직접 사용하지 않고, Spring Boot와 Spring 프레임워크를 이용하는 것
* 필터는 특별한 종류의 서블릿, 웹 애플리케이션을 향해 들어오는 모든 요청을 가로챌 수 있음
* 따라서 실질적인 비즈니스 로직이 실행되기 전에 일어났으면 하는 프리 로직이나 프리워크를 정의할 수 있음

<br>


### Spring Security 내부 흐름
* 스프링 시큐리티에서 '엔드유저'
  * 엔드 유저 : 보안 시스템을 통해 보호되는 애플리케이션 또는 시스템을 실제로 사용하는 최종 사용자를 의미
  * 즉, 엔드 유저는 소프트웨어, 웹 사이트, 네트워크 서비스 등의 제품이나 서비스의 실질적인 수혜자
* [1]. 유저가 로그인 페이지에서 본인의 자격증명을 입력
  * 요청을 백엔드 웹 애플리케이션에 전송할 때 Spring Security의 도음을 받아 보호함
  * 서블릿 컨테이너/톰캣 서버/엡 애플리케이션을 요청 수신할 때 Spring Security의 필터가 백엔드 서버에 들어오는 모든 요청을 감시해줌
  * 이 필터들은 엔드유저가 접근하는 경로를 확인해줌(보호된 자원/공개된 자원 판별)
* [2]. 인증 성공시 Spring Security필터들은 유저의 로그인 여부를 체크하는 로직을 갖춤
  * 로그인 페이지를 강제하는 것이 아니라 첫번째 로그인에서 발생한 기존 세션 ID나 기존 토큰을 활용함
  * Spring Security 프레임워크 안에는 20개 이상의 필터가 있음(애플리케이션 보호에 매우 중요)
  * Spring Security 필터는 엔드유저가 보내는 유저네임과 비밀번호를 추출하고 2단계 안의 인증 객체로 변환함
  * 인증객체는 Spring Security 프레임워크 안에서 엔드유저의 정보를 저장하기 위한 핵심 표준
* [3]. 인증객체가 형성되면 Spring Security 필터들은 이 요청을 인증관리자에게 넘김(authentication manager)
  * 인증관리자는 실질적인 인증 로직을 관리하는 관리 인터페이스 or 클래스
  * 인증관리자가 하는 일 : 웹 애플리케이션 안에 어떤 인증 제공자가 존재하는지 확인함(authentication providers)
  * 인증제공자 정의가 가능하다면 내부에 실질적인 인증 로직을 정의할 수 있음. (어떤 유저 자격 증명을 확인할 것인지)
  * 유효한 인증제공자가 어떤 것인지 확인하는 것이 인증 관리자의 책임
  * 인증관리자는 이 요청이 인증성공/인증실패 지점에 도달할 때까지 해당 인증 관리자들에게 요청을 전달함
    * ex) 인증에 실패한 시나리오 : 단순히 로그인 실패했다는 응답을 엔드 유저에게 반환하는 것이 아님.
    * 대신 가능한 모든 인증 제공자들을 시도할 것임. 모든 시도에서 인증 실패해야 엔드 유저에게 인증 실패했다고 응답함
* [4]. 인증 제공자 안에 직접 로직을 작성하거나 UserDetailsManager/UserDetailService라는 Spring Security 제공 인터페이스와 클래스를 활용할 수 있음
  * 모든 사전 정의된 로직은 대부분의 프로젝트에서 요구하는 공통 로직임(저장소에서 유저 정보를 불러와서 엔드유저가 제공한 정보와 비교)
  * 이 로직은 이미 UserDetailsManager/UserDetailService 인터페이스에서 제공함
  * 물론 원한다면 직접 작성 가능함
  * 중요한 것은 인증 제공자 내부에 인증을 수행하는 모든 비즈니스 로직을 작성한다는 것
  * 인증서 내부의 비밀번호 인증 시 항상 암호화/해싱을 해야함
  * 모든 비밀번호 관련 표준과 로직을 수행하기 위해 Spring Security 프레임워크 안에는 Password Encoder 인터페이스와 구현내용이 있음
  * 따라서 UserDetailsManager/UserDetailService/Password Encoder는 엔즈 유저가 제공한 자격 증명이 유효한지 협동해서 판별함
  * 그 결과에 따라 인증제공자에게 자격증명이 유효한지, 성공적인지 아닌지를 알려줌
* [5]. 인증 제공자의 프로세싱이 끝나면 이를 다시 인증 관리자에게 넘김
  * 인증 관리자로부터 이 정보는 Spring Security 필터들로 돌아감
  * 응답을 엔드유저에게 전송하기 전에 Spring Security 필터들은 2단계에서 이들이 생성한 인증 객체를 보안 컨텍스트 안에 저장하려고 함
  * 보안 컨텍스트 내부에 저장하려는 인증 객체는 인증성공 여부,세션ID 등의 정보를 갖게됨
  * 9단계인 보안 컨텍스트 내부에 이런 인증 정보를 저장하고 있기 때문에 엔드 유저의 첫번째 로그인 단계에서 인증이 성공적이었다면 두번째 요청부터는 해당 유저의 자격 증명을 요구하지 않음

<br>


### Spring Security 몇가지 중요한 내부 필터
#### Authorization 필터
* 책임 : 엔드 유저가 접근하고자 하는 URL에 접근을 제한하는 것
* 공개 URL이라면 응답은 엔드유저에게 자격증명을 요구하지 않고 바로 표시됨
* 보안 URL에 접근하고자 한다면 해당 URL의 접근을 멈추고, 해당 요청을 Spring Securtity 필터체임의 다음 필터로 리다이렉트 함
  * 내부 코드를 보면 doFilter라는 메소드가 권한 부여 관리자의 도움을 받아서 특정 URL이 공개/보안인지 체크 후 접근 허용/거부 함
#### DefaultLoginPageGenerating 필터
* 보안 URL에 접근하려고 할 때 로그인 페이지를 보여주는 것이 바로 이 필터의 도움
* generateLoginPageHtml 메서드를 
  * 로그인 페이지 관련 HTML 코드가 존재함
  * 꾸미는 것은 부트스트랩 이용함
  * form 태그와 Post, 전송 URL 구성 방법을 확인할 수 있음
#### UsernamePasswordAuthentication 필터
* 엔드 유저가 본인의 유저네임(loginId), 비밀번호같은 자격증명을 입력하고 나면 등장함
* attemptAuthentication이라는 메소드
  * 책임 : 수신하는 http의 출력요청으로부터 유저 네임과 비밀번호를 추출하는 것
  * 이 요청으로 추출한 정보로 UsernamePasswordAuthenticationToken 객체를 생성함
  * UsernamePasswordAuthenticationToken 객체
    * 이 클래스는 인증 인터페이스를 구현함(implements Authentication)
    * Authenticate 메서드를 호출해서 인증 관리자에게 넘기게됨
    * 인증 관리자(class ProviderManager)도 AuthenticationManager 인터페이스의 구현임
      * authenticate라는 메서드가 있음
      * 역할 : 프레임워크 내에 사용 가능한 모든 인증 제공자/개발자가 정의한 인증제공자와 상호작용 시도
      * for(AuthenticationProvider provider : gerProviders()) 반복문으로 모든 적용 가능한 인증 제공자를 반복함
      * ProviderManager는 인증 성공이나 인증 실패를 확인할 때 까지 인증 제공자들을 반복함
      * ProviderManager는 Spring Security의 인증 제공자중 DaoAuthenticationProvider 를 호출함(이 클래스는 extends AbstractUserDetailsAuthenticationProvder)
        * ProviderManager가 DaoAuthenticationProvider 클래스의 내부에는 authenticate 메소드에 요청을 전송하고
        * 이 authenticate 메서드 안에서 모든 인증 로직이 반환됨
        * 유저 네임이 무엇인지 불러오고, 이 유저네임을 활용해서 retrieveUser 메서드를 호출함
        * retrieveUser 메서드 내부에서 인증 제공자가 UserDetailsManager 또는 UserDetailsService의 도움을 받는것을 볼 수 있음. (+Password Encoderd)
        * 모든 역할과 책임을 분리해둔 것.
          * 강의의 예시에서는 DaoAuthenticationProvider 클래스의 retrieveUser 메서드가 UserDetailsManager의 구현체 중 하나인 InMemoryUserDetailsManager 클래스의 도움을 받아 유저 자격 증명을 이용함
          * 이 클래스에서는 loadUserByUsername 메서드를 호출
      * retrieveUser 메서드는 UserDetails를 반환함 -> authenticate 메서드 내부에서 유저 정보를 받아서 additionalAuthenticationChecks와 같은 추가 메서드에 전달함(additionalAuthenticationChecks도 DaoAuthenticationProvider의 메서드)
      * additionalAuthenticationChecks 메서드는 UI에서 제공한 비밀번호와 저장소 내부의 비밀번호를 비교함
        * 물론 여기서도 Password Encoder를 활용함
        * this.matches 메소드가 비밀번호가 일치한다는 의미로 true 반환 시 이 응답은 ProviderManager에 전달됨
* 인증 성공적이라면 프레임워크는 보호된 API URL 보안 웹페이지를 표시해줌


<br>



### 인증 정보 없이 요청을 처리하는 과정 이해
* Spring Security 프레임워크가 엔드 유저에게서 수신하는 다수의 요청을 받을 때 유저 자격증명을 반복해서 요구하지 않는 이유
* 사용자가 로그인하면 새로은 세션ID(JSESSIONID)를 재생성해서 브라우저 쿠키로 반환함
* 기본적으로 모든 로그인된 유저에 대해 JSESSIONID값을 생성해서 유저들에게 반복적으로 강제하지 않음
* 물론 이건은 기본 동작이며 엄청나게 안전하지 않기 때문에 JWT토큰을 이용한 고급 접근법이나 프레임워크를 이용해야함(추후 강의)

<br>














