# 유데미 Spring Security 6 초보에서 마스터 되기 최신강의! (JWT , OAUTH2 포함)
## ch.03 - 유저 정의와 관리

<br>

### 이번 단원 설명
* 애플리케이션 메모리 안에 다수의 유저 만들기
* 유저 자격증명을 만들고 DB 안에 저장하기
* DB의 도움을 받아서 인증 진행하기
* 주의점
  * 강의에서 자격증명을 애플리케이션 메모리에 저장하는 것은 하위환경에서만 가능
  * 운영 시에는 엔드 유저의 자격증명을 데이터베이스에 저장/저장장치 안에 저장/Keyclock/Okta 인증거비스를 이용한 프레임워크 이용

<br>

### InMemoryUserDetailsManager 를 사용한 유저 설정 접근방식
* 강의에서 보안URL, 허용URL 차단을 확인하기 위한 인메모리 방식의 유저 만들기 설명으로 실제 운영에 사용하지 않는 방법이기 때문에 필기는 하지 않음
* 대신 중요한 것만 필기하기.
* 강의에서는 비밀번호 암호화 없이 일반 텍스트로 비교하는 권장하지 않는 NoOpPasswordEncoder()를 사용함.
* 보통은 아래와 같이 BCryptPasswordEncoder()를 사용함
```java
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
```

<br>


### 유저 관리 인터페이스와 클래스 들에 대한 이해
* 기본적으로 Spring Security에서는 DaoAuthenticationProvider라는 이름의 AuthenticationProvider가 제공됨
* 이 AuthenticationProvider는 Spring Security 프레임워크 내에서 사용 가능한 UserDetailsManager 및 UserDetailsService 목록에서 도움을 받음
* UserDetailsService
  * loadUserByUsername라는 추상 메소드가 있음
  * 브라우저에 입력한 엔드 유저의 세부 정보를 로드하는 것
  * 유저 이름만 로드하는 이유(=비밀번호는 로드 안하는 이유) : 
    * 비밀번호를 불필요하게 네트워크 전송해서는 안됨, 
    * 비밀번호를 검증하는 일은 Spring Security의 AuthenticationProvider 구현체 중 하나인 DaoAuthenticationProvider에 의해 수행되기 때문에
* UserDetailsManager
  * 유저의 세부 정보를 관리하는데 도움됨
  * 유저 생성, 유저 업데이트, 유저 삭제, 비밀번호 변경, 유저 존재 여부와 같은 메서드 포함함
  * 그리고 물론 UserDetails 인터페이스로부터 loadUserByUsername 메소드를 상속받음
* Spring Security 팀은 단순히 인터페이스를 정의하는 것 뿐만 아니라 UserDetailsManager의 일부 샘플 구현을 제공함
  * InMemoryUserDetailsManager
  * JdbcUserDetailsManager
    * 이것을 토대로 데이터베이스에서 유저 세부 정보를 검색할 수 있음
  * LdapUserDetailsManager를
    * 비슷하게 유저 세부 정보를 저장하기 위해 Ldap 서버를 사용하는 경우 사용함
    * Ldap 서버 : 디렉터리 서비스를 제공하는 네트워크 프로토콜. 중앙 집중식 사용자 관리, 인증 및 권한 부여, 경량 프로토콜, 유연한 검색 기능 이 특징
* 자체 인증로직 있거나 모든 것을 직접 작성 시 자체 AuthenticationProvider를 정의할 수 있음

<br>


### UserDetails 인터페이스와 User 클래스 심층 분석
* UserDetails 인터페이스
  * 저장하고 저장시스템에서 로드하려는 엔드 유저의 세부 정보를 나타내는 인터페이스
  * Spring Security 팀은 구현체인 User클래스를 제공함
* 따라서 엔드 유저의 이름, 비밀번호, 권한 과 같은 세부정보가 UserDetails 구현클래스의 객체 내에 저장될 수 있음
* UserDetails 인터페이스와 구현클래스는 InMemoryUserDetailsManager/JdbcUserDetailsManager/UserDetailsService와 같은 모든 상위 클래스와 인터페이스 내에서 사용됨
* UserDetails 인터페이스
  * getAuthorities 메서드 : 엔드 유저의 권한 똑은 역할 목록을 보유하고 있어서 이를 이용한 권한부여, 역할 기반 액세스 매커니즘을 구현할 수 있음
  * getPassword, getUsername 메서드 : 엔드 유저 비밀번호와 유저 이름을 반환함
  * 그 외의 메서드 : 유저 계정 만료여부, 유저 계정 잠금 여부, 여저 자격 증명 만료 여부, 유저 계정 활성/비활성 여부 확인함
* UserDetails와 User 클래스내에는 getter 메서드만 있고, setter 메서드는 없음 : 읽기만 가능, 재지정 불가능(보안을 위해서)
#### 인증객체 VS UserDetails인터페이스와 User클래스(07:00)
* 로그인 유저 세부 정보를 저장하는 두 가지 별개의 방법이 있는 이유
* 1. 인증 객체(Authentication)
  * 스프링 시큐리티의 인증 프로세스에서 중심적인 역할
  * 인증된 사용자의 정보(주로 Principal과 권한(Authorities))를 포함
  * 시스템 내에서 사용자의 인증 상태를 나타냄
  * Authentication 객체는 인증 과정에서 생성됨
  * 인증이 완료된 후에는 SecurityContext에 저장되어 애플리케이션 전역에서 접근할 수 있음
* 2. UserDetails 인터페이스와 User 클래스
  * 사용자의 세부 정보를 로딩하는 방법을 정의함
  * serDetails 인터페이스는 스프링 시큐리티가 사용자 정보를 다루는 데 필요한 정보(사용자 이름, 비밀번호, 활성화 상태, 권한 등)를 제공하는 메서드를 정의
  * 구현체 중 하나인 User 클래스는 이 인터페이스를 구현하여 스프링 시큐리티가 사용할 수 있는 사용자 정보의 기본적인 형태를 제공
* 두 가지 방법은 서로 보완적인 관계임
  * UserDetails와 UserDetailsService는 주로 사용자 정보를 데이터베이스나 다른 저장소에서 조회하여 인증 프로세스에 제공하는 데 사용됨
  *  인증 프로세스가 성공하면, 조회된 UserDetails 정보를 바탕으로 Authentication 객체가 생성되어 시스템에서 사용자를 대표하게됨
* UsernamePasswordAuthenticationToken
  * 인증 구현 중 하나
  * Java 라이브러리의 Principal 인터페이스를 implements한것 (Principal는 getName메서드 보유)
  * 자바 보안과 관련된 중요한 개념 중 하나로, "주체"를 나타냄
  * 스프링 시큐리티에서는 Principal 개념이 Authentication 객체에 포함되어 있음
    * 스프링 시큐리티 내에서, Authentication 객체의 getPrincipal() 메서드는  인증된 사용자의 Principal을 반환함
    * 여기서 반환되는 Principal 객체는 일반적으로 UserDetails 인터페이스를 구현한 객체가 됨
  * Authentication 인터페이스는 스프링 시큐리티가 제공하는 인터페이스
    * getPrincipal, getDetails, getAuthorities, getCredentials, isAuthenticated, setAuthenticated와 같은 메소드 보유
* DaoAuthenticationProvider에서 유저 인증 시 유저 세부 정보가 이 Authenticate 메소드 내에 채워진 후에 
  * 인증이 성공하면 createSuccessAuthentication이라는 메서드를 호출함
  * createSuccessAuthentication이라는 메서드는 UserDetails에서 Authentication으로 필요한 모든 세부 정보를 채우고 
  * 동일한 것을 반환함


<br>

### UserDetailsService 와 UserDetailsManager 인터페이스 심층 분석
* UserDetailsService 인터페이스
  * loadUserByUsername이라는 메서드 존재함
  * UserDetailsManager에 의하여 확장됨 (public interface UserDetailsManager extends UserDetailsService)
* UserDetailsManager 인터페이스
  * 새로운 유저 생성, 기존 유저 업데이트 할 수 있는 능력 제공 -> Manager라는 이름이 참 적절함
* 기본적으로 유저 세부 정보를 로드하고 저장하는 이 생태계 전반은 기본 AuthenticationProvider인 DaoAuthenticationProvider에 의해 활용됨
  * 단순 자체 로직을 의항하면 AuthenticationProvider를 정의하고 해당 커스텀 내에서 AuthenticationProvider 내에 원하는 로직 작성하기
* 내용

<br>


### UserDetailsManager 를 구현하는 클래스
* LDAP관련 의존성 추가하기
  * spring-ldap-core
  * spring-security-ldap
#### InMemoryUserDetailsManager
  * 운영시 사용할 수 없으므로 강의 이해만 하고 필기는 안하겠음
#### JdbcUserDetailsManager
  * 기본적으로 Spring Security 팀에서 데이터베이스 구조, 테이블 구조, 열 등등 모든 구조를 설계하고 
  * 이러한 설계로 이 구현 클래스 속에 모든 것을 코딩해두었음
  * loadUserByUsername 메소드가 호출 -> 보냈던 유저 이름을 기반으로 이 메서드 안에서 쿼리가 생성되고 SQL쿼리로 변경됨
  * ```public class JdbcDaoImpl extends JdbcDaoSupport implements UserDetailsService, MessageSourceAware ```
    * JdbcDaoImpl 클래스의 loadUserByUsername 메서드 구현 중 일부 : ```return getJdbcTemplate().query(this.usersByUsernameQuery, mapper, username);```
    * 타고타고 들어가면 ```	public static final String DEF_USERS_BY_USERNAME_QUERY = "select username,password,enabled "
			+ "from users "
			+ "where username = ?"; ```
    *  "from users " -> 테이블 이름이 users여야 함을 알 수 있음
    * 혹은 현재 사용중인 내 테이블의 이름으로 쿼리 수정해야함
```java
public class CustomUserDetailsService implements UserDetailsService {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CustomUserDetailsService(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public static final String CUSTOM_USERS_BY_USERNAME_QUERY = "select username, password, enabled " +
            "from member " +  //나의 회원 테이블에 해당하는 
            "where username = ?";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return jdbcTemplate.queryForObject(CUSTOM_USERS_BY_USERNAME_QUERY, new Object[]{username}, new RowMapper<UserDetails>() {
            @Override
            public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                String username = rs.getString("username");
                String password = rs.getString("password");
                boolean enabled = rs.getBoolean("enabled");
                return new User(username, password, enabled, true, true, true, AuthorityUtils.NO_AUTHORITIES);
            }
        });
    }
}
```
* 관리자 인증 시에 member 테이블에서 정보를 가져오는 쿼리만 사용하는 경우 해결방법
  * 조건부 쿼리 실행
    * 사용자가 로그인 시도 시 입력한 정보를 기반으로 먼저 member 테이블에서 조회를 시도하고, 해당 사용자가 없는 경우 admin 테이블에서 다시 조회하는 방식
    * 효율적 측면에서 불리함
  * 다형성을 이용한 인증 제공자 구현
    * 일반 사용자와 관리자를 위한 별도의 인증 제공자를 구현하기
  * 그러나 통합된 사용자 테이블을 사용하는 것이 가장 일반적이고 효율적인 접근 방법일 수 있음
* "org/springframework/security/core/userdetails/jdbc/users.ddl"
  * 해당 스키마 파일에 유저 테이블과 권한 테이블을 생성하는 DDL문이 존재함
* 구현한 인터페이스 중에 GroupManager도 있음
  * GroupsManager의 도움으로 그룹을 생성하고, 유저들을 그룹으로 지정할 수 있음.
  * 때로는 유저에게 하나의 권한만을 지정하는 것은 충분하지 않을 수 있기 때문에
  * 그룹으로 권한들을 묶어 사용할 때 사용함
  * 이후 권한 부여 파트에서 설명함
#### LdapUserDetailsManager
* 흔하게 사용되는 구현 클래스는 아님
* 저장 시스템으로 LDAP서버가 있지 않은 이상 UserDetailManager를 사용하지 못할 수 있기 때문에
* LDAP와 Active Directory를 저장 시스템으로 사용하는 조직은 많지 않기 때문에 필요한 상황에 관련 강의 수강을 추천함
#### UserDetailsManager을 직접적으로 바로 사용할 수 있는가?
* 아님. 강의에서는 DB생성을 하지 않았음



<br>


### 클라우드에 MuSQL서버 설치하기
* AWS를 이용해서 신규 이용시 1년 무료 이용방법 안내
  * aws.amazon.com
  * Modify DB instance 페이지 내부의 Additional configuration이라는 탭에서
    * 기본값은 Not publicly accessible를 Publicly accessible로 변경하기
    * 이렇게 설정함으로써 이 데이터베이스를 바깥의 공개 트래픽에서 접근이 가능함
  * springsecurity 베이스를 클릭하고
    * 이 데이터베이스와 연관된 VPC security groups를 열어야 함(데이터베이스의 보안 그룹)
    * AWS 내부의 Security Groups 설정은 데이터베이스 서버를 거치는 아웃바운드와 인바운드 트래픽을 관리할 수 있도록 해 줌
    * 관심 가져할 할 부분은 인바운드
      * 이 데이터베이스 서버를 향해 웹 애플리케이션에서 들어오는 정보는 인바운드가 됨
      * Edit inbound rules를 클릭햐고 인바운드 규칙을 정의하기
      * 프로토콜, 포트, 소스를 설정하기
      * IP 000은 모든 유형의 트래픽을 허용하므로 삭제하고 새로 설정하기
      * IPv6 선택 -> ::/0
      * IPv4 선택 -> 0.0.0.0/0
    * 하지만 서버를 이용하지 않을 때에는 반드시 정지시키기(계속 작동 중으로 둔다면요금도 증가 )
    * 그러므로 데이터베이스를 사용하지 않을 때에는 Stop temporarily를 선택하기
    * 정지한 데이터베이스는 일주일 뒤 재가동 되므로 사용하지 않을거라면 삭제하기
* 클라우드에서 무료로 MySQL 서버를 구축하는 방법
  * freemysqlhosting.net 사이트 이용
  * 그냥 계정을 생성한 다음 작은 MySQL 데이터베이스를 선택할 수 있음
  * 주의할 점 : 무료로 얼마나 유지될 수 있을 지 알 수 없음, 매주 이메일에 받은 링크로 인증해야 유지됨

<br>



### JdbcUserDetailsManager로 ...하기
* 추가할 의존성
  * spring-boot-starter-jdbc
  * (MySQL사용시) mysql-connector-j dependency
  * spring-boot-starter-data-jpa
```java
@Bean
public UserDetailsService userDetailsService(DataSource dataSource){
    return new JdbcUserDetailsManager(dataSource);
        }
```
* PasswordEncoder Bean도 당연히 필요함!!!!
  *  Spring Security에게는 항상 비밀번호를 어떻게 저장하였는지 알려주어야 함

<br>



### 인증을 위한 사용자 정의 테이블 생성
* JdbcUserDetailsManager는 규모가 작은 애플리케이션이나 Spring Security의 스키마 구조를 따르는 것이 허용되는 실제 애플리케이션에서는 사용 가능함
* Spring Security가 제공하는 User테이블과 다른 구조로 사용자를 관리하고 싶을 때
  * 자신이 원하는 구조에 맞게 UserDetailsService나 UserDetailsManager 구현을 작성하여야함

<br>


### 맞춤형 UserDetailsService 구현
* UserDetailsService 인터페이스를 구현하여서 LoadUserByUsername 메소드 오버라이딩 하기
  * LoadUserByUsername 메서드 내부에서는 정보를 DB로부터 가져오고 UserDetails 객체로 변환하여서 프레임워크로 돌려보내야함(인증을 가능케 하기 위해)
```java
@Service
public class ProjectUserDetails implements UserDetailsService {
    
    @Autowired
    CustonmerRepository custonmerRepository;
    
    @Override
    public UserDetail loadUserByUsername(String username) throws UsernameNotFoundException {
      List<UserDetails> users = custonmerRepository(username);  //변경부분. username은 이메일주소로 해당 회원 찾아오는 것
          if (users.size() == 0) {
              this.logger.debug("Query returned no results for user '" + username + "'");
              throw new UsernameNotFoundException(this.messages.getMessage("JdbcDaoImpl.notFound",
                      new Object[] { username }, "Username {0} not found"));
          }
          UserDetails user = users.get(0); // contains no GrantedAuthority[]
          Set<GrantedAuthority> dbAuthsSet = new HashSet<>();
          if (this.enableAuthorities) {
              dbAuthsSet.addAll(loadUserAuthorities(user.getUsername()));
          }
          if (this.enableGroups) {
              dbAuthsSet.addAll(loadGroupAuthorities(user.getUsername()));
          }
          List<GrantedAuthority> dbAuths = new ArrayList<>(dbAuthsSet);
          addCustomAuthorities(user.getUsername(), dbAuths);
          if (dbAuths.size() == 0) {
              this.logger.debug("User '" + username + "' has no authorities and will be treated as 'not found'");
              throw new UsernameNotFoundException(this.messages.getMessage("JdbcDaoImpl.noAuthority",
                      new Object[] { username }, "User {0} has no GrantedAuthority"));
          }
          return createUserDetails(username, user, dbAuths);

  }
  
    
    
}

```


* 기본적으로는 Spring Security가 우리가 생성한 UserDetailsService의 구현 클래스를 이해할 수 없음
* Spring 프레임워크인 Spring Security에서 이를 이해할 수 있도록 하려면 이 클래스를 Bean으로 생성하여야 함
  * @Service 애너테이션 붙이기
  * 왜냐하면 이 클래스 내부에는 비지니스 로직을 썼으므로 service layer로 기능하기 때문에
* 프로젝트 내부에 UserDetailsService 구현이 여러 개 있으면 DaoAuthenticationProvider에서 충돌이 발생해서 No AuthenticationProvider found라고 표시됨
  * 해결법 : 기존의 JdbcUserDetailsManager 주석처리하기
* 내용

<br>







### 새로운 유저 등록하는 Rest API 구축
* 새로운 비지니스 로직을 작성하기 
  * 엔드 유저가 자신의 정보를 직접 입력하여 데이터베이스에 등록하고 
  * 같은 자격 증명을 이용하여 Spring Boot 웹 애플리케이션이나 그 웹 애플리케이션 내부에 존재하는 REST 서비스에 접근이 가능하도록
* 2가지 방법
  * 인터페이스 UserDetailsManager를 구현하는 것
    * 구현할 때에는 createUser, updateUser, deleteUser와 같은 메소드가 있어 비지니스 로직을 작성하기 위해서는 모두 오버라이드해야 함
    * 권장하지 않음
    * Spring Security는 인증 과정에만 연관되면 되기 때문에
  * 인터페이스 UserDetailsService의 loadUserByUsername만 오버라이딩 하기
* 해당 내용의 테스트를 위해서 csrf보안은 잠시 해제함.
  * http.csrf().disable() 추가
* 내용
* 내용
* 내용

<br>


















