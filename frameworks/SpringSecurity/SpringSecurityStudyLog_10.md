### OAuth
* OAuth는 승인에 사용되는 업계 표준 프로토콜
* 이제 인증도 지원함

<br>

#### OAuth에 관련된 중요한 개념
* 리소스 소유자
  * google drive를 소유한 나
* 클라이언트 어플리케이션
  * Todo 관리 어플리케이션
  * 우리가 갖고 있는 파일들에 액세스하려는 애플리케이션
* 리소스 서버
  * 리소스가 있는 장소
  * 우리가 액세스하려는 리소스는 Google Drive 파일
* 승인 서버
  * Google OAuth 서버입니다
  * 클라이언트를 인증하고 필요한 토큰을 생성


<br>

**[ 새로운 프로젝트 생성 ]**


<br>

```
[강의에 필요한 리소스]

##Google API 콘솔
##http://localhost:8080/login/oauth2/code/google
 
spring.security.oauth2.client.registration.google.client-id=YOUR_CLIENT_ID
spring.security.oauth2.client.registration.google.client-secret=YOUR_SECRET
```

<br>

* Spring Security의 기본 설정은 이 SpringBootWebSecurityConfiguration 파일에 있음
* 해당 파일에서 defaultSecurityFilterChain 코드를 복사하고 (설정에 있는 보안 필터)
* 새로만든 @Configuration
public class OauthSecurityConfiguration 클래스에 복사 붙여넣기 후 수정
```java
@Configuration
public class OauthSecurityConfiguration {
		@Bean
		@Order(SecurityProperties.BASIC_AUTH_ORDER)
        SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
			http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
//			http.formLogin(withDefaults());
//			http.httpBasic(withDefaults());
            http.oauth2Login(Customizer.withDefaults());
			return http.build();
		}
}
```
* 오류 메세지 확인할 수 있음 : ClientRegistrationRepository 타입의 Bean을 정의하는 걸 고려하라는 뜻

<br>

* 선행 되어야 할 것 : 먼저 우리 애플리케이션을 위한 자격증명을 생성하기
* Google API console을 검색 -> Google Developer Console나옴
* 자격 증명 생성하기 **296강**
* 그럼 다음 화면에 이렇게 OAuth 클라이언트 ID를 생성 -> 동의 화면을 설정
* 동의 화면을 설정
  * 외부 사용자 External을 선택하고 Create
  * 개발자 연락처 정보를 설정 할 수도 있고, 
  * SAVE AND CONTINUE
* 스코프 설정
  * 스코프는 앱에 사용자를 승인하기 위해 사용자에게 요구하는 권한
  * 관리자에게 필요한 사용자 세부정보를 의미
  * ADD OR REMOVE SCOPES
  * 스크롤 +  UPDATE
* 테스트 사용자
* 요약문

<br>

* +CREATE CREDENTIALS를 클릭 OAuth client ID
* 제안된 기본 이름 사용
* 여기서 꼭 추가해줘야 하는 게 바로 승인된 리디렉션 URI
* 로컬이라면 http://localhost:8080/login/oauth2/code/google

<br>
* 생성하고 나면  클라이언트 ID가 있고 클라이언트 비밀번호 확인할 수 있음


<br>

### application.properties 설정
```
spring.security.oauth2.client.registration.google.client-id=YOUR_CLIENT_ID
spring.security.oauth2.client.registration.google.client-secret=YOUR_SECRET
```

<br>



* Spring Security가 인증을 하고 인증이 성공적이라면 모든 세부정보가 Authentication 객체에 저장됨
* 이것을 요청 메서드에 자동 연결할 수 있음
* GetMapping 컨트롤러에 매개변수로 받을 수 있다는 뜻(Authentication authentication )
* Authentication 객체에서 얻을 수 있는 정보
  * 구글 내부 이름 [102938237812732]
  * 부여된 권한(ex, 이메일/프로필 정보)
  * authentication.getPrincipal() : 더 자세한 정보 (이름, 도메인, 성, 토큰생성 시간, 이메일)









