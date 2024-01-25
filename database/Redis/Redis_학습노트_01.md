## 유데미 Redis : 개발자를 위한 Redis 완벽 가이드
### ch.01 - 시작하기 && ch.02 - 데이터를 추가하고 쿼리하는 명령어

<br>
<br>

### Redis를 사용하는 이유
* 데이터를 아주 빠르게 저장하고 가져올 수 있음
  * 모든 데이터를 메모리에 저장함
  * 모든 데이터를 아주 단순한 구조로 관리함 : 데이터가 HashMap, List 등과 같은 고전적인 자료 구조로 관리됨
  * 의도적으로 아주 단순한 기능만 갖추고 있음

<br>

### 초기설정
* https://redis.com/
* 회원가입 - 무료 free 플랜 생성 + DB생성
* Redis 명령어 실행 방법
  * rbook.cloud 이용하기
  * rbook을 로컬에서 사용하기
  * Redis-CLI 사용하기

<br>

### 기본 명령어
* 명령어 : 정보를 저장하기 이해 데이터베이스에 전송하는 문자열
* SET, GET 다음의 단어를 ket나 변수명으로 저장함
  * ```SET message 'Hi there!'```  key: message / value : 'Hi there!'
    * ```GET message```
* 일반적으로 사용되는 데이터 타입 : String, List, Hash, Set, Sorted Set, Bitmap, Hyperloglog, Json, Index
* 각 명령어 유사한 것을 묶어서 이해하면 좋음 (블로그에 표로 정리하기)
* 참고하면 좋은 공식문서 redis.io/commands
* 공식문서 이해하기
  * Filter by group으로 사용하고자 하는 데이터타입 선택하기
  * 명령어 선택하기
  * 헤딩 텍스트 이해하기 

<br>

### 문자열 기본 명령어
* ex) SET key value ```SET key value [NX | XX] [GET] [EX seconds | PX milliseconds | EXAT unix-time-seconds | PXAT unix-time-milliseconds | KEEPTTL]```
  * 대문자 : 명령어 
  * 소문자 : 개발자가 원하는 값
  * 대괄호 안의 단어 : 선택사항, 선택적인 인후 -> 공식문서 참고
  * 기호 | : OR의 의미
* SET 변형 명령어
  * SET + key + value + 키를 설정할 조건 명시 + 키를 설정했을 때 이전에 저장되어 있던 값 반환 +  만료시간
  * SET + key + value +     [NX | XX]      +            [GET]                      +  만료시간
* 중간에 공백있는 문자열 저장시 ""로 감싸기
 
<br>

### 키 설정 조건
* [NX | XX] 
* NX (Not eXists) 
  * 해당 키가 존재하지 않을 때만 값을 설정
  * 이미 키가 존재하면 명령어는 아무런 동작을 하지 않음
* XX (eXists)
  * 해당 키가 이미 존재할 때만 값을 설정
  * 키가 존재하지 않으면 명령어는 아무런 동작을 하지 않음

 
<br>

### 만료 옵션 사용 사례
* [EX seconds | PX milliseconds | EXAT unix-time-seconds | PXAT unix-time-milliseconds | KEEPTTL]
* Redis 데이터베이스에 저장한 데이터가 만료되어 자동으로 삭제될지 여부 결정하는 선택적 인수
  * EX (EXpire)
    * 설정된 시간(초 단위) 후에 키가 만료되도록 함
    * 이 옵션을 사용하면 키에 대한 값이 자동으로 삭제됨
  * PX (Pexpire)
    * EX와 유사하지만, 시간 단위가 밀리초
  * EXAT
    * 키가 만료될 UNIX 시간(초 단위)을 지정
    * 이 시간에 도달하면 키는 자동으로 삭제됨
  * PXAT
    * EXAT와 유사하지만, 시간 단위가 밀리초
  * KEEPTTL (KEEP Time To Live)
    * 이 옵션을 사용하면 현재 키의 TTL(Time To Live, 만료 시간)을 유지
    * 키에 새 값을 설정할 때 기존의 만료 시간을 그대로 유지하고 싶을 때 유용함
* 만료시간 사용 이유
  * Redis의 근본적인 용도 : Redis는 본래 캐싱 서버로 설계되었음 (특정 데이터를 일정 기간 동안 저장한 다음 필요하지 않게 되면 삭제)
  * Redis 데이터베이스에 캐싱한 값이므로 공간을 확보하기 위해 자동으로 삭제하는 것
  * Redis의 근본적인 용도가 캐싱 서버이기 때문에 오래된 데이터를 캐싱해 유지하는 것을 막고 메모리가 부족하지 않도록 데이터를 삭제하는 것
* Redis의 여러 문제를 해결하는 다양한 창의적인 사용 사례가 있지만 대표적인 이유는 캐싱


<br>

### SETEX, SETNX, MSET, MSETNX
* SETEX = SET + EX
* SETNX = SET + NX
* MSET
  * 여러 키 쌍 저장 동시 거장 가능함
  * SET을 반복해서 호출하는 것과 동일한 기능
  * ```MSET color red onekey twokey```  -> 꺼내기 ```GET onekey``` => red,  ```GET twokey``` => red
* MSETNX = MSET + NX 


<br>

### GET과 MGET
* GET + 가져올 키의 이름
* MGET : 하나의 명령어에서 여러 키의 값을 가져올 수 있음
  * ```MSET onekey twokey``` ->  "red", "red"

<br>

### 문자열 범위
* DEL
  * 데이터베이스에 존재하는 키와 저장된 데이터를 삭제함
  * 문자열 뿐만 아니라 모든 자료형에 사용할 수 있음
* GETRANGE 
  * 데이터베이스 내 존재하는 문자열에서 특정 범위의 문자만 가져올 수 있음
  * 인덱스는 0부터 시작
  * model에 'black'이 저장되어 있을 때 : ```GETRANGE model 0 2``` => "bla"
* SETRANGE
  * GETRANGE의 반대
  * 기존 문자열의 일부를 업데이트할 수 있음
  * model에 'black'이 저장되어 있을 때 : ```SETRANGE model 2 ooo``` => 인덱스 2부터 대체함 "blooo" 

<br>

### 명령어 응용하기
* 예시
  * 자주쓰는 가구 Map data가 있을 때 => 가구 제품의 목록을 인코딩된 데이터셋으로 변환하는 것
  * (문자열 전체를 저장하는 대신 간단한 값만 인코딩)
  * 그리고 그리고 여러 가지 속성을 인코딩한 문자를 합쳐서 문자열로 저장 => item1 : aqg / item2 : qop / item3 : cir
  * 가능한 정보를 압축해 단일 문자로 값을 인코딩 할 수 있음!!


<br>

### 숫자 다루기
* 문자열에 사용하던 것과 거의 동일한 명령어를 숫자를 설정하고 가져오는 데 사용할 수 있음
* SET, GET, MGET, MSET 등을 동일하게 사용할 수 있고 SET 명령어의 옵션도 동일하게 적용
* 숫자를 다룰 때는 숫자만을 처리하는 데 사용되는 추가 명령어 있음
  * DECR, DECRBY, INCRBY, INCR, INCRBYFLOAT
  * 문자열 키에 사용 X => 사용 시 Redis가 오류 메시지를 반환함
* INCR
  * 기존의 숫자에 1 더함
  * ```SET age 20```  -> ```INCR age``` -> 21
* DECR
  * 기존의 숫자에서 1 뺌 
  *  ```SET age 20```  -> ```DECR age``` -> 19
* INCRBY
  * 기존의 숫자에 더함 (값이 없으면 기본 1)
  * ```SET age 20```  -> ```INCRBY age 20``` -> 40
* DECRBY
  * 기존의 숫자에서 뺌 (값이 없으면 기본 1)
  *  ```SET age 20```  -> ```DECRBY age 10``` -> 10
* INCRBYFLOAT
  * 소수점이 있는 플로트 값을 처리
  * INCRBYFLOAT에 키 이름과 함께 소수점이 있는 숫자를 전달
  * 뺄샘 하려면 음수를 전달하기
  *  ```SET age 20```  -> ```INCRBYFLOAT age 6.400145``` 
  *  ```SET age 20```  -> ```INCRBYFLOAT age -6.400145``` 


<br>

### 숫자 명령어의 존재 이유
* INCR 명령어
  * [1] 값을 확인
  * [2] 값을 수정하고 다시 저장
  * ex) 블로그 게시물, 이미지 또는 댓글의 추천 수 증가
* GET, SET을 이용하는 경우의 단점 
  * 데이터 베이스에 2번 접근해야 함(API서버에서 Redis에 요청을 2번 해야함)
  * 특정 게시물을 추천하는 요청이 API 서버에 동시에 들어왔을 경우(엄청난 양의 요청이 들어오는 가끔의 경우) 잘못된 없데이트 될 수 있음
* 위 경우의 해결방법 3가지
  * 특수한 명령어인 Watch를 통한 Redis 트랜잭션을 사용하기
  * 락을 사용하기
  * INCR 명령어를 사용하기(한가지 명령어 사용)
* **중요**
  * Redis가 기본적으로 동기식으로 작동하며 다양한 요청을 동시에 처리할 때 동시성을 고려해야 한다는 것 기억하기!!
  * 여러 API에서 동시에 데이터를 처리하며 항상 동시성에 대해 생각해야함


