<br>

# HashMap, TreeSet
- 자주쓰는 메서드
- 문제풀이의 실마리, 사고과정 정리하기

<br>

### HashMap의 메서드
- map.put(key, value);
- 카운팅 할 때 자주 쓰는 메서드
- map.getOrDefault(찾을 값의 키, 키없으면 반환할 값)    
- ex) map.put( key, map.getOrDefault(key,0)+1 )  맵에서 key로 값을 가져오는데, 값이 있으면 value++; key로 저장된게 없으면 (key , 0) 으로 저장

- 특정키가 있는지 확인하는 메서드 map.containsKey('A'); -> true / false 리턴해줌

- key의 종류개수 map.size()

- 특정 키 삭제하기 map.remove('A');


<br>


### 애너그램
- 한 단어를 재배열하면 상대편 단어가 될 수 있는 것
- Map에 각 문자를 key로, 알파벳의 개수를 value로 
- key가 있으면 확인 후 value--, 개수가 다르면 해당 key의 값이 0, key 존재 여부 확인
- for each문으로 접근 할 때 str.toCharArray()


<br>

### 매출액의 종류
- 정해진 크기의 구간으로 배열 만들기, 그 안의 중복은 없이 종류 개수 찾기
- 투포인터 + 슬라이딩윈도우 + HashMap
- 그림 그리는게 중요하네

<br>

### 모든 애너그램 찾기
- 투포인터 + 슬라이딩윈도우 + HashMap
- Map의 key와 value 모두 비교해주는 메서드 aMap.equals(bMap);
- key가 없어서 value가 0이 되면 remove 해주는 것이 핵심
- String aStr (abcbAbsb 이런거),  String bStr (abc. 찾을 단어. 애너그램으로)
- 슬라이딩 윈도우는 사전작업이 필요하다
- 사전작업) 찾을 단어로 맵 구성 bMap.put( x , bMap.getOrDefault(x,0)+1 );  
- (x는 bStr을 char[]로 변환해서 foreach할 때의 변수)
- 사전작업) 윈도우 길이 int len = bStr.length()-1;
- 사전작업) 윈도우의 길이-1 만큼 처음 단어로  Map만들기
- 길이만큼 for문 돌면서  aMap.put( aStr.charAt(i), aMap.getOrDefault( aStr.charAt(i),0 )+1 );  
- int lt=0;
- rt 시작을 len으로 하며 put하고 비교 -> 같으면 카운트++
- lt의 키값 1빼고, 그로 인해서 0이되면 remove, 그뒤에 lt++
<br>

### K번째 큰 수
- 1부터 100 적힌 카드에서 3장 뽑아서 합 만들기
- K번째로 큰수. 같은 수는 중복허용 안돼서 제외하고 생각하기 -> Set을 이용하기
- 정렬된 Set이 필요하기 때문에 TreeSet사용
- 내림차순이 필요하기 때문에 TreeSet 생성시 매개변수로 정렬기준 Collections.reverseOrder() 제공하기
- 3장의 합 -> 3중 for문. 카드 중복 안되기 때문에 i , j , t 반복문의 시작 번호 주의 i=1 j=i+1 t=j+1
- Set도 향상된 for문 사용 가능
- Set은 인덱스를 통한 직접접근 허용하지 않음. 
- for문 출력하면서 cnt++ 하면서 cnt가 특정 숫자가 됐을 때 return하도록 하면 됨
- [다른방법]
- Optional<Integer> thirdElement = treeSet.stream().skip(2).findFirst(); 
- stream 이용하기
- TreeSet은 NavigableSet 인터페이스를 구현, 이 인터페이스는 첫 번째 요소와 마지막 요소를 검색하는 데 사용할 수 있는 메서드를 제공
- tSet.first(), tSet.last()