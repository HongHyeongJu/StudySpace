<br>

# ch2. 배열 

<br><br>

- 2중 for문은 n^2이
- 피보나치수열


### 소수 구하기는 에라토스테네스 체가 빠르다(제곱근 보다 훨씬)
- 소수 개수 구하는 int prime;
- n+1개만큼 배열 만들기(배열은 0부터 시작하니까 마지막 숫자index배열까지 생성)
- 배열 0으로 채우기
- i는 2부터 시작. 2소수니까 prime++; 2의 배수 인덱스 모두 1로 변경;          [카운팅]  [배수 표시]  [걸러내기]
- i를 3으로 증가. index 3은 0이니까 prime++; 3의 배수 index모두 1로 변경;   
- i가 4일 때 numArr[4]==1 이니까(2의 배수라서 1됨) 패스

- 2중 for문인데 자신의 배수만큼 돌아야 하므로 안쪽 for문은
	for(int j=i; j<=n ; j=j+i)  numArr[j]=1;

```Java
// 숫자 뒤집기     (1)       (2)           (3)
// tmp=1230    t=tmp%10    res=res*10+t     tmp=tmp/10  
//              0          0=0+0           123
//              3          3=0+3           12
//              2         32=30+2	        1
//              1        321=320+1  
```
<br>

### 약수 구하기
```Java
 boolean ifPrime = false;
for(int i=2; i<num; i++){   //2부터 자기자신 전까지의 숫자로 나눴을 때 0이나오면 소수
	if (num%i==0) return false;
}
return ifPrime=true;
```


- 둘 중에 큰 값으로  Math.max(값A, 값B)
- 2차원 배열 대각선  arr[i][i]   arr[i][5-i-1]


- 격자판 상하좌우, 봉우리 찾기
- 평범한 i,j 2중 for문 안에 k for문 넣기 xArr={-1,0,1,0}  yArr={0,1,0,-1}

<br>