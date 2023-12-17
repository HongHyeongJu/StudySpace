####  Spring Boot 3 & Spring Framework 6 마스터 (2023 Java 최신) [섹션 12]


<br>


## REST API
### Hello World REST API
* Hello World
  * @GetMapping("/hello-world")
* Hello World Bean
  * @GetMapping("/hello-world-bean")
* Hello World Path Variable
  * @GetMapping("/hello-world/path-variable/{name}")

<br>

### Todo REST API
* Retieve Todos
  * @GetMapping("/users/{username}/todos")
* Retieve Todo
  * @GetMapping("/users/{username}/todos/{id}")
* Delete Todos
  * @DeleteMapping("/users/{username}/todos/{id}")
* Update Todos
  * @PutMapping("/users/{username}/todos/{id}")
* Create Todos
  * @PostMapping("/users/{username}/todos")


<br>

### CORS 요청 활성화하기
*  WebMvcConfigurer
  * Spring MVC 애플리케이션의 Java 기반 설정을 사용자 지정하기 위한 콜백 메서드를 정의함
* addCorsMappings() 
  * CORS 매핑을 추가하는 메서드
  * 이걸 이용하면 크로스 오리진 리퀘스트 처리를 '전역적으로' 설정할 수 있음
* Bean을 정의 + 커스텀 설정을 통해 이 특정한 메서드를 오버라이드 하기
* 이것의 반환값이 WebMvcConfigurer인  corsConfigurer() 메서드 정의
* @Bean으로 등록
* WebMvcConfigurer 인터페이스의 익명 구현체를 생성하고 반환하면서 + CORS 설정을 정의
* ```void addCorsMapping(CorsRegistry registry)``` { //CorsRegistry 객체를 사용하여 CORS 설정을 정의함
* ```registry.addMapping("/")``` //모든 경로에 활성화하기
* ```.allowedMethods("*")``` //모든 HTTP  메서드 요청 받기 
* ```.allowedOrigins("http://localhost:3000");``` //http://localhost:3000에서 오는 요청에 대해서만 CORS 요청을 허용
  * localhost:3000은 개발 환경에서 일반적으로 사용되는 로컬 주소
  * 배포 환경에서는 애플리케이션에 접근하는 실제 도메인 또는 호스트 주소로 변경해야함
  * 예를 들어, 애플리케이션을 https://myapp.com에 배포한다고 가정할 때, CORS 설정은 다음과 같이 변경됨
    * ```.allowedOrigins("https://myapp.com")```
  * 여러 도메인에서의 접근을 허용해야 할 경우, .allowedOrigins() 메서드에 여러 주소를 나열할 수 있음
    * ```.allowedOrigins("https://myapp.com", "https://anotherdomain.com")```
* 추가사항
* 배포 환경에서는 보안을 위해 필요한 도메인만 허용해야함
* 모든 도메인에서의 접근을 허용하는 것(.allowedOrigins("*"))은 일반적으로 권장되지 않음!!!!
* HTTPS를 사용하여 애플리케이션을 배포하는 것이 바람직함(데이터 전송 중 보안을 강화하고, 현대의 웹 표준에 부합)
  * 때로는 동적으로 CORS 정책을 설정해야 할 필요가 있음


```java
import java.beans.BeanProperty;

@SpringBootApplication
public class RestfulWebServicesApplication {

    public static void main(String[] args) {
		SpringApplication.run(RestfulWebServicesApplication.class, args);
	}
    
    @Bean  //WebMvcConfigurer 타입의 객체를 반환하는 이 객체는 Spring MVC의 웹 설정을 커스터마이징하는데 사용됩니다.
    public WebMvcConfigurer corsConfigurer() {
        //WebMvcConfigurer 인터페이스의 익명 구현체를 생성하고 반환
        return new WebMvcConfigurer() {   //여기서 CORS 설정을 정의
            void addCorsMapping(CorsRegistry registry) { //CorsRegistry 객체를 사용하여 CORS 설정을 정의함
                registry.addMapping("/") //모든 경로에 활성화하기
                        .allowedMethods("*") //모든 HTTP  메서드 요청 받기 
                        .allowedOrigins("http://localhost:3000"); //http://localhost:3000에서 오는 요청에 대해서만 CORS 요청을 허용
                //주로 개발 환경에서 프론트엔드 서버와 백엔드 서버가 다른 포트를 사용할 때 필요
                //실제 배포에서는 수정해야하는 부분
            }
        };
    }
}
```




<br>

### API 호출 코드 모듈만 추출하기
* API 호출을 하는 모든 서비스를 API패키지로 모을 수 있음

<br>

### Spring Boot REST API에서 Axios를 사용하는 최적의 방식
* 기존에 알고있던 내용과 같아서 필기하지 않음

### ~리액트 이야기~
* 이후에 리액트 공부할 때 다시볼 것
* 라이브러리 Formil, Moment



<br>


####  Spring Security로 Spring Boot REST API 보호하기
* 만일 누군가가 틀린 사용자 이름이나 틀린 패스워드를 입력하면 토큰이 유효하지 않을 것이므로 인증 실패를 리턴하게 하기
* authorizeHttpRequests()에서 우린 특정한 것들에 대해 인증을 비활성화하기
  * antMatchers()이용
  * HttpMethod는 OPTIONS 
  * ```auth.antMatchers(HttpMethod는.OPTION, "/*").permitAll()```

<br>



#### 리액트 내용
* React 앱에 로그인할 때 기본 인증 서비스 호출하기
* Basic 토큰 만들기
  * 'Basic '이라고 하고 + (username+":"+password)
  * ase64 인코딩
  * window.btoa() 메서드 이용
  * 이렇게 기본 인증 토큰을 얻고, 기본 인증 토큰을 사용해서 executeBasicAuthenticationService를 호출할 수 있음
* async와 await를 사용하여 기본 인증 API 호출하기
  * 기본 인증 서비스가 실행될 때까지 기다렸다가 response를 리턴하기


<br>


####  AuthContext에 기본 인증 토큰 설정하기
* 모든 API 클라이언트에서 호출을 가로채고 인증 헤더를 설정하기
* 토큰을 헤더에 추가하기 (이것을 위해 인터셉터라는 걸 설정)
* 모든 요청에 대해 인터셉터를 생성하고 이 특수한 로직을 사용하기
  * config.headers.Authorization 설정
  * 기본 인증 토큰 baToken과 같다고 해주기
  * 그리고 config를 리턴하기


<br>


#### 표준 토큰 시스템 JWT 이용하기
* JWT는 Json Web Token의 약자
* 토큰을 만드는 표준 시스템을 정의
* 두 당사자들 간에 안전하게 클레임을 표시하기 위한 공개된 산업 표준
* JWT에는 사용자 세부정보와 인증도 담을 수 있음 
* **헤더**
  * 헤더의 타입은 JWT
  * 알고리즘
    * 해싱 알고리즘
    * 예시에 HS256
* **페이로드**
  * JWT의 일부로서 갖고 있길 원하는 속성
  * 기본적으로는 데이터를 말함
  * 표준 속성
    * iss는 JWT 토큰을 발행한 이슈어(issuer)
    * sub는 주제(subject)
    * aud는 대상(audience)이고 목표로 하는 대상
    * exp는 토큰이 언제 만료(expire)되는지
    * iat는 토큰이 발행된 시기, 토큰이 언제 있었는지, 언제 토큰이 생성되었는지 
  * 사용자 지정 속성도 추가할 수 있습니다
* **시그니처**
  * your-256-bit-secret을 추가하고 토큰이 유효한지 아닌지 확인할 수 있음 
* JWT의 흐름은 상당히 복잡함
* **적용방법**
  * 스프링 시큐리티 spring-boot-starter 의존성 추가
  * spring-boot-starter-oauth2-resource-server 사용
    * oauth2-resource-server
    * 애플리케이션의 일부로서 노출되어 있는 REST API 리소스가 있고요, JWT로 보호할 것
  * org.springframework.boot에 spring-boot-configuration-process 추가
  * ex) 만든 패키지인 com.in28minutes.rest.webservices .restfulwebservices.jwt가 
    * 반드시 RestfulWebServicesApplication 클래스의 서브 패키지에 있어야 함
  * 이전에 설정한 기본 보안 인증이 잡히지 않도록하기 
    * BasicAuthenticationSecurityConfiguration으로 가서 
    * 여기 있는 @Configuration을 빼주기!!
    * 이 BasicAuthenticationSecurityConfiguration 파일을 더 이상 로딩하지 않기
* 주의점
  * BasicAuthenticationSecurityConfiguration 파일을 더 이상 로딩하지 않기
  * jwt 코드가 컴포넌트 스캔에 의해 스캐닝되는 패키지 안에 있어야 한다는 점
* https://github.com/in28minutes/master-spring-and-spring-boot/blob/main/13-full-stack/99-reuse/02-spring-security-jwt.md
* 4:58
* 인증 헤더가 이상할 때 살펴볼 부분 2곳
  * 요청
    * ex) /authenticate 요청,  post를 사용, 그리고 username과 password가 담긴 json 구조물을 만들었음
    * 보내는 데이터가 예상한 대로인지 확인하기
    * Bearer 다음에 있는 공백은 아주 아주 중요
    * 페이로드 확인
    * 중요한 부분은 Request Headers
    * Authorization이라는 헤더 스펠링 확인
  * 인터셉터
    * 이미 인터셉터를 설정하고나서 요청의 일부로서 인증 토큰을 전송할 때 인터셉터가 문제일 수 있음
* 유효토큰 확인하기
  * jwt.io


<br>


#### 궁금증
* 토큰의 길고 복잡한 고유값은 무엇으로 만드는가?
  * 사용자 정보 + 페이로드값
  * 페이로드 값은 항상 달라지기 때문에 토큰 값이 변하는 것
  * 서명 과정에서 페이로드의 값이 변경되면, 생성되는 서명도 달라진다고 함
  * JWT의 페이로드 값은 해싱 과정에서 중요한 역할을 함
  * JWT의 서명 부분은 헤더, 페이로드, 그리고 서버의 비밀 키를 사용하여 생성함
* 서버의 비밀키??
  * 서버의 비밀 키를 지정하는 과정은 JWT를 생성하고 검증하는 데 중요한 부분
  * 위의 필기에서 언급된 'your-256-bit-secret'이 바로 서버의 비밀 키에 해당함
  * 이 비밀 키는 JWT의 서명을 생성하고 검증할 때 사용되며, 토큰의 보안을 보장하는 데 핵심적인 역할
  * 비밀 키 설정 방법
    * 암호화 알고리즘 선택
      * 우선 JWT 생성에 사용할 암호화 알고리즘을 선택해야 합니다. 
      * 예를 들어, HMAC SHA256 (HS256)을 사용할 수 있습니다.
    * 비밀 키 생성
      * 서명 생성에 사용될 비밀 키를 생성합니다. 
      * 이 키는 충분히 길고 복잡해야 하며, 안전하게 보관되어야 합니다. 
      * 예를 들어, 256비트의 무작위 문자열을 사용할 수 있습니다.
    * 애플리케이션에 키 적용: 
      * 생성된 비밀 키를 애플리케이션의 구성 파일(예: application.properties 또는 application.yml)에 저장하거나 환경 변수로 설정합니다.
      * ex)  yaml ```Copy code   jwt.secret=your-256-bit-secret```
    * JWT 라이브러리에 키 적용: 
      * JWT 라이브러리에 이 비밀 키를 제공하여 JWT를 생성하고 검증합니다. 
      * 예를 들어, Java에서 JWT 라이브러리를 사용하여 토큰을 생성하고 검증할 때, 이 비밀 키를 사용합니다.
  * 보안 고려사항
    * 비밀 키 보안: 
      * 비밀 키는 서버 측에서만 알고 있어야 하며, 외부에 노출되어서는 안 됩니다. 
      * 키가 노출되면, 토큰의 무결성이 보장되지 않습니 다.
    * 환경 변수 사용: 
      * 보안상의 이유로, 비밀 키는 코드에 직접 하드코딩되어서는 안 됩니다. 
      * 대신, 환경 변수나 외부 구성 파일을 통해 관리하는 것이 좋습니다.
    * 정기적 갱신: 
      * 보안을 강화하기 위해 정기적으로 비밀 키를 변경하는 것도 좋은 방법입니다.
























