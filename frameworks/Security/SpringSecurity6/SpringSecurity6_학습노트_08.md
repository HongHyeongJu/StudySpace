# 유데미 Spring Security 6 초보에서 마스터 되기 최신강의! (JWT , OAUTH2 포함)
## ch.08 - 커스텀 필터

<br>

## 결론은 그냥 filter 보다는 OncePerRequestFilter 사용을 권장함(이 노트 후반부부터 설명 시작)

<br>

### Spring Security 필터와 사용방법
* 인증 및 권한 부여 프로세스 중에 실행될 수 있는 커스텀 필터를 만들 수 있음
  * 커스텀 필터를 만들어야하는 이유
    * 복잡한 기업 응용 프로그램에서 종종 특정 상황이나 클라이언트 요구 사항에 따라 인증 및 권한 부여 흐름 중 일부를 하우스키핑 활동에 수행해야함
    * 하우스키핑 활동의 예
      * 인증 전에 몇가지 입력 위반을 수행하기 (요구에 추적, 감사 및 보고 세부 정보를 추가하기)
      * 인증 프로세스 중에 시스템에 들어가려고 하는 엔드 유저의 일부 셉 정보를 로그에 기록하려고 할 수 있음
      * 인증 또는 권한 부여 전후에 자체 논리를 작성해야 하는 경우
    * 이러한 다양한 시나리오에 대해 자체적으로 커스텀 필터를 작성해서 Spring Security 필터의 흐름에 삽입해야함
* 모든 요청 또는 응답을 가로채기를 원할때 가장 적절한 옵션은 HTTP 필터
* 필터는 Spring Secutiry 프레임워크에서도 활용되는 특수 유형의 서블릿
  * Spring Security 필터 체인을 살펴보면 인증을 시도하려고 할 때, 실행되는 필터의 수가 상당히 많음
  * 자체적인 역할과 책임을 갖는 10개 이상의 Spring Security 필터가 실행됨
  * 필터의 실행 방식은 연쇄적
  * 필터 체인은 마지막 필터에 도달할 때까지 다음 필터를 계속해서 작동시킴
  * 내장된 필터는 Spring Security를 어떻게 구성하는지/어떻게 인증을 시도하는지에 따라 다양함

<br>

### Spring Security 프레임워크의 내장 필터
* Spring Security 프레임워크의 내장 필터 사용하기 위한 2가지 준비 (주의! 실제 웹 애플리케이션에 적용시 보안 위험 있음)(배움을 위한 것)
  * 메인 클래스에 @EnableWebSecurity(debug = true)  애터네이션 추가하기
  * application.properties 파일 내에서 디버그 로깅을 가능하게 하는  FilterChainProxy 클래스를 활성화 시키기
    * FilterChainProxy : Spring Security 프레임워크 내부에서 내장 필터를 연결하는 데 사용되는 로직을 가지고 있음
      * 안에 내부 클래스 존재함
      * private static final class VirtualFilterChain implements FilterChain {...}
        * doFilter(ServletRequest, ServletResponse ) : Spring Security 필터체인 내에서 사용 가능한 모든 필터를 반복하는 로직 보유
          * currentPosition 을 추적할 수 있음
          * currentPosition == this.size 이면 다음 필터는 호출되지 않음 
```
		@Override
		public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
			if (this.currentPosition == this.size) {
				this.originalChain.doFilter(request, response);
				return;
			}
			this.currentPosition++;
			Filter nextFilter = this.additionalFilters.get(this.currentPosition - 1);
			if (logger.isTraceEnabled()) {
				String name = nextFilter.getClass().getSimpleName();
				logger.trace(LogMessage.format("Invoking %s (%d/%d)", name, this.currentPosition, this.size));
			}
			nextFilter.doFilter(request, response, this);
		}
```

<br>


### 사용자 정의 필터 생성하기
* 커스텀 필터 작성 방법
  * jakarta.servlet 패키지 내에 있는 Filter 인터페이스를 구현하면 됨
  * doFilter를 오버라이딩 해서 메서드 내에서 실행하고 싶은 모든 로직을 작성하기
  * doFilter 메서드
    * 매개변수 : ServletRequest, ServletResponse, FilterChain 3가지
      * ServletRequest : 엔드유저로부터 오는 HTTP input 요청
      * ServletResponse : 엔드 유저나 클라이언트에게 다시 보낼 HTTP 응답
      * FilterChain : 정의된 순서대로 실행되는 피터들의 집합
* 어떻게 내 커스텀 필터를 Spring Security FilterChain에 주입할 수 있을까?
  * Spring Security 프레임워크 내에서 중요한 세 가지 메소드 이용하기
  * addFilterBefore(filter, class), addFilterAfter(filter, class), addFilterAt(filter, class) 
    * 매개변수 2가지
    * filter : 나만의 커스텀 필터 클래스의 객체
    * class : Spring Boot 내부 빌트인 필터의 이름
* public interface Filter {...}
  * init 메서드
  * doFilter 메서드 : 주 메서드. 커스텀 필터 정의 시 오버라이드 하고 비즈니스 로직을 구현해야 하는 메서드
  * destroy 메서드

<br>


### 초반 필터의 흐름
* CorsFilter 필터 실행
* CsrfFilter 필터 실행
* (여기!!) : 요구 사항이 인증 전에 일부 로직을 실행할 꺼라면 이 위치에서 커스텀 필터가 실행되어야함
* BasicAuthenticationFilter 필터 실행

<br>

### addFilterBefore() 메서드 사용하기
* 강의 예시 로직 : 로그인 ID에 test라는 글자가 있으면 안됨
* filter 패키기에 public class RequestValidationBeforeFilter  implements Filter {...} 생성
  * @Override  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 하기
```
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
            
        //매개변수로 받은 ServletRequest, ServletResponse 를 형변환
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        //애플리케이션의 요청으로 부터 Authorization header 얻기
        String header = req.getHeader(AUTHORIZATION);
        
        if (header != null) {
            header = header.trim();
            if (StringUtils.startsWithIgnoreCase(header, AUTHENTICATION_SCHEME_BASIC)) {
            
                //Basic 인증 시 'Basic ' 를 제거하기 위함. 공백포함이므로 6글자
                byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8); 
                
                //디코딩 하기 
                byte[] decoded;
                
                try {
                    decoded = Base64.getDecoder().decode(base64Token);
                    String token = new String(decoded, credentialsCharset);
                    
                    //구분자 값인 콜론으로 문자열 분리하기
                    int delim = token.indexOf(":");
                    if (delim == -1) {
                        throw new BadCredentialsException("Invalid basic authentication token");
                    }
                    String email = token.substring(0, delim);
                    
                    //강의에서 요구하는 조건
                    if (email.toLowerCase().contains("test")) {
                        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);  //400에러
                        return;
                    }
                } catch (IllegalArgumentException e) {
                    throw new BadCredentialsException("Failed to decode basic authentication token");
                }
            }
        }
        chain.doFilter(request, response);
    }
```
* substring을 사용하고 Authentication header 내에서 가장 첫 두 값을 추출하려고 시도 -> username
  * 강의에서 설정한 로직대로 인증
* 주의점
  * 어떤 커스텀 필터든 시간이 오래 걸리는 로직은 사용하지 않기
  * 비즈니스 또는 클라이언트에서 필수로 지정한 로직만 작성하도록 꼭 신중하게 고려하기!!
* ProjectSecurityConfig class 의 defaultSecurityFilterChain 수정
  * .addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class) 추가

<br>


### addFilterAfter() 메서드 사용하기
* 강의 예시 로직 : 어떠한 유저 인증이 성공적인 경우 "A가 인증 성공, 이러한 권한 가짐" 이라는 로그 찍기
* Spring Security in-built filter 바로 다음에 새로운 필터를 구성하려고 하면 addFilterAt 메소드를 사용하기
* public class AuthoritiesLoggingAfterFilter implements Filter{...}  생성
```
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        //현재 인증된 유저의 정보 얻기 (인증된 유저의 정보는 Security Context에 저장되므로)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        //인증객체가 null 이 아니면 인증이 성공했다는 것
        if (null != authentication) {
            LOG.info("User " + authentication.getName() + " is successfully authenticated and "
                    + "has the authorities " + authentication.getAuthorities().toString());
        }
        chain.doFilter(request, response);
    }
```
* ProjectSecurityConfig class 의 defaultSecurityFilterChain 수정
  * .addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class) 추가
* 내용

<br>


### addFilterAt() 메서드 사용하기
* addFilterAt()메서드를 이용하면 Spring Security 중 한 가지 필터 위치와 정확히 같은 위치에 커스텀 필터를 구성할 수 있음
* 주의점!!
  * 같은 위치에 여러개의 필터를 구성할 경우 내부적으로 Spring Security는 그 중 하나를 무작위로 실행함
  * 비즈니스 로직에 부작용이 없도록 로직을 작성할 것.
* 대부분의 애플리케이션에서는 addFilterBefore(), addFilterAfter()를 사용함
* addFilterAt() 를 사용하는 경우
  * 인증 프로세스 중에 사용자에게 인증이 진행 중임을 알리기 위해 이메일을 보내거나
  * 인증이 성공적이라고 엔드 유저에게 알리거나
  * 내부 응용 프로그램에 알림을 보내는 등
* 강의 예시 로직 : 이 필터 내에서는 인증이 진행 중이라는 새로운 문장 로그에 남기기
```
public class AuthoritiesLoggingAtFilter implements Filter {

    private final Logger LOG =
            Logger.getLogger(AuthoritiesLoggingAtFilter.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        LOG.info("Authentication Validation is in progress");
        chain.doFilter(request, response);
    }

}
```
```
.addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class)
```

<br>


### GenericFilterBean 과 OncePerRequestFilter 설명
* 필터 인터페이스 외에도 Spring 프레임워크를 사용할 때 커스텀 필터를 만들 수 있는  다른 고급 옵션이 있음
  * GenericFilterBean 클래스
    * 추상 bean 클래스 ```public abstract class GenericFilterBean implements Filter, BeanNameAware, EnvironmentAware,
		EnvironmentCapable, ServletContextAware, InitializingBean, DisposableBean {...}```
    * 장점 : (클래스의 주석을 읽으면 알 수 있음) 
      * 이 클래스는 필터 인터페이스의 간단한 기본적인 구현이며, 모든 유형의 필터에 대해 편리하고 좋은 클래스
      * web.xml이나 배포 설명자 내에서 구성한 모든 설정 매개변수, 초기 매개변수 및 서블릿 컨텍스트 매개변수의 세부 정보를 제공함  
      * 커스텀 필터 내에서 구성 매매변수, 초기 매개변수, 서블릿 컨텍스트에 접근해야하는 시나리오가 있을 때 활용할 수 있음
    * 메서드 : 환경 세부 정보, 필터 구성 세부 정보, 서블릿 컨텍스트 세부 정보 및 초기 매개변수를 제공하는 여러가지의 메소드가 존재함
    * 덕분에 매개변수를 읽기 위한 비즈니스 로직을 모두 작성할 필요가 없음
  * OncePerRequestFilter
    * ```public abstract class OncePerRequestFilter extends GenericFilterBean {...}```
    * 필터가 각 요청에 대해서 반드시 한 번만 실행되도록 보장한다는 것을 의미함
      * 이것은 클래스의 doFilter 메서드 내부에 필터 실행여부 판단을 위한 로직이 있음
      * 특정 시나리어에서 커스텀 필터를 호출하지 않고 진행할 때 필터를 건너뛰고 한 번만 실행되도록 하는 로직도 있음
* OncePerRequestFilter 시나리오에서는 비즈니스 로직을 doFilterInternal 추상메서드를 오버라이드 하여 적어야함
* 모든 비즈니스 로직은 doFilterInternal 메서드에 적기
* 또다른 유용한 메서드 shouldNotFilter
  * web API나 일부 REST API 경로에 대해 이 필터를 실행하고 싶지 않을 때
  * 이 메서드에 해당 세부 정보를 정의하고 조건에 따라 true를 반환하게 하기
  * return false가 기본동작이므로
* OncePerRequestFilter를 활용하는 Spring Security의 필터
  * BasicAuthenticationFilter
  * ```public class BasicAuthenticationFilter extends OncePerRequestFilter {...}```

<br>


















