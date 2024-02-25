# 유데미 Spring Security 6 초보에서 마스터 되기 최신강의! (JWT , OAUTH2 포함)
## ch.09 - JSON Web Token JWT를 사용한 토큰 기반 인증

<br>

### 세션 및 관련문제
* 토큰은 인증 및 승인에서 중요한 역할을 함
  * 세션과 쿠키 : 로그인 이후 엔드 유저가 자격증명을 입력하지 않아도 됨
  * XSRF 토큰 : header안으로 보내져서 CSRF공격에 대비
  * 그러나 복잡한 애플리케이션 생성시 도움되지 않음
  * 이유
    * [1] 해당 토큰은 유저 데이터를 갖고 있지 않음. 무작위 생성값으로 토큰 속에 유저 데이터를 저장할 수 없음
    * [2] 세션ID는 브라우저 속 쿠키로 저장되는데, 해당 쿠키는 유저 세션과 엮이게 됨. 엔드 유저가 브라우저를 닫지 않아서 세션이 아직 유효한 경우 악용될 수 있음.

<br>

### 인증 기반 토큰의 장점
* 토큰
  * 일반적으로 엔드 유저의 인증이 완료되자마자 로그인 작업 중 처음으로 생성됨
  * 로그인 중 토큰이 생성된 이후로는 해당 토큰이 클라이언트 어플리케이션에 의해 보호된 자료에 접근할 때마다 백엔드 시스템으로 보낼 수 있도록 사용함
  * 보내진 토큰이 유효한 경우 백엔드 서버는 올바른 반응으로 대응함
* 토큰 인증 단계
  * [1] 엔드 유저가 백엔드 애플리케이션에 본인의 ID와 비밀번호로 로그인 시도
  * [2] (해당 자격 유효한 경우) 백엔드 애플리케이션 또는 승인 서버가 토큰을 생산(무작위 생성), 토큰은 클라이언트에게 다시 전달
  * [3] 클라이언트가 보안된 API에 접속 할 때 클라이언트 애플리케이션이 반드시 같은 토큰을 백엔드 서버로 전송
  * [4] 전달 받은 토큰이 유효 -> 올바른 반응
* 인증 및 권한 부여 과정에서 토큰을 사용해야 하는 이유(장점)
  * [1] 토큰을 이용할 때마다 로그인 중에만 실제 자격증명을 백엔드로 보내면 됨. 
    * 그 뒤의 모든 요청에는 실제 자격증명을 공유하지 않아도 단순히 토큰 공유만으로 백엔드 작업 가능
    * 자격증명이 불필요하게 네트워크에 노출되는 것을 막을 수 있음
    * 백엔드에서 인증을 반복하지 않아도 됨
  * [2] 해커의 탈취로 부터 안전
    * (토큰X) : 해커가 요청마다 주고받는 자격증명 공유 시 유일한 해결책은 모든 엔드 유저의 비밀번호, ID 요청 뿐임 -> 기업 이미지 실추
    * (토큰O) : 토큰 해킹 사실을 알게되면 해당 토큰들을 단순히 무효화 시키면 됨
    * 토큰은 기업의 요구사항에 따른 토큰을 생성하고 이에 대한 수명도 설정할 수 있음
      * 단순히 블로그 등의 훔칠 정보가 없는 경우 1년의 긴 수명을 가진 토큰 만들어도됨, 반면 은행 같은 경우 짧은 수명의 접근 토큰(Access Token)을 만들어야함
    * 로그인 할 때마다 새로운 토큰을 갖게됨
    * 토큰을 사용하여 엔드유저의 정보를 저장할 수 있음 
      * ex) REST API 작업을 하는 사람은 이메일, 역할, 권한과 같은 엔드 유저에 대한 정보가 필요함
    * 현재 Spring Security로 만들어진 JSESSIONID 토큰 안에서는 이러한 유연성이 없어서, JWT 토큰의 도움을 받아야함
  * [3] 토큰은 재사용할 수 있음
    * ex) 구글
    * 토큰이 SSO (싱글 사인 온)을 이루는 데에 있어 도움이 됨
      * 같은 조직 내에서 다양한 애플리케이션을 이용할 때 반복적으로 로그인을 하지 않아도 되는 상황
    * 토큰 덕분에 stateless 로 있을 수 있음
      * stateless
      * 같은 사이트인 애플리케이션의 여러 인스턴스가 있을 때, 로그인 중에 요청이 1번 인스턴ㅅ로 가고, 이후의 요청들은 클러스터 속 다른 인스턴스에게 갈 수 있음
      * 해당 인스턴스가 세션 속 엔드 유저에 대해 특별히 기억하지 않아도 됨
      * 인스턴스 = 마이크로 서비스

<br>


### JWT 토큰 심층 분석
#### JWT = JSON Web Token
  * 내부적으로 이 토큰들은 데이터를 JSON 형식으로 유지하기 때문
  * 웹 요청에 사용 됨
  * UI 클라이언트 -> 백엔드 애플리케이션 통신할 때 사용됨. REST 서비스의 도움으로 JSON 형식으로 통신하기 위해 설계됨
  * 인증, 권한부여에 사용 가능한 토큰
  * 장점
    * [1] 토큰 자체의 내부에서 유저와 관련된 데이터를 저장&공유할 수 있게 도와줌
      * 서버 쪽 세션 안에 유저 정보를 갖고있는 번거로움 해결
      * 마이크로서비스 환경 속에서 엄청난 도움. Stateless 가능! (토큰 자체에서 유저정보 갖고있으므로)
    * [2] 토큰을 저장소나 캐시에 저장하지 않아도 됨
      * (이제 안해도 되는 작업)
        * 백엔드 서버의 가장 기본적인 접근은 해당 JWT 토큰을 처음 생성될 때마다 데이터베이스 혹은 캐시 속에 저장함
        * 클라이언트에서 특정 JWT 토큰을 사용하는 모든 후속 요청에서 해당 JWT 토큰이 이미 데이터베이스나 캐시에 저장된 토큰과 동일한지 확인함
        * 그러나 JWT 토큰의 **서명** 덕분에 어디에도 저장할 필요 없음
    * 
#### JWT 토큰 형식
  * 헤더.페이로드.시그니처 <- 마침표로 구분함
  * [1] 헤더 : 필수
    * 메타 데이터가 저장되어 있음 : 토큰에 대한 정보
    * 알고리즘이 무엇인지, 토큰의 종류, JWT 토큰을 생성하며 사용된 토큰의 형식 등..
  * [2] 페이로드(내용) : 필수
    * 저장을 원하는 내용 저장 가능
    * 일반적으로 저장하느 유저 정보 : 이름, 이메일, 역할, 토큰 만료시간, 토큰 발행자, 토큰 서명자
  * [3] 시그니처(서명) : 선택사항
    * 시그니처 덕분에 서버에 토큰을 저장할 필요 없어짐
    * 백엔드 서버는 헤더와 바디 속 값이 있는  JWT 토큰을 생성할 때마다 토큰에 디지털 서명을 하여서 나중에 누군가 토큰 조작을 시도할 때 쉽게 감지 가능함
#### JWT 토큰의 서명을 통해 (토큰을 저장소나 캐시에 저장하지 않고도) 반복적으로 유효성을 검사할 수 있는 이유
* 시그니처가 필요 없는 경우
  * 시그니처는 클라이언트 애플리케이션이 토큰과 방화벽 속 모든 소통을 조작하지 않을 것이라는 신뢰가 있다면 굳이 필요하지는 않음
  * 모든게 조직 내에 방화벽 속에 있기 때문에
* 시그니처가 필요한 경우
  * 해당 토큰이 인터넷 네트워크 속에서 이동하는 경우, 반드시 아무도 권한, 아이디와 같은 헤더와 바디 값들을 바꾸지 않도록 필요함
  * 토큰의 헤더와 페이로드는 base64로 인코딩 되기 때문에 쉽게 디코딩이 가능함 -> 해커가 조작 쉬움
* 시그니처를 만드는 방법
  * 백엔드에서 성공적인 인증 후에 새로운 JWT 토큰을 생성할 때마다 토큰에 디지털 서명을 하기
  * ex) SHA-256같은 알고리즘의 도움을 받음
* jwt.io

<br>


### JWT 사용을 위한 프로젝트 설정
* 의존성 추가하기 
  * jjwt-api
  * jjwt-impl
  * jjwt-jackson
#### 수정할 코드 [1]
* ProjectSecurityConfig 클래스의 defaultSecurityFilterChain 메서드
* 매번 JSESSIONID를 생성하고 해당 ID를 UI 애플리케이션에 전송하도록 지시하던 코드를 변경하기
* JSESSIONID를 생성 X, session ID 세부 정보도 저장 X
* 삭제할 코드 : ```http.securityContext((context) -> context.requireExplicitSave(false))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))```
* 새로운 코드 : ```http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))```
  * http + 매개변수를 받지 않는 sessionManagement() 메서드  + sessionCreationPolicy() : SessionCreationPolicy는 STATELESS로 선택
  * 생성한 코드의 의미
    * STATELESS로 설정함으로써 Spring Security 프레임워크는 세션을 사용하지 않고, 상태를 유지하지 않는(stateless) 방식으로 인증을 처리하겠다
    * JSESSIONID 쿠키를 생성하지 않으며, 세션을 기반으로 한 인증 상태 관리를 하지 않는다
#### 수정할 코드 [2]
* JWT 토큰을 UI 애플리케이션으로 보낼 때 권한 부여라는 이름의 response 헤더의 도움을 받음
* UI 애플리케이션의 헤더를 노출하려 하는 것이기 때문에 브라우저에게 알려야함
* 이 승인 헤더 없이는 브라우저가 해당 헤더를 받지 않고, UI 애플리케이션이 JWT 토큰을 읽지 못해 최초 로그인 이후 요청 중에 백엔드로 JWT 토큰을 보낼 수 없음
* 추가할 코드 : ```config.setExposedHeaders(Arrays.asList("Authorization"));```
```
(변경 전)
config.setAllowedHeaders(Collections.singletonList("*"));

(변경 후)
config.setAllowedHeaders(Collections.singletonList("*"));
config.setExposedHeaders(Arrays.asList("Authorization"));
```
* 이 ExposedHeaders 메소드에게 UI 애플리케이션에게 보내는 응답의 일부인 헤더의 이름이 무엇인지 보내는 것
  * 보낼 헤더 이름 : 권한부여
  * 그리고 같은 헤더 안에 JWT 토큰 값도 함께 보낼 것임
  * 궁금한점
    * CSRF 토큰 헤더에는 같은 작업을 하지 않은 이유 : 그것은 프레임워크가 제공한 헤더이므로, 프레임워크가 내부적으로 해결함
    * JWT 토큰은 직접 작성한 헤더로 보냄

<br>


### JWT 생성을 위한 필터 설정
* 최초 로그인 중에 JWT 토큰을 생성하기 위한 로직
* 내용
* 필터 구현하기
  * OncePerRequestFilter 상속받는 클래스 생성
  * doFilterInternal 메서드 오버라이딩
#### 토큰 생성 예시
```java
public class JWTTokenGeneratorFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        
        //basic authentication 필터 다음에 넣을 예정이므로, 이 필터는 엔드유저의 인증이 성공적일 것을 전제로함
        //현재 인증된 유저의 정보를 가져오기!
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (null != authentication) {
                                                //인터페이스 SecurityConstants 생성하기
            SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
                            //Keys 클래스는 의존성으로 추가학 JWT 토큰 라이브러리 제공
            
            String jwt = Jwts.builder()
                .setIssuer("Eazy Bank")
                .setSubject("JWT Token")
                .claim("username", authentication.getName())
                .claim("authorities", populateAuthorities(authentication.getAuthorities()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + 30000000))
                .signWith(key, SignatureAlgorithm.HS256) // 서명 알고리즘 명시적으로 지정
                .compact();
            
            response.setHeader(SecurityConstants.JWT_HEADER, jwt);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getServletPath().equals("/user");
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
        Set<String> authoritiesSet = new HashSet<>();
        for (GrantedAuthority authority : collection) {
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",", authoritiesSet);  //모든 권한 정보를 ,로 구분하여서 전달
    }

}
```
* Jwts.builder() 이후 메서드 호출
  * setIssuer() : Issuer은 해당 JWT 토큰을 발행하는 개인 혹은 조직 (강의영상에서는 setIssuer()임...)
  * setSubject() : 아무거나 넣어도 되지만, 평범하게 "JWT Token" 입력
  * claim() : 로그인된 유저의 ID, 권한을 채울 수 있음. 인증객체로부터 정보 받기. 비밀번호는 절대 안돼!
  * setIssuedAt() : JWT 토큰 발행될 때의 날짜 설정하기
  * setExpiration() : JWT 토큰의 만료 시간 설정하기. ```.signWith(key, SignatureAlgorithm.HS256) // 서명 알고리즘 명시적으로 지정```
  * signWith() : JWT 토큰 속 모든 내용에 디지털 서명 하기. 여기에 생성한 key값 넣기
  * compact() : 토큰이 생성됨
* 헤더 설정하고 해당 토큰을 응답에 보내기
  * ```response.setHeader(SecurityConstants.JWT_HEADER, jwt);```
  * SecurityConstants.JWT_HEADER 는 config파일의 ```config.setExposedHeaders(Arrays.asList("Authorization"));```이부분과 같아야함
* shouldNotFilter(HttpServletRequest request) 메서드 
  * 특정 조건 만족 시 필터를 실행하지 않는 메서드
  * 조건 : 로그인 과정 중에만 실행되기
  * ```return !request.getServletPath().equals("/user");```

* contants 패키지 생성
  * SecurityConstants 인터페이스 생성 : JWT 토큰 관련 값 저장
  * 강의에서는 JWT_KEY 값을 직접 명시하지만, 실제 서비스에서는 보안을 고려한 방법으로
```java
public interface SecurityConstants {

    public static final String JWT_KEY = "jxgEQeXHuPq8VdbyYFNkANdudQ53YUn4";  //백엔드 애플리케이션만 알고있어야 함 //원래 이런 하드코딩은 금지!
    public static final String JWT_HEADER = "Authorization";

}
```
#### 생성한 필터 추가하기
* ```.addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)```
#### JWT 토큰을 검증하기
* 로그인 작업 이외의 모든 REST API 호출에 대해 UI 애플리케이션에서 받은 JWT 토큰을 검증하기
* OncePerRequestFilter 상속받는 필터 클래스 생성하기
  * doFilterInternal 메서드 오버라이딩 하기
* 예시 코드 설명
```java
public class JWTTokenValidatorFilter  extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        
                            //권한 부여 헤더 값 가져오기
        String jwt = request.getHeader(SecurityConstants.JWT_HEADER);
        
        
        if (null != jwt) {
            try {
                                    //비밀키 다시 생성
                SecretKey key = Keys.hmacShaKeyFor(
                        //JWT 토큰때 사용한 키를 가져옴
                        SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));

                Claims claims = Jwts.parser()  
                        .verifyWith(key) //parser() 메서드 안의 Secret Key 호출하기  //강의는 setSigningKey()
                        .build()
                        .parseSignedClaims(jwt) 
                        // 강의는 parseClaimsJws() : 매개변수로 받은 jwt의 서명을 검증하고 
                        // 검증에 성공하면  Jws<Claims> 객체를 반환함.
                        //  이 객체는 JWT에서 클레임 세트를 포함하며, 이를 통해 JWT에 담긴 정보에 접근 가능함
                        .getPayload();  //사전작업 이후 바디(페이로드) 읽기
                //claims 를 이용해서 엔드유저의 정보를 가져올 수 있음 (ex. id나 권한)
                
                String username = String.valueOf(claims.get("username"));
                String authorities = (String) claims.get("authorities");
                
                //받은 정보 덕분에 UsernamePasswordAuthenticationToken 종류의 인증객체 생성 가능 (물론 비밀번호는 null)
                Authentication auth = new UsernamePasswordAuthenticationToken(username, null,
                        AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
                //인증객체 생성 성공 = 인증과정이 성공적이다
                
                //인증객체 저장
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                throw new BadCredentialsException("Invalid Token received!");
            }

        }
        
        //filterChain 속에 다음 필터를 호출
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        
        //로그인 작업을 제외하고, 모든 API호출에 대해 실행하도록 하기
        return request.getServletPath().equals("/user");
    }

}
```
#### 생성한 필터 추가하기
* ```.addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)```
* Spring Security 프레임워크의 실제 인증 유효성 검사 이전에 JWT 유효 토큰 필터가 실행되도록


<br>


### JWT 토큰 기반을 위한 클라이언트(UI) 측 수정 작접
* responseData로 부터 Authorization 라는 이름의 헤더 읽기
* 그리고 sessionStorage 안에 Authorization 로 저장
* 이 코드는 최초의 성공적인 로그인 과정 이후 받게 되는 JWT 토큰을 읽게 하는 것
* 요청을 보낼 때
  * 헤더 이름이 Authorization인 요청으로 같은 JWT 토큰을 보내야함
  * Authorization라는 이름의 아이템을 가진 sessionStorage 에서 온 JWT 토큰을 읽었을 때 null이 아니라면 
  * sessionStorage에서 받은 것과 같은 Authorization 값을 보내기
  * ID, 비밀번호를 Authorization 헤더의 일부로 전송은 오직 로그인 도중에만!
  * 모든 후속작업은 JWT 토큰을 전송하기

<br>


### 애플리케이션 실행을 통한 JWT 변동 사항 검증
* 기존의 디버그 코드 제거
  * application.properties의 denug

<br>


### 최신버전의 jjwt에서 API직관적으로 개선됨
* (변경 전)
```
(변경 전)
Claims claims = Jwts.parser()  
                    .verifyWith(key) //parser() 메서드 안의 Secret Key 호출하기  //강의는 setSigningKey()
                    .build()
                    .parseSignedClaims(jwt) 
                    // 강의는 parseClaimsJws() : 매개변수로 받은 jwt의 서명을 검증하고 
                    // 검증에 성공하면  Jws<Claims> 객체를 반환함.
                    //  이 객체는 JWT에서 클레임 세트를 포함하며, 이를 통해 JWT에 담긴 정보에 접근 가능함
                    .getPayload();  //사전작업 이후 바디(페이로드) 읽기
```
* (변경 후)
```
(변경 후전)
Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(jwt)
                    .getBody();
```








