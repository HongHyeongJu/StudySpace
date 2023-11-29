## CORS, 오리진 간 리소스 공유
* 브라우저에서 현재 오리진 외부에 있는 리소스에 AJAX 호출을 보내는 것은 허용되지 않dma
* 도메인 간 허용되는 요청을 설정할 수 있는 규격
* 사용 가능한 접근법
  * 1. 글로벌 설정 
    * 모든 컨트롤러, 모든 리소스, 모든 REST 컨트롤러에 적용됨
    *  addCorsMappings 콜백 메서드를 WebMvcConfigurer에 추가하기
  * 2. 모든 컨트롤러에서 로컬 설정을 사용하는 것
    * 특정 요청 메서드나 특정 컨트롤러 클래스에 @CrossOrigin을 추가


<br>

## 사용자 자격증명 저장하기
* 저장장소
  * 인메모리 : 모든 데이터를 메모리에 저장하는 데이터베이스 유형
    * application.properties에 설정한 값을 기준으로 함 
  * 데이터베이스 : 디스크에 데이터를 영구적으로 저장하는 시스템
  * LDAP(경량 디렉터리 액세스 프로토콜) : LDAP는 디렉터리 서비스를 쿼리하는 프로토콜,
    * : LDAP 자체는 데이터베이스가 아니라, 이러한 디렉터리 서비스에 접근하기 위한 방법을 제공

```` java
    @Bean
    public UserDetailsService userDetailsService() {
        // 각 사용자에 대한 UserDetails 객체 생성
        UserDetails user1 = User.withUsername("hello")
                                .password("{noop}world")
                                .roles("USER")
                                .build();

        UserDetails user2 = User.withUsername("lemon")
                                .password("{noop}candy")
                                .roles("ADMIN")
                                .build();

        UserDetails user3 = User.withUsername("black")
                                .password("{noop}pink")
                                .roles("PLAYER")
                                .build();

        // InMemoryUserDetailsManager에 UserDetails 객체들을 전달
        return new InMemoryUserDetailsManager(user1, user2, user3);
    }
````
* 이건 테스트용. 
* 메모리에 세부 정보를 저장하는 것은 테스트 목적으로는 좋지만 프로덕션 환경용으로는 권장되지 않음

<br>

* UserDetailsService는 사용자별 데이터를 로드하는 코어 인터페이스
  * 사용자 세부 정보를 가져올 때 사용함
* InMemoryUserDetailsManager 
  * UserDetailsManager의 비지속적 구현
  


<br>

## JDBC를 이용한 사용자 자격증명을 저장 방법
* gradle에 의존성 추가
````
	implementation 'org.springframework.boot:spring-boot-starter-jdbc'  //jdbc 추가
	implementation 'com.h2database:h2'  //h2 DB
````
* url 고정 - application.properties파일에
````
spring.datasource.url=jdbc:h2:mem:testdb
````
* 관리자 계정으로 로그인 -> localhost8080:/h2-console 이동 -> chrome://restart를 입력
* 접속하면 깨진 이미지르 보게됨 (Spring Security에서는 기본적으로 프레임을 사용 해제하기 때문에)
* 프레임 사용 설정 방법
* 필터 체인에 내용 추가 http.headers().frameOptions().sameOrigin()
  * 요청이 동일한 오리진에서 오는 경우 해당 애플리케이션에 프레임을 허용하도록 지정하는 것

<br>
* JdbcDaoImpl 클래스 
* DEFAULT_USER_SCHEMA_DDL_LOCATION가 정의되어 있음(사용자 스키마의 데이터 정의 언어)

````
create table users(username varchar_ignorecase(50) not null primary key,password varchar_ignorecase(500) not null,enabled boolean not null);
create table authorities (username varchar_ignorecase(50) not null,authority varchar_ignorecase(50) not null,constraint fk_authorities_users foreign key(username) references users(username));
create unique index ix_auth_username on authorities (username,authority);
````

* 클래스의 기본 스키마 정의를 복사해서 현재 만드는 configration에서 빈 생성에 사용하기
````java
    @Bean
    public DataSource dataSource(){
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript(DEFAULT_USER_SCHEMA_DDL_LOCATION) //자체 데이터 소스를 생성
                .build();
        }
    }

````

<br>
* 사용자 추가
* 앞서서 자동 생성한 사용자는 주석처리하고 새롭게 작성
* JDBC매니저(JdbcUserDetailsManager) 이용하기

````java
    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource){

        UserDetails user1 = User.withUsername("hello")
                                .password("{noop}world")
                                .roles("USER")
                                .build();

        UserDetails user2 = User.withUsername("lemon")
                                .password("{noop}candy")
                                .roles("ADMIN")
                                .build();

        UserDetails user3 = User.withUsername("black")
                                .password("{noop}pink")
                                .roles("PLAYER")
                                .build();

        var jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.createUser(user1);
        jdbcUserDetailsManager.createUser(user2);
        jdbcUserDetailsManager.createUser(user3);

        return jdbcUserDetailsManager;

    }
````
* h2 확인하면 데이터 저장과 권한 부여 확인됨







