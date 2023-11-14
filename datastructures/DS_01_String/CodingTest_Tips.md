# String
<br>
- 헷갈렸던 메서드 / 자주 사용되는 메서드만 체크

<br>
<br>

### 대소문자

#### Strign을 대문자/소문자로 변환

-   str.toUpperCase()
-   str.toLowerCase()

#### char를 대문자/소문자로 변환

-   Character.toUpperCase(c);
-   Character.toLowerCase(c);

#### String에서 대->소 , 소->대 변환

-   String을 char배열로 변환하고 char\[\] = str.toCharArray()
-   향상된 for문을 사용하면서
-   대소문자 boolean 결과에 맞춰서 if(Char)
-   적절히 변환 Character.toUpperCase(x) / Character.toLowerCase(x)
-   변환한 문자열은 String answer에 +=로 누적

#### 아스키 넘버를 사용할 수도 있음

-   A=65, Z=90
-   a=97, z=122
-   둘의 차이는 32
-   소문자면 answer += (char)(x-32) / 대문자면 answer -= (char)(x+32)

<br>
<br>

### 단어 자르기

#### 단어중 가장 긴 단어 찾기. 길이가 같다면 가장 앞에 있는 단어

-   공백 기준으로 잘라서 String\[\] 만들기
-   `String[] strArr = str.split(" ");`
-   첫번째 방법
-   `String answer = ""; int maxLength = 0; String[] strArr = str.split(" "); for(String x : strArr){ int len = x.length; if(len > maxLength) { maxLength= len; //가장 긴 길이 갱신 answer = x; //그 단어로 교체 } }`
-   두번째 방법 : 공백의 인덱스번호를 찾기 -> 그 인덱스 앞까지 자르면 단어

```
String answer = "";
int maxLength = 0;
int pos = 0;
while( (pos = str.indexOf(' ')) != -1 ){
  String tmp = str.substring(0, pos);
  int len = tmp.length();
  if(len > maxLength){
    maxLength= len;  //가장 긴 길이 갱신
    answer = x;      //그 단어로 교체
  }
str = str.substring(pos+1);     
}                                          
//그러나 이경우 가장 마지막 단어는 공백 없어서 비교 못함. 그래서 반복문 바깥에서 한 번 더 확인
if(str.length() > maxLength) answer = str;   
```

<br>
<br>

### 단어 뒤집기, StringBuilder

#### 단어 뒤집기

-   StringBuiler의 뒤집는 메서드

```
String tmp = new StringBuilder(x).reverser.toString();
```

-   String를 하나의 char\[\]로 변환해서 맨앞인덱스&맨뒤인덱스 값 교환하기
-   인덱스는 startIndex++; endIndex--; 그리고 둘이 같으면(차이나지않으면) 멈추기

```
for(String x : str){
    char[] s = x.toCharArray();
    int startIndex = 0;
    int endIndex = x.length()-1;
    while(startIndex < endIndex){
        char tmp = s[startIndex];
        s[startIndex] = s[endIndex];
        s[endIndex] = tmp;
        startIndex++;
        endIndex---;
    }
    String tmp = String.valueOf(s);
    andswer.add(tmp);   //빈 ArrayList answer에 담기
}
```

-   파이썬에서는 가장 왼쪽문자 lt, 가장 오른쪽문자 rt라고 한다고 해서 나도 이제 변경함

<br>
<br>

#### 특정 문자 뒤집기

-   Q.영어 알파벳과 특수문자 구성된 문자열이 주어지면, 영어 알파벳만 뒤집기
-   예시 (lt)a # b ! G E \* T @ S(rt)
-   lt와 rt가 둘 다 알파벳일 때 교환 = lt와 rt가 둘 다 특수문자가 아닐 때
-   if의 조건을 lt 특수문자? -> 특수문자면 그냥 증가. 아니면 else로 rt 비교
-   `String answer; char[] sArr = str.toCharArray(); int lt = 0; int rt = str.length()-1; while( lt < rt ){ if(!Character.isAlphabetic( sArr[lt] )) lt++; else if(!Character.isAlphabetic( sArr[rt] )) rt--; else { char tmp = s[lt]; s[lt] = s[rt]; s[rt] = tmp; lt++; rt--; } } answer = String.valueOf(sArr);`

<br>
<br>

### 중복 문자 제거

#### indexOf(char) 이용하기

-   indexOf는 그 문자를 가장 먼저 찾은 인덱스를 반환
-   for문과 str.charAt(i) 문자를 하나씩 순회하는데
    -   인덱스i와 str.indecOf(str.charAt(i))가 같으면
    -   그 문자는 첫음으로 발견된 것
    -   인덱스i와 str.indecOf(str.charAt(i))가 다르면
    -   그 문자는 첫번째 문자가 아닌것 = 중복이란 의미
-   같을 때만 문자 누적하면 됨

<br> 
<br> 

### 회문 문자열, 팰린드롬

#### GOOG, POOP 과 같이 거울처럼 생긴 문자

-   앞에서 말한 것처럼 lt와 rt가 문자열 길이의 1/2만큼 반복할 때까지 같아야 함
-   반복문은 for(int i=0; i< len/2 ; i++)
-   lt = i;
-   rt = len -i -1;

```
str = str.toUpperCase();   // 대소문자 구분 안하기로 했을 때
int len = str.length();
for(int i=0; i< len/2 ; i++){
    if(str.charAt(i) != str.charAt(len -i -1) ) return "NO";
}
```

-   StringBuilder을 이용하기 .equalsIgnoreCase()

```
String tmp = new StringBuilder(str).reverse().toString();  //원래 문자열, 뒤집은 문자열 비교
if(str.equalsIgnoreCase(tmp)) answer="YES";
```

 <br> 
 <br> 

#### 팰린드롬 = 앞에서 읽을 때나 뒤에서 읽을 때나 같은 문자열

-   이번 문제의 조건은 알파벳만 검사함. 특수문자나 숫자는 상관없음
-   replaceAll()에 사용한 정규식. ^는 부정 \[A-Z\]는 대문자 -> 합쳐서 "영어대문자가 아니면"

```
str = str.toUpperCase().replaceAll("^[A-Z]", "");
String tmp = new StringBuilder(str).reverse().toString();
if( str.equals(tmp) ) answer = "YES";
```

<br> 
<br>  

### 숫자만 추출

#### 문자와 숫자가 섞여있는 문자열로 자연수 만들기

-   문자와 숫자가 섞여있는 문자열이 주어지면 숫자만 추출해서 그 순서대로 구성된 자연수 만들기
-   예시 tge0a1h205er -> 01205 ->1205
-   아스키문자 '0'=48, '9'=57
-   **answer = answer \*10 + (x - 48)**
-   \*10을 하는 이유: 자릿수를 늘리기 위해서... 1 -> 10 + 2 -> 120 + 0 -> 1200 + 5
-   아.. 이런걸 외우라는 거구나

```
int answer=0;
for( char x : str.toCharArray() ){
    if( x>=48 && x<==57 ) answer = answer*10 + (x-48);
}
return answer;
```

-   또 다른 방법 Character.isDigit(x) -> 이것이 숫자인지 판별
-   `String answer = ""; for( char x : str.toCharArray() ){ if( Character.isDigit(x) ) answer += x; } return Integer.parseInt(answer);`


<br> 
<br>   

### 가장 짧은 문자 거리

#### 문자열str의 각 문자가 타겟문자 target와 떨어진 거리를 순서대로 출력하기

-   teachermode e
-   각각의 문자가 e로 부터 떨어진 거리를 순서대로 출력하기
-   배열 왼쪽부터 임의의 숫자 P를 제시하고 타겟 숫자와 같으면 0 , 아니면 1 증가시키면서 옆으로 이동하기
-   P의 초기화는 터무니 없는 숫자로
-   \[1001\]\[0\]\[1\]\[2\]\[3\]\[0\]\[1\]\[2\]\[3\]\[4\]\[0\] -> 타겟 문자 자신은 0, 아니면 옆으로 1 증가. 다시 자신이면 0
-   최소 거리를 구해야 하므로 오른쪽에서 for문이 한 번 더 돌아야 함.
-   이미 구한 값과 비교하면서 새로 구한 값이 더 작으면 수정

```
int[] answer = new int[str.length()];
int p = 1000;
for(int i=0; i<str.length(); i++){
    if(s.charAt(i)==target){
        p=0;
        answer[i]=p;
    } else {
        p++;
        answer[i]=p;
    }
}
p=1000; //p 다시 초기화. 오른쪽부터 다시 반복문 돌아야 하기 때문
for(int i=str.length(); i>0 i--){
    if(s.charAt(i)==target){
        p=0;
    } else {
        p++;
        answer[i]=Math.min( answer[i], p);  //양방향 비교로 얻은 값 중에서 작은 것을 대입하기
    }
}
```

<br> 
<br> 

### 문자열 압축

#### 같은 문자 반복 찾기

-   같은 문자가 연속으로 반복되는 경우 반복되는 문자 바로 오른쪽에 반복 횟수를 표기하는 방법으로 문자열 압축하기
-   반복횟수 1은 적용하지 않음
-   예시 KKHSSSSSSSE
-   i와 i+1을 비교. 같으면 cnt증가, 다르 지점을 만나면 String answer에 해당 문자, cnt를 누적하면 됨.
-   cnt를 1로 초기화 하고 다시 비교하며 지나가기
-   배열 맽 끝문자가 없어 예외 생길 수 있기 때문에 맨 끝에 빈 문자열을 추가해줘야함.

```
String answer = "";
str = str + " ";
int cnt = 1;
for( int i=0; i<s.length()-1 ; i++ ){
    if( s.charAt(i)==s.charAt(i+1) ) cnt++;
    else{
        answer += s.charAt(i);  //문자 추가
        if(cnt>1) answer += String.valueOf(cnt);  //문자 반복 횟수 추가
        cnt=1; //cnt초기화
    }
}
return answer;
```

<br> 
<br> 
  

### 암호

#### 특수문자 -> 이진수 -> 십진수 -> 아스키코드 변환

-   샵별별별별별샵을 일곱자리 이진수로 변환 -> 1000001 -> 10진수화하면65 -> 아스키코드 65 = A
-   예제 샵별별별별샵샵샵별별샵샵샵샵샵별별샵샵샵샵샵별별샵샵별별
-   인덱스0부터 7까지 문자열 자르기 str.subString(0, 7)
-   샵은 1로, 별은 0으로 치환하기

```
for( int i=0; i< n ; i++ ){
    String tmp = str.subString(0, 7).replace('#','1').replace('*','0');
    int num = Integer.parseInt(tmp, 2);  //2진수 문자열을 10진수 int로 변환하기
    answer += (char)num;  //정수를 아스키코드에 해당하는 것으로 바꾸기 (이경우 67 -> C)
    str = str.subSring(7);  //이미 확인한 7개 빼고 그 이후만 남게 String 자르기
}
```

<br> 
<br> 

코딩테스트 문자열 핵심

- String 메서드 (대소문자, 구분자로 자르기, index찾기)
- String의 char[] 변환
- char 메서드 (대소문자 t/f, 변환)

- StringBuilder의 메서드 (뒤집기, 불변인 String보다 유용함)