# 유데미 Spring Security 6 초보에서 마스터 되기 최신강의! (JWT , OAUTH2 포함)
## ch.04 - PasswordEncoder 관리

<br>

### Spring Security에서 비밀번호가 기본적으로 인증되는 과정
* 엔드 유저가 본인의 자격증명인 유저네임과 비밀번호 입력
* 로그인 버튼 클릭 시 Spring Security 프레임워크는 인증 제공자 냅의 모든 로직을 실행
  * UserDetailsManager의 구현 클래스 내부에 있는 loadUserByUsername 메소드의 도움으로 저장소에서 모든 정보 불러옴
  * PreAuthenticationChecks 내부에서 모든 부정적이 시나리오 확인(비활성 등)
  * 전부 성공 시 additionalAuthenticationChecks라는 메소드 호출함(DaoAuthenticationProvider 안에서 실질적인 검증이 일어남)
  * 구현부에서 PasswordEncoder.matches라는 메소드를 호출해서 PasswordEncoder안에 구현된 passwordEncoder로 비밀번호 검증
  * 엔드 유저가 제공한 비밀번호와 데이터베이스에서 불러온 비밀번호를 비교
  * 비밀번호가 일치한다면 로그인에 성공하고 그 반대는 로그인에 실패
* 비밀번호를 일반 텍스트로 저장하고 비교하는 것은 무결성과 기밀성에 문제 있으므로 반드시 암호화 해야함

<br>

### 인코딩 VS 암호화 VS 해싱
#### 인코딩
* 데이터를 A형식에서 B형식으로 변환하는 과정
* 기밀성이 포함되지 않음
* 디코딩의 과정으로 복원할 수 있음
* 완전히 가역적임
* 유명한 인코딩
  * ASCII, BASE64, UNICODE
#### 암호화  
* 기밀성을 보장하는 방법
* 일반 데이터를 변환하는 과정
* 데이터 암호화 시 특정 알고리즘을 따르고, 이 암호화 알고리즘에 비밀키를 제공하는 것
* 특정 암호화 같의 일반 텍스트가 무엇인지 알고싶다면 복호화 해야함
* 복호화 하고 싶은 경우에는 무조건 암호화 과정에서 사용된 동일한 알고리즘과 동일한 비밀키 또는, 키를 사용해야함
* 암호화 알고리즘과 비밀키는 주로 백엔드 애플리케이션 내부에 기밀 데이터로 관리됨
* 개발자나 테스터가 비밀키나 알고리즘 같은 변수에 접근할 수 있기 때문에 이것을 비밀번호 관리에는 추천하지 않음
#### 해싱
* 해싱에서 데이터는 수학적 해싱 기능을 사용해서 해시값으로 변환됨
* 해싱을 적용하면 비가역적임
* 최초의 일반 텍스트 비밀번호를 알아래는 것은 매우 어렵거나 불가능함
* 비밀번호 검증시 유저가 입력한 비밀번호의 해싱값 VS DB에 저장한 기존 비밀번호의 해싱값을 비교함
* 해싱이 작동하는 시범
  * Bcrypt-Generator.com
* Bcrypt는 해싱 알고리즘

<br>


### PasswordEncoder 인터페이스
* PasswordEncoder 인터페이스
  * 2개의 추상 메서드와 1개의 디폴트 메서드가 있음
  * (추상) String encode(CharSequence rawPassword);
    * 엔드 유저의 등록 절차에 사용할 수 있음
    * 등록 절차에 입력한 일반 텍스트 비밀번호를 해시 문자열/암호화된 값으로 변환함
    * 사용하는 PasswordEncoder에 기반해 그에 상응하는 해싱 알고리즘과 해싱 절차가 일어남
  * (추상) boolean matches(CharSequence rawPassword, String endodePassword);
    * 로그인 작업에서 유저 입력 비밀번호와 DB저장 비밀번호 비교에 사용함
  * (디폴트) default boolean upgradeEncoding(String encodePassword){return false;}
    * 언제나 false를 반환함
    * upgradeEncoding이 true값을 반환하면 해싱을 두번함
    * 일반 비밀번호는 기본 1회 해싱으로 충분함

<br>


### PasswordEncoder 구현 클래스
* Spring Security 프레임워크 안의 다양한 PasswordEncoder 구현 클래스
#### NoOpPasswordEncoder
* 해싱X, 인코딩X, 암호화X
* 비밀번호를 일반 텍스트로 취급함
* 사용추천 안함
#### StandardPasswordEncoder
* 운영 앱에 추천하지 않음
* 사용 중단 표시됨
* @Deprecated라는 주석
  * 이 PasswordEncoder를 추천하지 않는 이유는 그저 일반 텍스트 비밀번호를 암호화기 위해
  * 암호화 알고리즘을 사용하는 레거시 애플리케이션을 지원하고자 이 PasswordEncoder를 구현한 것이기 때문입니다
* 무작위 솔트값을 사용해 SHA-256 해싱 알고리즘을 구현함
* 또한 추가적인 보호를 제공하기 위해 시스템 전체 비밀 값을 사용함
* 최신 애플리케이션에서는 안전하지 않음
#### Pbkdf2PasswordEncoder
* 5-6년 전쯤에는 사용했지만 이것도 운영 어플에 추천하지 않음
* 고성능 GPU 기계를 갖고 있다면 손쉽게 공격받고 추측할 수 있음
* 무차별 대입 공격에 취약함
  * 해커가 다양한 입력값을 시도해보고 주어진 해시 값의 최초 텍스트 비밀번호를 추측하는 공격
  * 이를 예방 하기 위해서는 다양한 조합의 8자리 이상의 비밀번호 사용하고, 강력한 해싱 알고리즘을 사용하도록 하기


#### BcryptPasswordEncoder
* 1999년에 발명된 BCrypt 해싱 알고리즘을 사용함
* 일반 텍스트값을 해싱하거나 matches 메소드를 실행하려고 하면 이것은 CPU 연산을 요청함 -> 쉬운 Java코드가 아님
* 개발자가 설정한 작업량, 라운드 수에 따라 이 해싱 알고리즘이 사용하는 CPU연산이 더 많아질 수 있음
* 무차별 대입 공격에 강력함
#### SCryptPasswordEncoder
* 알고리즘의 해싱 기능 또는 matches 기능을 사용하고자 할 때 두 개의 인자를 요구함
  * BcryptPasswordEncoder에서 다룬 연산능력
  * 메모리
* 고의적으로 일부 메모리 할당을 요구하므로 해킹에 더 어려워짐
#### Argon2PasswordEncoder
* 최신 알고리즘
* 세가지 측면이 필요함
  * Bcrypt, SCrypt 에서 사용한 연산능력
  * 메모리
  * 다중 스레드/다중 CPU 코어
* 매우 강력하다는 장점이 있지만 해커의 연산에 오래걸리듯 일반 유저가 로그인 시에도 웹 애플리케이션이 세가지 자원을 제공해야 하기 때문에 느려짐
#### 결론
* 알파벳+숫자+특수문자 비밀번호와 BcryptPasswordEncoder를 이용하는것을 추천함

<br>


### Bcrypt PasswordEncoder 사용 (유저 등록)
* 기존의 passwordEncoder() 메서드 수정
```
@Bean
public PasswordEncoder passwordEncoder(){
  return new BCryptPasswordEncoder();
}
```
* 그리고 로그인 컨트롤러에 ```@Autowired  private  PassewordEncoder passwordEncoder;```추가
```java
@RestController
public class LoginController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Customer customer) {
        Customer savedCustomer = null;
        ResponseEntity response = null;
        try {
            String hashPwd = passwordEncoder.encode(customer.getPwd());  //이부분에 적용
            customer.setPwd(hashPwd);
            savedCustomer = customerRepository.save(customer);
            if (savedCustomer.getId() > 0) {
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("Given user details are successfully registered");
            }
        } catch (Exception ex) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occured due to " + ex.getMessage());
        }
        return response;
    }


}
```
* 해싱 알고리즘 라운드 수는 기본값인 10으로도 충분함

<br>


### Bcrypt PasswordEncoder 사용 (로그인)

* BCryptPasswordEncoder 클래스 내부에는 public enum BCryptVersion 이 존재함
  * $2A("$2a"), <- Spring Security가 사용하는 버전
  * $2Y("$2y"),
  * $2B("$2b");
* 빈 생성자 호출시 ```public BCryptPasswordEncoder() { this(-1);}``` 이렇게 작성되었기 때문에
  * 또 다른 생성자로 가고 이것이 전송할 기본 버전은 $2a인 것을 알 수 있음
  * 또한 strength 값이 -1로 일치한다면 기본 설정으로 10을 고려함 (해싱 과정에서 사용하는 로그 라운드 수나 작업량 변수가 됨)
* strength 값의 범위 : 4 ~ 31
```java
	public BCryptPasswordEncoder(BCryptVersion version, int strength, SecureRandom random) {
		if (strength != -1 && (strength < BCrypt.MIN_LOG_ROUNDS || strength > BCrypt.MAX_LOG_ROUNDS)) {
			throw new IllegalArgumentException("Bad strength");
		}
		this.version = version;
		this.strength = (strength == -1) ? 10 : strength;
		this.random = random;
	}
```
* SecureRandom 값의 목적은 무작위로 생성된 값을 더해주도록 함
* 모든 PasswordEncoder들 또는 다른 비밀번호 관련 클래스들은 org.springframework.security.crypto 패키지에서 찾을 수 있음
* 이미 업계 표준을 준수하고 있는 제공되는 PasswordEncoder를 사용하자


<br>


<br>












