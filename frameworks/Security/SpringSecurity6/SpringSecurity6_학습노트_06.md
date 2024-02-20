# 유데미 Spring Security 6 초보에서 마스터 되기 최신강의! (JWT , OAUTH2 포함)
## ch.06 - CORS와 CSR

<br>

### 이전 강의 프로젝트 업데이트 하기
* 공지사항 정보와 함께 캐시에 관련된 헤더정보도 전달해서
* 어떤 공지사항 정보를 보내든 다음 60초 동안 그것을 이용하라는 의미
  * = 60초 이내의 새로고침은 RESR API 요청이 이루어지지 않음
  * 캐시 안에 들어있는 공지사항 정보는 60초 동안 유효함
```java
@RestController
public class NoticesController {

    @Autowired
    private NoticeRepository noticeRepository;

    @GetMapping("/notices")
    public ResponseEntity<List<Notice>> getNotices() {
        List<Notice> notices = noticeRepository.findAllActiveNotices();
        if (notices != null ) {
            return ResponseEntity.ok()
                    .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
                    .body(notices);
        }else {
            return null;
        }
    }

}
```


<br>

### CORS 에러 소개
* CORS는 Cross Origin Resource Sharing의 약자
  * 두 출처가 자신들의 자원을 공유하는 것
    * 출처(Origin) = URL =  프로토콜(HTTP) + 호스트(도메인) + 포트
  * 서로 다른 두 애플리케이션이 소통하는 것 = 교차출처
* 이런 종류의 소통이 일어날 땐 Chrome, Firefox, Safari와 같은 최신 브라우저들은 기본적으로 이런 소통은 차단함
  * 보안적 특성 때문에 최신 프로세스는 두 개의 서로 다른 출처에서 소통하는 것을 특히 특별한 설정 없이 할 경우 소통을 차단함
* 보안 위협, 공격은 아니지만 보안 위험으로 부터의 보호 차원의 단계

<br>


### CORS를 해결할 수 있는 여러가지 방법
* 브라우저에게 출처가 다른 곳에서의 소통을 허락 받을 수 있는 방법
* 강의 예제에서 CORS 에러가 일어난 이유 : 포트가 다르기 때문에
* 해결 방법 2가지
  * Spring Security 프레임워크의 도움 받기
    * 1. REST Controller로 주석을 달아둔 클래스 or REST API들을 정의해둔 곳에 @CrossOrigin 주석을 달기 
      *  @CrossOrigin 주석을 달면 어느 출처에서 소통을 받으려는 것인지 말해주는 것
      * 백엔드 애플리케이션 쪽에서는 소통을 받으려는 또 다른 출처를 설정해주기 
      * ex) ```@CrossOrigin(drigins="http://localhost:4200")```
      * ex) ```@CrossOrigin(drigins="*")``` : 어떠한 도메인과도 소통할 준비가 되었다고 알려주는 것
      * 브라우저가 백엔드 애플리케이션에 설정하는 것을 알아듣는 이유 : 브라우저들은 대부분 pre-flight 요청을 함
        * pre-flight 요청 :  본 요청을 서버에 보내기 전에 브라우저가 먼저 서버에 보내는 "예비 요청". 이 요청은 실제 요청을 안전하게 처리할 수 있는지 서버에 확인하기 위해 사용함
        * pre-flight 요청 특징 
          * 목적 : 브라우저가 본 요청을 보내기 전에, 해당 요청이 서버의 CORS 정책에 부합하는지 확인하기 위해서. 즉 서버가 다른 출처에서 오는 요청을 허용하는지, 그리고 어떤 HTTP 메소드와 헤더가 허용되는지 미리 확인하는 것.
          * 방식 :  HTTP OPTIONS 메소드를 사용하여 이루어짐. 실제 요청을 실행하기 전에 서버의 capabilities를 조회하기 위한 메소드
          * 헤더 : Pre-flight 요청은 Origin, Access-Control-Request-Method (실제 요청에서 사용될 HTTP 메소드), Access-Control-Request-Headers (실제 요청에서 사용될 헤더) 등의 특별한 CORS 요청 헤더를 포함할 수 있음
          * 조건 :  모든 CORS 요청이 pre-flight를 거치는 것은 아님!  단순 요청" (simple requests)은 pre-flight 과정 없이 직접 요청될 수 있음
            * 단순 요청이 아닌 경우 : 사용자 정의 헤더를 사용/ PUT, DELETE, CONNECT 등의 HTTP 메소드를 사용
    * 2. 개별 컨트롤러에 설정하는 것이 아니라 Spring Security 프레임워크의 도움을 받아 글로벌로 설정하기
      * Security FilterChain의 bean 생성할 때 CORS 설정을 정의하기!
```
.cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));  // 소토아고자 하는 출처 리스트 적기
                        config.setAllowedMethods(Collections.singletonList("*"));  //어떤 HTTP메서드를 받고 싶은지 설정하기
                        config.setAllowCredentials(true); //인증정보들을 넘기고 받는데 동의함
                        config.setAllowedHeaders(Collections.singletonList("*"));  //다른 출처에서 오는 모든 종류의 헤더를 애스터리스트(*)로 정의하기
                        config.setMaxAge(3600L);  //브라우저에 이 설정을 1시간동안 기억해 두고, 1시간 지나면 캐시로 변환됨
                        return config;
                    }
                })

```

<br>


### Spring Security로 CORS 문제 해결하기
* CORS 설정은 웹 애플리케이션 내에서 controller level 또는 대외적으로 설정할 수 있음
* CORS 설정을 대외적으로 설정하기(글로벌)
  * config 패키지의  class ProjectSecurityConfig - defaultSecurityFilterChain 메서드 수정
    * http에 CORS라는 새로운 메서드 불러오기. CORS 메서드 호출에는 configureSource(CorsConfigurationSource의 객체) 메서드를 불러와줘야함
      * CorsConfigurationSource는 인터페이스이기 때문에  익명의 내부클래스를 만들기
```
.cors(corsCustomizer -> corsCustomizer.configurationSource(

                    //익명 클래스 시작
                    new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));  // 소토아고자 하는 출처 리스트 적기
                        config.setAllowedMethods(Collections.singletonList("*"));  //어떤 HTTP메서드를 받고 싶은지 설정하기
                        config.setAllowCredentials(true); //인증정보들을 넘기고 받는데 동의함
                        config.setAllowedHeaders(Collections.singletonList("*"));  //다른 출처에서 오는 모든 종류의 헤더를 애스터리스트(*)로 정의하기
                        config.setMaxAge(3600L);  //브라우저에 이 설정을 1시간동안 기억해 두고, 1시간 지나면 캐시로 변환됨
                        return config;
                    }
                    //익명 클래스 끝
                    
                })

```
* setAllowedOrigins 메서드 : 어떤 출처, 도메인, 서버들이 백엔드와 소통하도록 허용되었는지 설정함
* 그러나 이런 정보들을 기입하면 컴파일 에러가 발생
  * 이는 CORS와 CSRF가 서로 다른 설정이기 때문
  * 서로 다른 설정을 정의할 때는 .and()를 호출해야함. 그러나 람다 DSL 이용시에는 생략
* CORS 정보들을 설정하는 건 항상 백엔드 쪽의 역할! 잊지 말기

<br>


### CSRF 공격 
* 보안 공격
* 브라우저 안에 있는 보호 정책 아님 
* 보안 취약성!
* 기본적으로 Spring Security CSRF으로부터 보호를 제공함
  * 어떠한 post 작업도 처용하지 않고, DB에 업데이트를 요구하거나 새 데이터 생성을 허용하지 않음
  * 이러한 동작들은 기본적으로 Spring Security에서 차단시킴
  * 그래서 앞 강의에서 CSRF 보호막을 비활성화하고 강의를 진행함
* 그러나! Spring Security 프레임워크에서 절대 CSRF를 비활성화해서는 안됨 
* csrf.disable() 제거하기

<br>


### CSRF 공격을 처리하는 솔루션
* CSRF
  * Cross-Site Request Forgery
  * CSRF 또는 XSRF
  * 보안공격, 보안위협
  * 백엔드 애플리케이션에서 정보를 빼내가는 것
* CSRF공격에 대처하기 위해서는 백엔드 애플리케이션이 들어오는 HTTP요청이 정당한 유저에게 오고있는지, 혹은 해커에서 오는건 아닌지 잘 구분할 수 있어야 함
  * CSRF 토큰(안전한 랜덤 토큰)을 이용하기
  * CSRF 토큰이 있으면 백엔드 서버는 정당한 유저와 해커를 구분함
  * 자격증명 시 엔드유저에게 인증정보와 관련 쿠키와 함께, 백엔드에서 CSRF 토큰을 랜덤하게 생성해서 동일하게 UI로 전달함
  * 가장 흔한 방법은 CSRF 토큰을 쿠키 자체에 넣어서 전송하기
  * 강의의 공격 시나리오가 안통하는 이유
    * 브라우저의 도움을 받아 기본형식으로 백엔드에 쿠키를 보내는 것이 아니라, 
    * 헤더나 내용(Body/Payload)에 보내야 하고 백엔드 서버의 동의가 있어야 하기 때문에 해커의 코드는 전달되지 않음

<br>


### 공개 API에 대한 CSRF 보호 무시
* Contact, Register 같은 공공 API를 보호할 필요가 있는지
  * 누군가 연락처 정보를 제출하고 애플리케이션을 등록하는 데에 큰 위험이 없기 때문에 이런 종류의 공공  API는 CSRF 공격을 다룰 필요가 없음
  * 민감한정보가 없기 때문에
* Spring Security 프레임워크에게 공공 API를 알려주기
  * CORS다음에 CSRF 메소드를 불러오기.
  * csrf().ignoringRequestMatchers() 연쇄 호출하기 
  * ignoringRequestMatchers() : 일치하는 요청 무시하기 메서드
  * ```.csrf((csrf) -> csrf.csrfTokenRequestHandler(requestHandler).ignoringRequestMatchers("/contact","/register")...```
  * notices API 를 명시하지 않은 이유 : Get API 이기 때문에 상관 없음(항상 백엔드에서 데이터를 가져옴. 업데이트가 아님)

<br>



### 웹 애플리케이션에서 CSRF 토큰 솔루션 구현하기
* **defaultSecurityFilterChain 메서드 안에 CsrfTokenRequestAttributeHandler 객체 생성해주기**
  * ```CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();```
  * CsrfTokenRequestAttributeHandler 클래스는  CSRF 토큰이 요청 속성으로써 활성화 될 수 있도록 도와줌. 헤더로든, 변수로든 토큰 값을 해결함
  * (뜻) : Spring Security 프레임워크가 CSRF 토큰 값을 생성하고 값이 처리되거나, UI 애플리케이션에게 헤더 또는 쿠키의 값을 전달하기 위해서 이 AttributeHandler 클래스의 도움을 받아야 함. 그래서 AttributeHandler를 사용해서 같은 이름의 객체를 만듦
  * 생성한 객체에 setCsrfRequestAttributeName 메서드 호출하기. 매개변수로는 "_csrf" 전달 (물론 언급하지 않아도 Handler가 같은 이름을 생성한다고함. 가독성을 위해 기입)
```
CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
requestHandler.setCsrfRequestAttributeName("_csrf");
```
* **이전에 만든 csrf 메서드 수정하기** 
  * .csrf라는 두가지 메서드 중에 매개변수를 받는 2번째 메서드 선택. 람다식으로 인수 제공
```
.csrf((csrf) -> csrf.csrfTokenRequestHandler(requestHandler).ignoringRequestMatchers("/contact","/register")
                 .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
```
* csrfTokenRequestHandler 다음에 ignoringRequestMatchers를 블어와야 뒤의 공공 API URL에게 csrf가 작동하지 않음
  * 그 이후에는 csrfTokenRepository 메서드 불러와서 CookieCsrfTokenRepository.withHttpOnlyFalse() 전달하기
  * CookieCsrfTokenRepository 클래스 설명
    * A CsrfTokenRepository that persists the CSRF token in a cookie named "XSRF-TOKEN" 
    * and reads from the header "X-XSRF-TOKEN" following the conventions of AngularJS. 
    * When using with AngularJS be sure to use withHttpOnlyFalse().
    * 쿠키를 유지하는 역할, 헤더에서 X-XSRF-TOKEN 이 이름으로 찾음
    * 쿠키에 사용될 이름 : ```static final String DEFAULT_CSRF_COOKIE_NAME = "XSRF-TOKEN";```
    * 헤더에 사용될 이름 : ```static final String DEFAULT_CSRF_HEADER_NAME = "X-XSRF-TOKEN";```
    * 그러면 .withHttpOnlyFalse()는 왜 불러올까?
      * Spring Security 프레임워크에게 "HttpOnlyFalse를 설정으로 쿠키를 생성해줘야 어플케이션의 JS코드가 쿠키 값 읽을 수 있다고 말해주는 것
* **마지막으로 남은 단계**
  * 백엔드 앱에서 첫 로그인 후 UI어플리케이션에게 헤더와 쿠키 값을 보내야 UI앱이 CSRF 토큰 값을 알 수 있음
  * CSRF 토큰을 보내려면 UI 애플리케이션에 보내는 모든 응답에 filter 클래스를 생성해야함
  * filter 패키지 생성 
    * CsrfCookieFilter 클래스 생성
    * 이 클래스를 필터로 만들기 위해서는 Spring Security가 제공한 필터 중 하나를 확장해야 함
      * 필터 이름은 OncePerRequestFilter
      * ```public class CsrfCookieFilter extends OncePerRequestFilter ```
      * doFilterInternal 메서드 오버라이드 하기
```java
public class CsrfCookieFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // HttpServletRequest 안에서 가능한 CsrfToken를 읽을 수 있게 하기
        // 객체 안에 헤더 이름 확인하기
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        // null이 아닐 경우 프레임워크가 CSRF 토큰을 생성했다는 뜻 일 수 있음
        if(null != csrfToken.getHeaderName()){
                                //같은 HeaderName을 채우기  //응답이 헤더 안에 있는 토큰 값
            response.setHeader(csrfToken.getHeaderName(), csrfToken.getToken());
        }
        
                                        //같은 응답이 FilterChain 안에 있는 다음 필터에게 전달됨
        filterChain.doFilter(request, response);
    }

}
```
* CSRF 토큰을 헤더안에 넣기 성공! 이후는 Spring Security가 도와줌
* 개발자로써 CSRF 토큰 값을 채우면 응답 헤더로써 Spring Security 프레임워크는 CSRF 쿠키를 보내고 동일한 것을 브라우저나 UI 애플리케이션에 응답 보내는 것에 신경써줌
* **필터와 Spring Security 프레임워크 소통 시키기**
  * csrf코드 이후에 ```.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)``` 추가하기
    * 두번째 인자로 넘기는 BasicAuthenticationFilter
      * HTTP Basic Authentication을 사용할 때 중요한 역할을 함
    * 이 코드의 뜻은 Spring Security 프레임워크에게 '"BasicAuthenticationFilter를 먼저 실행하고 CsrfCookieFilter를 실행해줘'라고 하는 것
      * BasicAuthenticationFilter 후에만 로그인 동작이 완료될 수 있고, 로그인 동작이 완료되면 CSRF토큰이 생성됨
* **cors메서드 전에 코드 추가하기 :  스프링 시큐리티의 보안 컨텍스트 및 세션 관리 설정 구성내용**

```
// 보안 컨텍스트의 저장 방식을 구성합니다.
// requireExplicitSave가 false로 설정되면, 스프링 시큐리티는 보안 컨텍스트를 저장소(예: HTTP 세션)에 자동으로 저장하려고 시도합니다.
securityContext((context) -> context.requireExplicitSave(false))

// sessionManagement 설정은 세션 관리 전략을 정의합니다.
// sessionCreationPolicy(SessionCreationPolicy.ALWAYS)는 스프링 시큐리티가 요청이 들어올 때마다 항상 새로운 세션을 생성하도록 지시합니다.
.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))

```  
  
* 이 설정의 목적
  * 현재 로그인해서 REST API들에 접근할 때 Spring Security 프레임워크에게 _“내가 만든 sessionManagement(세션생성전략)를 따라서 JSESSIONID를 만들어줘"_ 라고 함
  * 이 설정을 통해 Spring Security 프레임워크에게 _"첫 로그인이 완료되면 항상 JSESSIONID를 생성해줘"_ 라고 지시하게됨]
  * 이 2줄의 코드가 없으면 매번 보안된 API에 접근할 때마다 어플에서 자격증명을 해야함
  * context.requireExplicitSave(false)
    * 기본적으로 이 값은 true였음. 현재 기본설정을 덮어씌우고 있음
    * 의미 : "내가 SecurityContextHolder 안에 있는 인증 정보들을 저장하는 역할을 맡지 않을 거야. 프레임워크들이 대신 수행하게 해줘" 라고 합니다
    * SecurityContextHolder 안에 인증정보 저장함

<br>

* 백엔드 코드 수정 완료.
* **프론트엔드 측에서 CSRF 토큰을 관리하는 방법**
  * UI 애플리케이션쪽도 CSRF 토큰 값을 받고 나중에 있을 후속 요청에 동일한 CSRF 토큰 값을 보낼 수 있어야 함
    * 로그인 동작이 호출되고 완료된 이후
      * 모든 응답 받기 ex) 쿠키, responseData안의 body,
      * 쿠키를 읽고 sessionStorage나 어떤 형식으로든 저장해야함
      * 그래야 추후의 클라이언트가 생성할 모든 요청에서도 같은 토큰을 몇 번이고 보내줄 수 있음
  * 동작 단계
    * 1. CSRF 토큰 수신
      * 사용자가 로그인하면 서버는 CSRF 토큰을 생성하고, 이 토큰을 클라이언트(즉, 사용자의 브라우저)에 전달합니다. 
      * 토큰 전달 방법은 보통 쿠키를 통하거나, 로그인 응답의 일부로 responseData 내에 포함시켜서 할 수 있습니다.
    * 2.
      * 프론트엔드는 서버로부터 받은 CSRF 토큰을 저장해야 합니다. 
      * 저장 방법으로는 세션 스토리지(sessionStorage), 로컬 스토리지(localStorage), 또는 애플리케이션의 상태 관리 저장소 등이 있을 수 있습니다. 
      * 이 토큰은 나중에 서버로 요청을 보낼 때 사용됩니다.
    * 3. 후속 요청에 토큰 사용
      * 사용자가 서버에 요청을 보낼 때마다(예: 폼 제출, AJAX 요청 등), 프론트엔드는 저장해둔 CSRF 토큰을 요청과 함께 서버로 보내야 합니다. 
      * 보통 이 토큰은 요청의 헤더에 추가하거나, 요청 본문에 포함시켜 전송합니다.
    * 4. 서버의 토큰 검증
      * 서버는 받은 요청에서 CSRF 토큰을 추출하고, 세션에 저장된 토큰과 비교하여 검증합니다.
      * 토큰이 일치하면 요청을 수행하고, 일치하지 않으면 요청을 거부합니다.

<br>



### 프론트엔드 측에서 CSRF 토큰 받고 전달하기
* CSRF 토큰 수신 및 세션 스토리지에 저장
* XSRF-TOKEN 쿠키 읽고 sessionStorage에 저장하기
```javascript
function getCookie(name) {
    let cookieValue = null;
    if (document.cookie && document.cookie !== '') {
        const cookies = document.cookie.split(';');
        for (let i = 0; i < cookies.length; i++) {
            const cookie = cookies[i].trim();
            // Does this cookie string begin with the name we want?
            if (cookie.substring(0, name.length + 1) === (name + '=')) {
                cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                break;
            }
        }
    }
    return cookieValue;
}

// XSRF-TOKEN 쿠키 읽기
const xsrfToken = getCookie('XSRF-TOKEN');
// sessionStorage에 저장하기
if (xsrfToken) {
    sessionStorage.setItem('XSRF-TOKEN', xsrfToken);
}

```

* 후속 요청에 XSRF-TOKEN 포함하기

```javascript
// 세션 스토리지에서 XSRF-TOKEN 읽기
const xsrfToken = sessionStorage.getItem('XSRF-TOKEN');

// 후속 요청에 XSRF-TOKEN 포함하여 보내기
fetch('/api/some-endpoint', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json',
        // 서버가 요구하는 CSRF 토큰 헤더 이름에 맞추어 값을 설정
        // 일반적으로 'X-XSRF-TOKEN'이 사용되지만, 서버 설정에 따라 다를 수 있음
        'X-XSRF-TOKEN': xsrfToken
    },
    body: JSON.stringify({ data: 'yourData' })
}).then(response => {
    // 응답 처리
}).catch(error => {
    // 오류 처리
});
```

### BasicAuthenticationFilter
* HTTP Basic Authentication 사용할 때 Spring Security 안에 필터가 하나 더 있음
* 굉장히 중요한 역할을 하는 BasicAuthenticationFilter 
  * doFilterInternal 메서드
    * Authentication authRequest = this.authenticationConverter.convert(request);
    * authenticationConverter 컨버터가 생성됨
    * request를 변환하고
    * UsernamePasswordAuthenticationToken 객체를 보내줌 
* 컨버터 안에는 무엇이 있을까?
  * BasicAuthenticationConverter 클래스의  convert 메서드
  *  HTTP 요청에서 기본 인증(Basic Authentication) 헤더를 추출하고 이를 사용해 UsernamePasswordAuthenticationToken 객체를 생성하는 과정을 담당함
  * 구현과정
    * Authorization 헤더 가져오기
      * if (header == null) : HTTP 요청에서 Authorization 헤더를 가져오기
    * Basic 인증 스킴 확인
      * if (!StringUtils.startsWithIgnoreCase(header, AUTHENTICATION_SCHEME_BASIC)) {return null;}
      * 가져온 헤더 값이 "Basic"으로 시작하는지 확인
      * AUTHORIZATION라는 이름의 헤더가 있다면 헤더 값은 "Basic"이고 usernmae과password의 인코딩된 값임
    * 인증 토큰 디코딩
      * Basic" 다음에 오는 Base64로 인코딩된 문자열을 디코딩하여 사용자 이름과 비밀번호를 추출
      * String token = new String(decoded, getCredentialsCharset(request)); 
      * int delim = token.indexOf(":");
    * 사용자 이름과 비밀번호 분리
      * 사용자 이름과 비밀번호는 : 문자로 구분됨
    * UsernamePasswordAuthenticationToken 생성
      * UsernamePasswordAuthenticationToken result = UsernamePasswordAuthenticationToken
	  * .unauthenticated(token.substring(0, delim), token.substring(delim + 1));
        * UsernamePasswordAuthenticationToken.unauthenticated 메서드 : UsernamePasswordAuthenticationToken 객체를 생성하기 위해 안전하게 사용될 수 있는 메소드
    * 인증 상세 정보 설정
      * result.setDetails(this.authenticationDetailsSource.buildDetails(request));
      * 요청으로부터 추가적인 인증 상세 정보를 가져와 토큰에 설정함
      * 일반적으로 클라이언트의 IP 주소, 세션 ID 등 요청에 대한 메타데이터를 포함
    * 결과 반환
      * 생성된 UsernamePasswordAuthenticationToken 객체를 반환됨
      * return result;

<br>

### 추가
* addFilterAfter 메소드는 스프링 시큐리티의 HttpSecurity 객체를 통해 호출되며, 
* 이 메소드는 첫 번째 매개변수로 전달된 필터를 두 번째 매개변수로 지정된 필터 이후에 실행되도록 필터 체인에 추가함
  * ex)  addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class) 
  * 첫번째 호출 필터 : BasicAuthenticationFilter.class  //기본 인증 로직 적용
  * 두번째 호출 필터 : new CsrfCookieFilter()  // CSRF토큰 확인














