# 유데미 Spring Security 6 초보에서 마스터 되기 최신강의! (JWT , OAUTH2 포함)
## ch.10 - 메소드 레벨 보안

<br>

### Spring Security의 메소드 레벨 보안
* API 경로를 보호하는 것 이외에도 Spring Security는 메소드 보호도 제공함
* Spring Security에 대해 의존성을 가진 애플리케이션 내에서 메서드 레벨 보안 기능 활성화 하는 방법
  * @EnableMethodSecurity 애너테이션 사용하기 -> 메인 클래스 또는 구성클래으에
* 메서드 레벨 보안 기능
  * 웹이 아닌 애플리케이션에서도 강제로 권한을 부여할 수 있음
  * 일부 권한 규칙을 강제하여 접근 권한이 있는 사용자만이  Java 메소드를 호출할 수 있도록 함
* 메서드 레벨 보안을 사용하는 상황
  * [1]. 호출 권한 부여
    * Java 메서드 위에 특정 메서드의 호출을 위해 메서드 레벨 보안을 사용하게 되면 엔드유저가 이에 따른 권한이나 역할을 구성하는데 필요한 설정이 있어야함
    * API경로와 URL에 인증과 권한부여 실시 + 메서드 레벨 보안 -> 보안이 2단계가 됨 => 애플리케이션의 보호가 강력해져 해킹으로 부터 안전함
  * [2]. 필터링 권한 부여
    * 필터링 조건 또는 권한과 규칙에 의해 어떤 데이터를 수락하고 싶은지, 어떤 데이터를 엔드 유저에게 돌려 보애고 싶은지 검증할 수 있음
* 메서드 레벨 보안 활성화 방법
  * @PreAuthorize 와 @PostAuthorize
    * 주석 활용할 때마다 prePostEnabled 라는 특정 변수를 true로 활성화 시켜줘야함 (추천)
    * SpEL 활용 가능함
    * 추천
  * @Secured 와 @RoleAllowed (비추천)
    * 덜 강력함
    * 비추천

<br>

### 메소드 레벨 보안 호출
#### @PreAuthorize
* 이 주석으로 권한에 관한 규칙/보안에 관한 요구사항 정의
* Speing Security 프레임워크는 유저 정보에 따른 보안 관련 요구사항이 충족되어야지만 해당 메소드 호출함
* @PreAuthorize
  * 메서드 실행 권한을 검사할 때 사용 
  * 구체적 사례 : 유저 이름을 기반으로 특정 유저 대출 정보를 로딩하는 메서드
  * 로직은 간단히 만들고 권한 역할을 애너테이션으로 체크하기
  * 예시
    * @PreAuthorize("hasAuthority('VIEWLOANS')")
    * @PreAuthorize("hasRole('ADMIN')")
    * @PreAuthorize("hasAnyRole('ADMIN','USER')")
    * @PreAuthorize("#username == authentication.principal.username")
  * 표현식에서 반환된 값이 true일 경우에만 메서드가 실행
#### @PostAuthorization
* @PostAuthorization
  * 메소드의 호출을 멈추지 않음
  * 메서드 실행 후에 표현식을 평가
  * 메서드의 실행 결과를 바탕으로 보안 결정을 내릴 때 사용
  * 대신 별도 권한 규칙 없이 실행되면서 + 어떤 것들이 반환되는지 검증함(반환하는 객체에 대한 접근 권한을 검사)
    * ex) 리턴 객체가 특정 유저와 연관 있는지 확인할 수 있음
  * 메서드 실행 후, 표현식에서 반환된 값이 true가 아니라면, 보안 예외가 발생
  * 예시
    * @PostAuthorization("returnObject.username == authentication.principal.username")
    * @PostAuthorization("hasPsermission(returnObject, 'ADMIN')")
  * 
* 요구사항이 매우매우매우 복잡한 경우  PermissionEvaluator 인터페이스를 구현할 수 있음
  * hasPermission 메서드를 오버라이딩해서 복잡한 로직을 안에 작성하기
  * ```boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission);```
    * 매개변수 1 : 로그인한 유저에 대한 정보
    * 매개변수 2 : 엔드 유저에게 리턴하려는 객체, 객체 자체를 보낼 수 있음
    * 매개변수 3 : hasPermission 메서드에서 평가하려는 것
  * ```boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission);```
    * 매개변수 2 : targetId 객체의 ID or 직렬화ID
    * 이거 말고 위의 메서드를 이용하자
  * 그리고 사용하려는 메서드에서 @PreAuthorize/@PostAuthorization 애너테이션 안에서 hasPermission 키워드를 사용하기
* 두 애너테이션의 사용 추천 상황
  * @PreAuthorize : 요구 사항이 명확할 때, 대부분 이 애너테이션 사용
  * @PostAuthorization : 엔드유저에게 다시 보내려고 작성한 객체를 기반으로 평가하고 싶을 때
* 애너테이션만으로 메서드 보안이 가능한 이유
  * Spring AOP 덕분에
  * 런타임에서 Spring Security 프레임워크가 메서드 호출이 일어날때 호출 이전에 인터셉트 하여서 @PreAuthorize 주석의 도움으로 관련 규칙 실행
  * 그리고 @PostAuthorization 주석이 있으면 메서드 출력이 반환되기 전에 인터셉트함



#### 프로젝트에 적용하기
* 애플리케이션 내에서 메서드 레벨 보안을 활성화 하기 위해서 메인 클래스 주석 수정하기
```java
@SpringBootApplication
@EnableMethodSecurity(prePostEnabled = true,  securedEnabled = true,  jsr250Enabled = true)
public class EazyBankBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EazyBankBackendApplication.class, args);
	}

}
```
* config 클래스 수정
```
(수정 전)
.requestMatchers("/myLoans").hasRole("USER")

(수정 후)
.requestMatchers("/myLoans").authenticated()
```
* @PreAuthorize, @PostAuthorization 는 컨트롤러, 서비스, 레파지토리 어떤 메서드에도 사용 가능

<br>


### 권한 필터링
* 필터링 조건 강제하기
* @PreFilter
  * Java 메서드에 받고싶지 않은 특정 입력이 있는 상황
  * 받고싶지 않은 권유 규칙 기반의 입력이 있는 상황
  * @PreAuthorize 와 유사
  * 실제 메소드 비즈니스 로직에 작성해둔 요구사항 기반으로 필터링된 정보만 보낼 수 있음
  * @PreFilter 를 붙이는 메서드의 매개변수는 반드시 컬렉션 인터페이스 유형이여야함
* @PostFilter
  * @PostFilter 를 붙이는 메서드의 반환타입은 반드시 컬렉션 인터페이스 유형이여야함
  * 작성한 메소드의 객체의 필터링 기준 혹은 권한 기준을 시행할 수 있음
  * 
* 예시
```java
@RestController
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    @PostMapping("/contact")
    // @PreFilter("filterObject.contactName != 'Test'")  //test 라는 이름의 Contact 객체를 받으면 정보처리 X
    @PostFilter("filterObject.contactName != 'Test'")  //test 라는 이름의 Contact 객체는 보내지 않기
    public List<Contact> saveContactInquiryDetails(@RequestBody List<Contact> contacts) {
        Contact contact = contacts.get(0);
        contact.setContactId(getServiceReqNumber());
        contact.setCreateDt(new Date(System.currentTimeMillis()));
        contact = contactRepository.save(contact);
        List<Contact> returnContacts = new ArrayList<>();
        returnContacts.add(contact);
        return returnContacts;
    }

    public String getServiceReqNumber() {
        Random random = new Random();
        int ranNum = random.nextInt(999999999 - 9999) + 9999;
        return "SR"+ranNum;
    }
}
```
* Contact 엔티티 생성
```java
@Getter  @Setter
@Entity
@Table(name = "contact_messages")
public class Contact {

	@Id
	@Column(name = "contact_id")
	private String contactId;

	@Column(name = "contact_name")
	private String contactName;

	@Column(name = "contact_email")
	private String contactEmail;
	
	private String subject;

	private String message;

	@Column(name = "create_dt")
	private Date createDt;
}
```












