## 유데미 Redis : 개발자를 위한 Redis 완벽 가이드
### ch.15 - 리스트에 데이터 저장하기

<br>
<br>


### 리스트
* 단점부터 알기
  * 제대로 쓰지 않으면 성능이 안좋음
* 사용처
  * 리스트는 문자열을 순서대로 저장하는 데 사용하기
* 주의점
  * 리스트는 배열이 아님. 보통의 배열과는 다르게 구현됨
  * 사실은 이중 연결 리스트로 구현됨
* 리스트가 흔히 사용되는 곳은 시계열 데이터
  * ex) 하루에 한 번 기록하는 기온
  * 항목을 추가할 때 유용함 (제일 끝이나 제일 앞에 추가할 때)
  * 이 항목들을 전부 순회하려고 할 때나, 가장 중앙에 있는 항목에 접근할 때 문제상황에 당면함
* Redis 명령어 => 요즘 잘 쓰지 않음, 또한 Redis에 새 자료형이나 명령어가 추가되며 더욱 안쓰이게됨

<br>


### 무용한.. 리스트 명령어
* LPUSH
  * 리스트의 왼쪽에 항목 추가
  * ```LPUSH temps 25```
* RPUSH
  * 리스트의 오른에 항목 추가
* LLEN
  * list Length를 의미함
  * 리스트에 있는 항목 개수를 알려줌
* LINDEX
  * 명시된 인덱스에 저장된 값을 반환함
  * 음수 명시하면 리스트의 제일 끝으로 감
  * ```-1``` -> 마지막 항목 조회하기
  * 음수 값을 더 아래로 내리면 리스트의 마지막에서 뒷걸음질 하는 것
* 필기


<br>


### 범위와 검색
* LRANGE
  * 리스트에 명시된 범위의 항목을 반환함(인덱스로 조회)
  * 리스트 개수보다 큰 수 넣어도 괜찮음
  * 음수를 사용할 수도 있음
* LPOS 
  * 해당 값이 존재 시 해당 값이 어디 있는지 인덱스를 알려줌
  * ```LPOS key element [RANK rank] [COUNT count] [MAXLEN len]```
  * 리스트에 없는 값을 조회했을 때 반환되는 건 null => ```nil```이라는 용어를 사용한다고함
  * 옵션
    * RANK
      * 리스트 항목은 고유하다는 보장이 없음(중복 허용)
      * LPOS를 할 때 같은 값 여러 개를 찾거나 아니면 같은 값은 무시하고 싶은 경우 RANK를 사용함
      * ex) RANK는 대상 값의 첫 번째나 두 번째나 세 번째 거를 무시하라고 하기
      * ```LPOS temps 25 RANK 2```  두번째로 등장하는 것을 대상으로 하기
    * COUNT
      * 인덱스 배열을 반환함
      * ```LPOS temps 25 COUNT 2``` => 각 25가 위치한 인덱스 정보를 알고싶을 때
    * MAXLEN
      * 데이터가 너무 많을 수 있기 때문에 MAXLEN을 명시해서 검색 깊이를 제한
      * ```LPOS temps 25 MAXLEN 10``` => 첫 10개 항목만 보기
        * ex) temps는 [1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 1] /  ```LPOS temps 2 MAXLEN 8```
        * MAXLEN 8 옵션은 리스트의 처음 5개 요소만 검색하도록 제한하므로 검색 범위는 [1, 2, 1, 1, 2, 1, 1, 2]
        * 반환되는 인덱스 ```2```
        * COUNT 옵션을 생략하면 기본적으로 한 개의 인덱스만 반환됩니다.
        * ```LPOS temps 2 MAXLEN 8 COUNT 3``` 이 명령어의 결과로 반환되는 인덱스는 [1, 4, 7]
* nil
  * Redis에서 '값이 없음' 또는 '존재하지 않음'을 나타내는 특별한 키워드
  * 다른 프로그래밍 언어에서는 null이나 None과 유사한 개념

<br>


### 리스트 자르기
* LPOP
  * 리스트의 왼쪽 즉 시작점을 기준으로 항목을 제거함
  * LPOP을 키 값이랑만 실행시키면 제일 첫 번째 항목이 제거됨
  * 숫자를 명시한 만큼 항목이 제거됨
  * ```LPOP temps 2``` => 처음 2개 항목을 제거함
* RPOP
  * 리스트의 오른쪽(마지막)을 기준으로 항목을 제거함
* LSET
  * 명시된 위치에 있는 항목의 값을 주어진 값으로 바꿔줌
  * ```LSET temps 2 32```를 하면 인덱스 2 위치에 있는 항목의 값을 32로  바꿔줌
* LTRIM
  * 이 명령어에 명시된 범위에 있는 항목은 두고 나머지를 제거함
  * LTRIM을 실행은 매우 조심해야함!! 왜냐면 너무나 쉽게 데이터를 망가뜨릴 수 있기 때문



<br>


### 항목 제거하기
* LINSERT
  * 리스트에 새 항목을 추가하기 (기존 항목을 찾아서 그 항목의 앞이나 뒤에 값을 삽입하게 해줌)
  * ```LINSERT temps BEFORE 30 15```
  * ```LINSERT temps AFTER 30 15```
* LREM
  * 리스트 항목 일부를 제거하게 해줌
  * 2번째로 명시하는 옵션값이 비교적 복잡함
  * ```LREM temps -2 25```
    * 2번째 옵션값
      * ex) -2 : 음수(시작점이 오른쪽, 이동방향은 왼쪽)
    * 맨 오른쪽 끝이 시작점, 왼쪽으로 이동하되 25가 값인 항목 두 개를 제거
* 필기


<br>


### 리스트 유스케이스
* 리스트 데이터 구조를 써야 하는 상황
  * 보통 Sorted set가 적합한 상황이 더 많을 것, 하지만 가끔은 리스트가 적합할 수 있음
  * 바로 데이터를 끝이나 앞에 추가하기만 할 때
  * 지속적으로 데이터를 추가만 하는 그런 상황
  * 수집된 데이터의 마지막이나 마지막 몇 개 기록만 조회하는 시나리오
* 언제 리스트를 쓰면 안 되는지 아는 것도 중요함!
  * 리스트에 항목이 많다면 리스트를 안 쓰는 게 좋음
  * 리스트 항목 조회 시 필터 조건을 적용이 필요할때도 안 쓰는게 좋음
  * 데이터가 정렬되는 기준이 시간이나 삽입된 순이 아닐 때
* 리스트가 적합한 곳
  * 누적 데이터 기록시 문자열 각각 안에 값을 일종의 스키마처럼 인코딩해 넣으면 원하는 정보를 다 꾸겨넣을 수 있음..ㅋ
  * 입찰 기록 누적하기
  * 리스트에는 직렬화된 형태로 저장하기 
  * ```제품의 입찰 가격 : 유닉스 타임스탬프```
  * 이 하나의 레코드를 보고 입찰가랑 입찰된 시간을 알 수 있음
  * 즉 데이터를 파싱해서 이 값들, 즉 숫자, 콜론, 숫자로 돼 있는 이걸 객체로 만들어줘야함

#### 직렬화와 역직렬화
* 나중에 다시 듣기


<br>


### 주제
* 필기
* 필기
* 필기





