## 유데미 Redis : 개발자를 위한 Redis 완벽 가이드
### ch.20 - 검색 구현하기

<br>
<br>


### 검색구현
* 상황에 따라 선택하기
  * 해시의 어떤 필드에 대해서 검색을 실행할 것인가
  * 풀 텍스트 검색을 원한다면 필드타입은 텍스트로
  * 실제로 그 인덱스를 생성하는 함수 작성하기
  * 인덱스 생성 시점을 결정하기



<br>


### 인덱스 생성 함수
* 강의는 Node.js를 이용하기 때문에 필요한 부분만 필기
  * 인덱스를 만들 때 접두어에 오탈자 방지를 위해서 함수를 이용하기(자바는 메서드겠지)
  * ex) public String itemsKey(Strting keyword){return "ietems#"+keyword; }

<br>


### 인덱스 생성 시점
* 애플리케이션이 시작되고 처음으로 Redis에 접속할 때 우리는 Redis를 확인하고 이미 존재하는 모든 인덱스로 된 리스트를 받을것임
* 이후 만들려는 인덱스가 이미 생성되어있는지 확인
  * 생성X -> 만들기
  * 생성O -> 아무것도 안하기
* ```FT._LIST```
  * 모든 인덱스로 된 리스트를 받는 명령어


<br>


### 검색어 파싱하기
* 강의에서 구현한 함수
  * 매개변수 2개 (사용자의 입력값, 반환할 최대 검색결과 개수)
* 입력갑 전처리
  * 특수기호 삭제하기 : 정규식 이용, 
  * 양끝 공백제거
  * 중간공백 기준으로 나누기
  * 공백 사이에 단어가 있으면 % 퍼센트 기호 추가하기, 없으면 빈문자열로 치환
  * AND연산 할 것인지, OR연산 할 것인지 정하기




<br>


### 검색 실행하기
* 강의에서 구현한 함수 필기
  * 정리된 결과를 보고 유효한지 확인하기
    * 기호제거로 인해 빈문자열이 된다면 DB의 모든 항목 리턴 -> 유효X
    * 빈문자라면 빈 배열 리턴하기 == 찾을 수 없다
  * 실제 검색 수행하기
  * 역직렬화 하고 검색 결과를 리턴하기



<br>


### 모의 데이터 보충하기
* 데이터가 많으면 검색 작업 최적화 작업을 훨씬 잘할 수 있음
* 그 이후로  Node.js 설명


<br>


### RediSearch와 TF-IDF
* 검색 시 설명이 아닌 이름에 검색어가 있는 항목이 우선시되도록 하기
* RediSearch의 알고리즘을 작동을 이해하면됨
* TF-IDF 알고리즘
  * 이 알고리즘을 이용하여서 다양한 문자열들을 살펴보고 주어진 검색어에 대해 어떤 것이 관련깊은지 결정할 수 있음
  * TF-IDF는 검색어 빈도를 역 문서 빈도에 곱해서 작동함
  * 곱하면 각각의 문자열에 점수가 매겨지고 점수가 높으면 그 문자열이 주어진 검색 결과와 더 관련이 깊다는걸 의미함
  * 검색어 빈도는 문자열 안에서 어떤 검색어가 몇번이나 등장하는지 확인하고 그걸 문자열 안의 총 단어 개수로 나눠서 계산하는 등의 계산 방식이 있음
  * 예를 들어 검색어들 사이에 단어가 몇 개 있는지, 혹은 우리가 검색하는 단어가 문자열에 몇 개 있는지 고려함
  * 개별 검색어/필드에 가중치를 추가할 수도 있음


<br>


### 필드에 가중치 적용하기
* 필드에 가중치 적용하기 위해서 쿼리를 업데이트 하기
  * 이름에 검색어 1번 < 설명에 검색어 반복 n번 : 검색 의도에 맞지 않음 
  * ex) 개별 해시의 name필드를 검색하고 chair를 찾고, 찾으면 5배의 가중치를 적용하라
  * AND는 OR로 변경 




<br>


### EXPLAINCLI를 이용한 쿼리
* EXPLAINCLI
  * 실제로 쿼리를 실행하진 않지만 실제로 어떻게 실행될 것으로 생각하는지에 관란 정보를 줄 것임(옵티마이저의 실행계획 같은건가)
  * 개발자의 쿼리 의도에 맞게 해석되는지 확인하기 위해 사용함
  * +chair(expanded)와 같은 RediSearch의 스테밍을 고려해야함
  * 각 항에 괄호를 추가해야함
    * FT.EXPLAINCLI idx:items '(@name:(chair))|(@description:(chair))'




<br> 


### PROFILE로 쿼리 성능 확인하기
* FT.PROFILE
  * 쿼리를 실행할 수 있고, Redis는 내부적으로 그 쿼리를 실행하는데 걸리는 시간을 측정함
  * 쿼리 명령의 구문들을 약간 시험해볼 수 있음
  * ```FT.PROFILE idx:items SEARCH QUERY 'chairs' LIMIT 0 0```



<br>


### 정렬과 검색
* RediSearch와 정렬에 관한 주의사항
  * RediSearch 자체로 검색 결과를 정렬할 수 있음(sort 필요 없음)
  * 대신 일반적인 검색을 하고 쿠리의 일부로서 어떤 필드로 그 결과를 정렬해 달라고 하는 것
  * 정렬을 이용하기 위해서 인덱스를 업데이트 해야함(구제척으로 정렬에 사용할 필드 알려주기)
  * 제한
    * 한 번에 한 필도르만 정렬할 수 있음
    * 한 번에 하나의 정렬 결과만 얻을 수 있음
  * 인덱스를 생성할 때 정렬을 하려면 모든 필드중 ```정렬이 가능하게 하려는 필드```, ```그렇지 않은 필드```를 밝혀야함
* 인덱스 변경하려고 할때마다 필드를 추가할 수만 있음. 필드 삭제 X, 기존 필드 변경 X
* 따라서 인덱스를 삭제하고 다시 생성해야함


<br>


### 기존 인덱스 업데이트 하기
### 검색 실행하기
* Node.js 코드 내용




<br>


### 검색결과 역직렬화 하기
* 검색을 실행하면 총 결과 개수인 total과 발견한 모든 항목 해시로 된 배열이 반환됨
  * 실제로 객체들로 이루어진 배열
  * 모든 객체에는 id가 있음
  * 그 id는 항목 해시가 저장된 곳의 키
  * value는 항목 해시 자체
* 객체를 분해해서 id와 value로 할당하기

