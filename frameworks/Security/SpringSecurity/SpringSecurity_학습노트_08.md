### JWT 리소스 설정하기
* **[1]JWT 만들기**
  * 인코딩하기
    * 특정한 사용자를 인증
  * JWT를 생성할 수 있는 리소스 필요함
* **[2]JWT를 헤더의 일부분으로 전송하기**
* **[3]JWT 검증** (OAuth2 리소스 서버에서)
  * 디코딩
  * 공개키 사용

<br> 

* 오히려 앞서서 디코딩과 RSA 공개키를 이미 만들었다.
* 거꾸로 1단계 인코딩 만들기를 이제 배움

<br> 

### 인코더 만들기
```java
    //인코더 만들기
    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource){
        return new NimbusJwtEncoder(jwkSource);
    }
```

<br> 
 
* JWT 토큰을 만들기 위해 JWT 리소스를 만들기
* JWT 리소스로부터의 응답이 JWT 토큰됨
* [1] 1단계에서 우리는 기본 인증을 사용해서 JWT 토큰을 받음
* [2-n] 2-n단계에서는 REST API에 대한 요청을 인증하기 위해 JWT 토큰을 Bearer 토큰으로 활용
  * "Bearer 토큰"
  * 웹 애플리케이션 및 API 보안에 사용되는 인증 방식 중 하나
  * 주로 OAuth 2.0 인증 프로토콜에서 사용됨
  * 웹 서비스에서 클라이언트가 인증을 통해 자원에 접근할 때 인증 정보를 전달하는 방식 중 하나
  * 클라이언트는 API 요청을 보낼 때 HTTP 헤더에 발급받은 액세스 토큰을 "Authorization" 헤더에 포함시킴. 이때, "Bearer"라는 접두사를 함께 사용


<br> 

Alt+Shift+I 

<br> 

### JwtAuthenticationResource 클래스(컨트롤러) 생성
* private JwtEncoder jwtEncoder; 주입받음
* 인증 객체를 받아서 JwtResponse를 생성하는 메서드 createToken(Authentication)
  * JwtClaimsSet은 JSON Web Token이 전달한 클레임을 나타내는 JSON 객체.
  * 이것을 빌더로 사용
```java
    //인증 객체를 받아서 JwtResponse를 생성하는 메서드
    private String createToken(Authentication authentication) {
        var claims = JwtClaimsSet.builder()
                .issuer("self")  //토큰을 발급한 엔터티를 나타냄
                .issuedAt(Instant.now())  // JWT 토큰의 발급 시각을 현재 시각으로
                .expiresAt(Instant.now().plusSeconds(60*15))  //JWT 토큰의 만료 시각을 현재 시각으로부터 15분 후로 설정
                .subject(authentication.getName())  //JWT 토큰의 주체(subject)를 인증(Authentication) 객체에서 가져온 사용자의 이름으로 설정
                .claim("scope", createScope(authentication)) //큰 내에 포함된 데이터를 나타내는 부분(키-값 쌍으로 표현)
                .build(); // JwtClaimsSet 객체를 생성하고, 이 객체는 JWT 토큰의 내용을 나타내는데 사용
        
/*        JwtEncoderParameters parameters = JwtEncoderParameters.from(claims);
        return jwtEncoder.encode(parameters).getTokenValue();*/  //리팩토링
        
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
```
* createScope() 메서드에서 모든 권한을 받기
```java
    private String createScope(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(a->a.getAuthority())
                .collect(Collectors.joining(" "));  //모든 권한을 공백으로 구분해서 취합
    }
```

<br>

* claims로부터 JwtEncoderParameters를 만들고요, 그걸 인코딩하고 getTokenValue() 해서 토큰 값을 리턴
* 코드가 복잡해 보이지만 authentication 객체로부터 모든 세부정보를 수집한 것뿐!
* 위의 코드로 받은 토큰
```
{
"token": "eyJraWQiOiI4YjhjOGRjYS05MmFiLTQ2ZmItOWM4ZS01YWU2NjE2OTAzM2QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiaGVsbG8iLCJleHAiOjE3MDE4NzcwNDMsImlhdCI6MTcwMTg3NjE0Mywic2NvcGUiOiJST0xFX1VTRVIifQ.SLxWSDeWpU4_7dFcFnP1kIvx24jtEZBuZSE6WAj_I4Oc54JkngTKf9aH21uqryo_r0D-blDPMg-j2cFvIxTBGokjR5_lXIu7o8CUVACJ4HM_AQ_KSAw0PUSCzPNhmAOTAwy5XByK_R8Q-eYt34XR8_rFUgxjErmzXXQ4H4heMnKBG9TLpop7qZ-ODORTmHVxeGGVwh3KRS5refupH0fgqSeN50wYTIKXQlU0ETLUku2txyuqKqGXb_57u7jmYJuHIDwznw_NujznFZzi1tU7b4jAgh1vsuF58holw0pT-TRcXpTE-2FI1oyLi8dpW2r4cbRbgCNiqtGZ1QYB4mu-hA"
}
```
* jwt.io를 방문해서 Json을 뗴고, 따옴표, 중활호를 삭제하면 유효한 JWT 토큰임을 알 수 있음
* 인증 헤더에 Bearer eyJraWQiOiI4... 처럼 보내면 인증 완료










