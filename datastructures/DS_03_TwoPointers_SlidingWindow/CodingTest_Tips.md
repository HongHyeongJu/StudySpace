
<br>

## 투 포인트 알고리즘, 슬라이딩 윈도우 


- 두 집합 공통원소 추출하기  / 손코딩 나올 수도 있음
- int[] aArr, intp[] bArr
- 먼저 두 집합을 오름차순 정렬한다 Arrays.sort(aArr)   Arrays.sort(bArr)
- aArr[p1] 와 bArr[p2] 비교해서 "같으면" answerArr에 add, 두 포인터 동시에 증가시키기 p1++, p2++
- aArr[p1] < bArr[p2] 이면 작은쪽 포인터를 증가  p1++


<br>
<br>

- 연속된 3일단의 최대 매출 찾기 : 슬라이딩윈도우 -> 투포인터가 같은 간격으로 이동
- 2중 for문 돌면 안됨. 
- for문 시작 전에 창문크기 k만큼 sum먼저 구해두기. answer도 그 값으로 초기화 해두기
- ex) sum = 38;  answer = 38;
- 창문 미는 방법 : sum(38) + arr[i] - arr[i-k]    이 값과 answer비교해서 큰 값으로 대입.
- 사전준비  for(int i=0; i<k; i++) sum +=arr[i];   answer = sum;
- 창문밀며 합구하기   for(int i=k; i<n; i++) { sum =+ (arr[i] - arr[i-k]);   answer = Math.max(answer, sum); }

<br>
<br>

- 연속 부분 수열
- 연속된 숫자 N개가 주어질 때 합이 M이 되는 경의의 수 구하기
- 2중 for문 돌면 안됨. n^2 -> n으로 구해야함

```Java
- 예제  1 2 1 3 1 1 1 2  있을 때 M되는 경우
       (lt)
       (rt)
```
- 먼저 lt부터 rt의 값을 구하고(sum) ==M인지 확인
- rt증가하기 / 증가한 곳의 값을 sum에 누적 / sum==M인지 확인 
- 같지 않으면 rt증가 -> sum에 누적 -> 확인 (반복)
- sum(7)  M(6) 처럼 sum이 커져버렸을 때 
- lt값 sum에서 빼기, lt 증가 -> sum과 M비교
- sum과 M 같으면 cnt++ 
- sum - arr[lt] 이렇게 값 빼고 lt++
- 확인. sum < M 이면 이번에는 rt증가하고 누적

<br>

- (sum이 작다) rt는 증가 / arr[rt]값 sum 누적 / M과 비교     sum==M : rt증가 / sum과M비교 / sum이 커져서 lt증가 로직으로
- (sum이 크다) sum에서  arr[lt]값 빼기 / lt 증가 / / M과 비교        sum==M : lt현재의 값 sum에서 빼고 / lt증가 / sum과M비교 / sum이 작아져서 rt증가 로직으로

```Java
int answer=0; sum=0; lt=0;
for(int rt=0; rt<n; rt++) { 
     sum += arr[rt];
     if(sum == M) answer++;
     while(sum>=M){
	sum-=arr[lt++];  //빼고 -> lt증가 후위증감법!!
	if(sum==M) answer++;
     }
}
```

<br>
<br>

## 연속된 자연수의 합
### [투포인터로 풀기]
- 15 만들기 7+8, 4+5+6, 1+2+3+4+5
- 자연수는 목표숫자의 /2 +1까지만 있으면 됨. 15의 경우 8까지

- rt는       rt++하고           /  sum에 arr[rt] 더하기
- lt는   sum에서 arr[lt] 빼고 /      lt++


### [수학으로 풀기]
- 15가 있을 때  1 + 2 를 뺌. 그러면 15-13=12 이고 /2로 나누어 떨어짐. 1+2에 각각 6을 더해줌 그러면 7+8
- 1+2+3 을 선제시, 합인 6을 15에서 뺌 = 9. 9을 3개로 나눌때 딱 떨어짐. 각각에 3을 더해주면 4+5+6
- 1+2+3+4  이것의 합을 15에서 뺌. 15-10=5 안됨
- 1+2+3+4+5 15에서 빼면 0 딱떨어짐.끝

```Java
M(목표숫자, 15)
int answer=0; cnt=1  //cnt는 1,2,3,4,5 이렇게 변화할꺼임
M--;  //(15-1)
while(n>0){
	cnt++;   // 1+2의 2를 만들어줌
	M=M-cnt   // 15-1에서 -2를 해줌
	if(M%cnt==0) answer++  // 숫자 개수인 cnt로 나눌 때 딱 떨어지면 ++
}  //while문이 다시돌면서 1+2+에 cnt++;로 인해서 +3을 해주고 / M에는 -3 해주는 것을 반복
```

<br>
<br>

### 최대 길이 연속부분 수열
- 1 1 0 0 1 1 0 1 1 0 1 1 0 1
- rt는 0을 만나면 1로 바꾼다. 최대횟수인 K를 넘으면 안됨. 
- 바꾼 횟수cnt > 최대횟수k 를 "안" 만족하는 경우 rt계속 이동
- 바꾼 횟수 > 최대횟수 조건 성립 하자마자(rt가 바꾸고 확인하는거 중요) lt이동
- lt는 이동하면서 rt가 바꾼 1을 0으로 변경함(cnt--). cnt바꾸고 lt증가!! 그냥 1인거면 그냥 이동. 
- lt와 rt의 차이가 제일 큰걸 찾으면 되겠다. rt가 이동할때 마다 연속수열 길이 구하기!  길이 answer 구하기 rt-lt+1
- answer은 커야만 갱신
- 0을 1로 진짜 바꾸면 안됨. 확인할 뿐.

```Java
int answer=0, cnt=0, lt=0;
for(int rt=0; rt<n; rt++){
	if(arr[rt]==0) cnt++;
	while(cnt>k){
	      if(arr[lt]==0) cnt--;
	      lt++;
	}
	answer=Math.max(answer, rt-lt+1);
}
return answer;
```
