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


### 애플리케이션 내에서 AuthenticationProvider 커스터마이징 하기
* config 패키지에 개인 Provider 클래스 생성하기 ex) ProjectUsernamePasswordAuthenticationProvider
* ```public class ProjectUsernamePasswordAuthenticationProvider```
  * implements AuthenticationProvider
  * 오버라이드 할 메서드 : authenticate(), supports()
  * supports() 구현
    * DaoAuthenticationProvide에서 응용할 코드 복사해오기 ```return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));```
  * authenticate() : 저장시스템 -> 유저세부사항 로딩, 비밀번호 비교 로직 작성하기
    * 그러기 위해서 ProjectUsernamePasswordAuthenticationProvider 클래스에 유저Repository와 PasswordEncoder를 Autowired해오기
  * Spring Security 프레임 워크에 감지될 수 있도록 @Component 붙여주기
```java
@Component
public class EazyBankUsernamePwdAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();   //로그인 정보
        String pwd = authentication.getCredentials().toString();   //로그인 정보
        List<Customer> customer = customerRepository.findByEmail(username);   //데이터베이스로부터 불러오기
        if (customer.size() > 0) {
            if (passwordEncoder.matches(pwd, customer.get(0).getPwd())) {  //비교하기
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority(customer.get(0).getRole()));   
                //엔드 유저의 authorities 세부 사항 덧붙여주기, //Role 문자열 값을 SimpleGrantedAuthority 클래스로 변환하기
                return new UsernamePasswordAuthenticationToken(username, pwd, authorities); 
            } else {
                throw new BadCredentialsException("Invalid password!");
            }
        }else {
            throw new BadCredentialsException("No user registered with this details!");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

}
```
* 덧붙여 유저의 연령 세부 정보, 국가 세부 정보를 확인하고, 그에 기반하여 인증을 허용하거나 거부하려는 경우에도 authenticate 메서드 내에 원하는 로직을 작성하면 됨
* UsernamePasswordAuthenticationToken 생성자
  * 유저 이름, credentials 그리고 authorities를 authentication 객체에 설정하고 있음
  * 동시에 authentication이 성공적이라고 명시함
```	
public UsernamePasswordAuthenticationToken(Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
		this.credentials = credentials;
		super.setAuthenticated(true); // must use super, as we override
	}
```

<br>


### 인증과정 테스트 일부
* ProviderManager 클래스의 내부 
* @Override public Authentication authenticate(Authentication authentication) 메서드
  * 구현과정에서 if (result != null) 인증 성공 후에는 필요없는 인증번호(비밀번호)를 제거함
    * if (this.eraseCredentialsAfterAuthentication && (result instanceof CredentialsContainer)) {((CredentialsContainer) result).eraseCredentials();}
    * 보안 문제 우려가 사라짐
  * 인증 성공 후에는 인증이 성공정으로 발생했다고 이벤트 게제함
    * if (parentResult == null) { this.eventPublisher.publishAuthenticationSuccess(result); }


<br>



### Spring Security 순서 흐름과 커스텀 AuthenticationProvider
* 사용자가 로그인 양식에 자신의 자격 증명(보통은 사용자 이름과 비밀번호)을 입력한다.
* AuthenticationManager (구현체는 ProviderManager)는 등록된 AuthenticationProvider들 중 적절한 것을 찾아 인증 과정을 수행하도록 요청한다.
* ProviderManager는 AuthenticationProvider의 authenticate 메소드에 인증을 위한 데이터(Authentication 객체)를 넘겨준다.
* 이 과정에서, 구현한 로직에 따라 사용자 정보를 조회하고, PasswordEncoder를 사용하여 제출된 비밀번호가 저장된 비밀번호와 일치하는지 검증한다.
* 이 비즈니스 로직에 완벽하게 만족한다면(인증 과정이 성공적으로 완료되면,) AuthenticationProvider는 완성된 Authentication 객체를 ProviderManager에 반환한다. 
  * 이 객체는 이제 인증된 사용자의 정보를 담고 있음
* 중요한 점
  *  더 이상 UserDetailsService, JWC UserDetailsManager, 사용자 상세 서비스, UserDetailsManager의 구현 클래스를 사용하지 않음
  * 왜냐하면 유저 상세 정보 검색 로직도 authentication 메소드 자체에 작성했기 때문에















