# 유데미 Spring Security 6 초보에서 마스터 되기 최신강의! (JWT , OAUTH2 포함)
## ch.07 - 권한부여/인가 

<br>

### Authentication (인증)  VS  Authorization (권한부여/인가)
#### 첫번째 차이점
* 인증
  * 웹 애플리케이션에 접근하는 유저를 식별하는 것
  * 안전한 API에 접근하도록
  * 내용
* 인가
  * 인증이 성공적으로 완료된 이후에 등장함
  * 권한부여의 일환 : 엔드유저가 어떠한 권한, 역활을 가지고 있는지 확인하는 것
  * 사용자가 자신의 권한에 기반하여서 웹 애플리케이션 안의 다양한 시나리오나 기능에 접근할 수 있음(비즈니스 로직)
* 권한부여
  * 사용자의 권한에 따라 시스템 내에서 엔드 유저의 접근을 강제하는 것
#### 두번째 차이점
* 인증
  * 항상 권한부여보다 먼저 등장함
  * 약어로 AuthN
* 인가
  * 항상 인증단계 이후에 실행됨
  * 약어로 AuthZ
* 언제나 엔드 유저에게 시스템 로그인을 묻고, 인증완료 후에 그의 권한과 접근범위 및 역할에 대해 고려함(권한 부여를 강제함)
#### 세번째 차이점
* 인증
  * 오직 유저 로그인 세부정보만 물어봄(사용자 자격증명)
* 인가
  * 오직 유저의 권한이나 특권, 역할에 대해서만 신경씀
  * 이러한 권한, 역할, 특권 만을 기준으로 웹 애플리케이션에서 그 유저의 접근 레벨을 결점함
#### 네번째 차이점
* 인증
  * 인증이 실패할 때마다 에러코드 401
* 인가
  * 권한 부여의 상황에서 에러코드는 403
  * 인증은 성공적이나, 해당 유저는 접근할 권한이 없다는 것 의미
  * 기능적인 접근 금지 에러 403


<br>

### Spring Security 6의 권한 보관 방법
* Spring Security 에서는 권한과 특권을 2가지 용어로 부름
  * 권한 : 이라는 용어 자체
  * 역할
* 권한과 역할 둘 다  Spring Security 프레임워크 내에서 저장되는 방식은 비슷함
* GranteAuthority 라는 인터페이스가 존재함
  * Spring Security가 제공하는 구현체는 SimpleGrantedAuthority 클래스
  * 특정 유저에게 권한 혹은 역할을 배정하고 싶을 때 SimpleGrantedAuthority 생성자를 문자열 형식으로 역할의 이름과 함께 불러오면됨
```
	public SimpleGrantedAuthority(String role) {
		Assert.hasText(role, "A granted authority textual representation is required");
		this.role = role;
	}
```
* setter메서드가 없기 때문에 그 누구도 정보를 변경할 수 없음
* 이러한 권한들은 어디에 저장되는 것일까?
  * UsernamePasswordAuthenticationToken객체를 생성할 때 인터페이스 내부에 사용가능한 getAuthority 메서드를 호출하는 것
* 실제로 앞의 강의에서 작성한 커스텀 인증제공자 ```@Component
public class EazyBankUsernamePwdAuthenticationProvider implements AuthenticationProvider ``` 
  * 의 authenticate 메서드를 살펴보면
  * ```authorities.add(new SimpleGrantedAuthority(customer.get(0).getRole()));```   
  * SimpleGrantedAuthority 클래스의 객체를 생성하고, 문자열형식으로 가져온 역할 정보를 전달함
  * 그리고 생성자의 내부에서 권한이 저장됨
  * ```return new UsernamePasswordAuthenticationToken(username, pwd, authorities); ```
    * UsernamePasswordAuthenticationToken 생성자 메서드 구현부 중 ```super(authorities);``` 부분
      * UsernamePasswordAuthenticationToken의 부모 클래스인 AbstractAuthenticationToken 클래스를 보면 권한을 수정되지 않는 리스트로 저장됨
      * 권한이 엔드유저에게 배정되면 아무도 수정할 수 없는 것
      * 아래의 코드 참조 (AbstractAuthenticationToken 클래스의 생성자)
```
	public AbstractAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
		if (authorities == null) {
			this.authorities = AuthorityUtils.NO_AUTHORITIES;
			return;
		}
		for (GrantedAuthority a : authorities) {
			Assert.notNull(a, "Authorities collection cannot contain any null elements");
		}
		this.authorities = Collections.unmodifiableList(new ArrayList<>(authorities));  //수정불가!
	}

```
* 그리고 언제든 프레임워크가 이 getAuthority() 메서드를 호출할 때 엔드 유저의 권한 또는 역할 정보가 무엇인지 파악할 수 있음
  * getRoles() 같은 다른 메서드는 존재X

<br>


### 권한 테이블을 설정해서 역할과 권한 저장하기
* Spring Security는 우리에게 유연성을 제공하여서, 각 유저에 대해 제한되지 않은 수의 권한 또는 역할을 부여할 수 있음
  * 하나의 권한, 역할만 저장할 수 있는 것이 아님
  * 이러한 유연성/기능을 활용하기 위해서는 Authorities 라는 테이블을 새로 형성해서 그 안에 유저가 가질 수 있는 권한의 종류를 정의내릴 수 있음

<br>


### 권한을 불러오기 위한 백엔드 작업
#### 엔티티 생성 및 수정
* Authority 엔티티 생성하기
  * private Long id;
  * private String name;
  * private Customer customer; //@ManyToOne  //@JoinColumn(name = "customer_id")
* Customer 엔티티 수정
  * private Set<Authority> authorities; 추가  //@JsonIgnore   //@OneToMany(mappedBy="customer",fetch=FetchType.EAGER)
* Jackson 라이브러리를 사용하여 Java 객체를 JSON으로 직렬화할 때 특정 필드의 처리 방식에 대한 애너테이션
```
//권한은 JSON 전달시 제외되도록
    @JsonIgnore
    @OneToMany(mappedBy="customer",fetch=FetchType.EAGER)
    private Set<Authority> authorities;
    
//비밀번호
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String pwd;
```
* @JsonIgnore
  * 직렬화와 역직렬화 모두에서 필드를 무시
  JSON으로 변환할 때 해당 필드는 포함되지 않으며, JSON에서 객체로 변환할 때도 해당 필드는 무시됨
* @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  * 직렬화할 때 필드를 무시하지만, 역직렬화할 때는 필드를 사용할 수 있게함
  * 서버로 데이터를 받을 때는 해당 필드를 사용할 수 있지만, 클라이언트로 데이터를 보낼 때는 해당 필드가 포함되지 않
* 물론 위의 내용은 해당 엔티티를 UI와의 전송 상황에 직접 사용할 때 필요한 보안 정책이고,
  * 화면에 맞춰 DTO를 이용한다면 생략할 수 있음
#### 커스텀 인증제공자 코드 수정
* 권한 엔터티 클래스로부터 실제 권한의 열 이름을 읽어오는 getGrantedAuthorities 메서드 생성하기
  * 매개변수 : 권한 엔티티 
  * 반환값은 List<GrantedAuthority>
```
    private List<GrantedAuthority> getGrantedAuthorities(Set<Authority> authorities) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Authority authority : authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
        }
        return grantedAuthorities;
    }
```
* authenticate 메서드에서 기존에 UsernamePasswordAuthenticationToken를 생성할 때 
  * 3번째 매개변수로 전달하던 리스트를  새 메서드 반환 값으로 바꿔주기
* 기존코드
```
List<GrantedAuthority> authorities = new ArrayList<>();
authorities.add(new SimpleGrantedAuthority(customer.get(0).getRole()));
return new UsernamePasswordAuthenticationToken(username, pwd, authorities);
```
* 변경코드
```
return new UsernamePasswordAuthenticationToken(username, pwd, getGrantedAuthorities(customer.get(0).getAuthorities()));
```

* 최종 수정본
```java
@Component
public class EazyBankUsernamePwdAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        List<Customer> customer = customerRepository.findByEmail(username);
        if (customer.size() > 0) {
            if (passwordEncoder.matches(pwd, customer.get(0).getPwd())) {
                return new UsernamePasswordAuthenticationToken(username, pwd, getGrantedAuthorities(customer.get(0).getAuthorities()));
            } else {
                throw new BadCredentialsException("Invalid password!");
            }
        }else {
            throw new BadCredentialsException("No user registered with this details!");
        }
    }

    private List<GrantedAuthority> getGrantedAuthorities(Set<Authority> authorities) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Authority authority : authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
        }
        return grantedAuthorities;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

}
```



<br>


### Spring Security 로 권한 설정하기 (이론)
* Spring Security 프레임워크 내에서 사용할 수 있는 권한 부여 메서드 (defaultSecurityFilterChain 메서드 내에서 사용)
  * hasAuthority() : 해당 권한을 가진 사용자만 특정 엔드포인트에 접근 가능
  * hasAnyAuthority()  : 해당 권한을 가진 사용자만 특정 REST API 서비스나 혹은 API path에 접근 가능
    * 엔드 유저의 나열된 권한 중 해당하는 것을 하나라도 가지고 있다면 해당 기능 또는 특정 REST API에 액세스할 수 있음
  * access() : Spring Expression Language(SpEL)의 도움을 받아 복잡한 권한 부여의 규칙을(권한과 조건) 정의하고 권한부여를 실행할 수 있음
* defaultSecurityFilterChain 메서드에 적용된 예시(구현부 일부)
```
.requestMatchers("/myAccount").hasAuthority("VIEWACCOUNT")
.requestMatchers("/myBalance").hasAnyAuthority("VIEWACCOUNT","VIEWBALANCE")
.requestMatchers("/myLoans").hasAuthority("VIEWLOANS")
.requestMatchers("/myCards").hasAuthority("VIEWCARDS")*/
```
* 그러나 이후에 권한이 아닌 역할기반 접근으로 변경되니까 참고만~~

<br>


### Spring Security 에서  권한 VS 역할
* 권한
  * 사용자가 가질 수 있는 개별 특권이나, 앱 내에서 엔드 유저가 수행할 수 있는 개별 작업 나타냄
  * ex) VIEWACCOUNT
  * 세밀한 방식으로 액세스를 제한하는 것에 사용할 수 있음
* 역할
  * 여러 권한의 그룹을 나타냄(권한 묶음)
* 프로젝트마다 권한 / 액션 등 프로젝트 내에서 어떤 용어를 사용하는 것과 상관 없이
* Spring Security에서는 권한 부여를 강제하기 위해 반드시 권한 또는 역할 중에 선택을 해야함
  * 그러나 특정 문자열이 권한인지 역할인지를 구별하기 위해서는 Spring Security 프레임워크 내에서 표준이 강제되어 있음!
  * 이 표준은 특정 문자열이 웹 애플리케이션 내에서 권한을 나타내는지 역할을 나타내는지 구별하기 위해 저장 시스템 내에서 항상 ROLE_ 접두사를 붙여야함!
* 코드 수정
  * 유저의 권한 부분을 삭제하고 
  * ROLE_ADMIN과 ROLE_USER와 같은 몇 가지의 역할을 생성하기


<br>

### Spring Security 로 역할권한 설정하기 (이론)
* 역할을 이용해서 권한 부여를 강제하는 메서드
  * hasRole() : 해당 역할을 가진 최종 사용자만 접근 가능
  * hasAntRole() : 특정 API경로가 주어진 역할 목록 중 해당하면 접근할 수 있음
  * access() : 역할과 SpEL를 사용해서 권한 부여 규칙을 장게할 수 있는 동일한 메서드. OR NOT같은 조건 가능함
* 데이터베이스 내의 모든 역할 데이터는 ROLE_ 접두사가 필수
  * 그러나 메서드에서는 접두사를 언급하면 안됨
  * Spring Security 프레임워크가 내부적으로 hasRole(), hasAnyRole() 메서드를 호출할 때 해당 접두사를 추사하기 때문에
* 메서드 호출방법 코드
  * 앞에서 defaultSecurityFilterChain 메서드에 적용된 예시(구현부 일부)를 주석처리하고 새로 작성
```
.requestMatchers("/myAccount").hasRole("USER")
.requestMatchers("/myBalance").hasAnyRole("USER","ADMIN")
.requestMatchers("/myLoans").hasRole("USER")
.requestMatchers("/myCards").hasRole("USER")
.requestMatchers("/user").authenticated()
.requestMatchers("/notices","/contact","/register").permitAll())
```












