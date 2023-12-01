## Spring Security를 이용해 패스워드를 처리하는 방법
* SHA-256 같은 해싱 알고리즘은 더 이상 안전하지 않음(최신 시스템이 너무빨리 계산 수행함)
* 권장하는 패스워드 저장방법 : 1초를 워크 팩터로 적용해 적응형 단방향 함수를 사용하는 것
* 단방향 함수
  * 한 방향으로 작동하는 함수
  * 적응형 단방향 함수의 대표적인 예 : bcrypt, scrypt, argon2 
* 워크 팩터
  * 시스템에서 패스워드를 확인하는 데 걸리는 시간
  * 패스워드를 해싱하고 이것을 저장된 값과 비교하는 데 걸리는 시간
  * 상당한 양의 작업이 필요하기 때문에 시스템에서 패스워드를 확인하는 데 최소 1초가 소요됨
  * 문제는 시간이 갈수록 시스템이 향상되므로 알고리즘도 그에 맞게 적응해야함(복잡성이 커지고 소요 시간이 증가하도록 설정하기)
* 패스워드 저장의 경우 해싱이 매우 빠르게 이루어진다면 다른 사람이 해싱을 수행하고 브루트 포스 알고리즘을 적용할 수 있습니다.
  * 의문점 : 너무 빠른 해싱이 안좋다?
  * 해싱이 너무 빠르면 공격자는 더 많은 비밀번호 조합을 짧은 시간 안에 시도할 수 있습니다. 
  * 이는 특히 공격자가 해시된 비밀번호 리스트를 가지고 있을 때 더 큰 문제가됨
* PasswordEncoder 인터페이스
  * 단방향으로 패스워드 변환을 수행하는 인터페이스
  * 다양한 구현 있음
  * BCrypt, Argon2, Scrypt
  * 유의점
    * 이름이 혼동을 줄 수 있음
    * 텍스트 인코딩과는 다른 인코딩. 불가역적
  * 권장되는 PasswordEncoder는 **BCrypt**PasswordEncoder


<br>


## BCryptPasswordEncoder
* 사용방법
* BasicAuthSecurityConfiguration에 추가
````Java
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }
````
* 강력한 해싱 함수 BCrypt를 사용하는 PasswordEncoder 구현함
* 해싱을 수행하며 클라이언트는 버전과 강도를 선택적으로 제공할 수 있음
* 강도 매개변수가 클수록 패스워드를 해싱하는 데 필요한 작업이 기하급수적으로 증가함
* 기본값은 10
* 적용 예시
````java
        var user1 = User.withUsername("hello")
//                                .password("{noop}world")
                                .password("world")
                                .passwordEncoder(str -> passwordEncoder().encode(str))
                                .roles("USER")
                                .build();
````
* H2 데이터베이스 user 비밀번호 저장값이 바뀌었음을 확인함
  * $2a$10$NwBWIDmCGlXcZox4Lb/Rs.rpMUbOk27zr0Cc.5yiYD1pD55WzXWIe
  * $2a$10$58VAqM6pkhYvP/ZSYde5lu/bylmchrWsPgqNyFvk6AvtihTHK0oom
  * $2a$10$yWsoKGDrPVEq8YKBI3EgW.jNiZ4Um5zUNtVfi3LuqmeVIES8KxfE2

