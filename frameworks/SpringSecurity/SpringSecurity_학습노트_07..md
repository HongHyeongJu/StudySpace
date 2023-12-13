## JWT 인증 설정하기
* build.gradle로 가서 OAuth2 리소스 서버를 설정하기(의존성 추가)
```` 
implementation 'org.springframework.boot:spring-boot-starter-oauth2-resource-server'
```` 
* REST API를 노출시키고, 인증과 승인에 JWT를 사용할 예정
* 기존의 'BasicAuthSecurityConfiguration' 파일의 내용을 복사해서 'JWTSecurityConfiguration'를 새로 만듦
* 기존의 클래스는 @Configuration 주석처리,
* JWTSecurityConfiguration은 jwt 패키지로 이동
* 기존의 DataSource, jdbcUserDetailsManager은 그대로 사용

<br>

### Oauth2 리소스 서버 활성화 하기
* shift 2번 누르고 OAuth2ResourceServerConfigurer 검색
* OAuth2ResourceServerConfigurer클래스는 OAuth2리소스 서버 지원을 제공해줄 수 있는 클래스
* 기본값으로 BearerTokenAuthenticationFilter를 연결해줌
* (이전 개념에서 배운 내용) JWT는 요청 헤더의 일부로 전송될 것이며, Bearer 토큰 형태로 전송됨
* 리소스 서버의 역할 : 이 토큰을 받아서 디코딩하고, 인증과 승인을 적절히 수행하기
* jwt()메서드로 OAuth 서버 설정을 할 수 있음
* 해당 메서드에서 레퍼런스 카피를 하고 현재 수정중인 JWT어쩌구 클래스의 필터체인에 붙여넣기함
* 예시 org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer#jwt()
````java

    @Bean
    SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
        http.sessionManagement() .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.httpBasic(withDefaults()); //구문은 브라우저에 기본 인증 팝업을 표시하도록 설정

        http.csrf().disable(); //csrf 사용 안함

        http.headers().frameOptions().sameOrigin(); // 프레임 사용 설정

        http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt); //추가한 코드


		return http.build();
	}
````
* 프로젝트 빌드 하면 실패 정보 뜸
````

***************************
APPLICATION FAILED TO START
***************************

Description:

Method SecurityFilterChain in com.udemystrudy.learnspringsecurity.jwt.JWTSecurityConfiguration
 required a bean of type 'org.springframework.security.oauth2.jwt.JwtDecoder' 
 that could not be found.
````
* (해석) SecurityFilterChain메서드가 JwtDecoder타입의 bean을 요구했는데, 그것을 찾을 수 없어서
* 디코딩을 위해서는 JwtDecoder가 필요하다
* 이를 위해서는 RSA 키쌍을 만들고, RSA 키 객체를 만드는 것이 선행되어야함

<br>

=============================================================

<br>

### [1] 키 쌍 만들기
* openssl라는 툴로  RSA 공개키와 비밀키를 만들 수 있음
* 강의에서는 코드를 통해 키 쌍을 만들 예정임(KeyPairGenerator라는 클래스를 사용해서)
* KeyPairGenerator클래스는 java.security의 일부
* generateKeyPair() 메서드로 키 쌍을 만들 수 있음
````java
//키 페어 만들기 (디코딩 사전 작업)
@Bean
public KeyPair keyPair(){
        try {
        var keyPaitrGenerator = KeyPairGenerator.getInstance("RSA");
        keyPaitrGenerator.initialize(2048);
        return keyPaitrGenerator.generateKeyPair();
//        } catch (NoSuchAlgorithmException e) {
        } catch (Exception e) {
        throw new RuntimeException(e);
        }
        }
````

<br>
       
### [2] RSA 객체 만들기 (키 쌍 이용해서)
* RSA 인코딩과 디코딩을 위해 nimbus라는 라이브러리를 사용
* nimbus는 이미 우리의 클래스 패스에 존재함(OAuth 리소스 서버 의존성에 포함)
* nimbus를 import해야함!
````
//import java.security.interfaces.RSAKey;   //이거 import하면 오류남. 조심
import com.nimbusds.jose.jwk.RSAKey;        //nimbusds import를 꼭 확인하기!(직접 작성해야함)
````


* RSA 객체 만드는 코드

````
    //RSA 객체 만들기
    @Bean
    public RSAKey rsaKey(KeyPair keyPair){

        return  new RSAKey
                .Builder((RSAPublicKey)keyPair.getPublic())
                .privateKey(keyPair().getPrivate())
                .keyID(UUID.randomUUID().toString())
                .build();
    }
````

<br>

### [3] JSON Web Key source 만들기
* RSA 키 하나만 있는 JWKSet 만들고 -> 이것을 이용해서 JWKSource 만들기
```java
public interface JWKSource <C extends SecurityContext> {

	List<JWK> get(final JWKSelector jwkSelector, final C context)
		throws KeySourceException;
}
```
* JWKSource는 get()메서드를 구현해야함
* 람다식을 이용해서 간추리기, 매개변수로 jwkSet을 사용함
```java
    @Bean
    public JWKSource<SecurityContext> jwkSource(RSAKey rsaKey){
        var jwkSet = new JWKSet(rsaKey);

        return  (jwkSelector, context) -> jwkSelector.select(jwkSet);

    }

```


### 디코더 만들기
* Nimbus가 디코더와 인코더를 모두 제공함. 
* 디코더 클래스를 살펴보면 많은 메서드가 있는데 그 중 withPublicKey()메서드를 사용
* 이 메서드는 RSAPublicKey를 매개변수로 받음. 이것의 인스턴스를 생성하고 디코딩에 사용함
```java
@Bean
public JwtDecoder jwtDecoder(RSAKey rsaKey) throws JOSEException {
        return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
        }
```
* 미처리 예외 타입 JOSEException
* 이 Bean을 직접 연결하지 않을 것이기때문에 예외내도 상관 없음

























