<br>

# RDFS, BFS 활용
- 문제풀이 흐름 알기

<br>

### 합이 같은 부분집합
- 부분집합 구하기
````
static String answer="NO";
	static int n, total=0;
	boolean flag=false;
	public void DFS(int L, int sum, int[] arr){
		if(flag) return;
		if(sum>total/2) return;
		if(L==n){
			if((total-sum)==sum){
				answer="YES";
				flag=true;
			}	
		}
		else{
			DFS(L+1, sum+arr[L], arr);
			DFS(L+1, sum, arr);
		}
	}
	...
````


<br>

### 바둑이 승차
- 위와 동일. 부분집함임을 바로 이해하기
- 최대 무게 찾기
- 리턴하는 분기점이 sum>C
- 태운다 태우지 않는다 :)




<br>

### 최대점수 구하기
````
public void DFS(int L, int sum, int time, int[] ps, int[] pt)
````





<br>

### 중복순열
- 앞과 달리 호출이 여러번 일어나기 때문에 사용O/사용X 처럼 2가닥이 아닌 여러 가닥으로 뻗어나감
```
import java.util.*;
class Main{
	static int[] pm;
	static int n, m;
	public void DFS(int L){
		if(L==m){
			for(int x : pm) System.out.print(x+" ");
			System.out.println();
		}
		else{
			for(int i=1; i<=n; i++){
				pm[L]=i;
				DFS(L+1);
			}
		}
	}
	public static void main(String[] args){
		Main T = new Main();
		Scanner kb = new Scanner(System.in);
		n=kb.nextInt();
		m=kb.nextInt();
		pm=new int[m];
		T.DFS(0);
	}
}
```




<br>

### 동전교환 DFS
- 15원을 거슬러주기 위한 최소 개수
- L : level, 동전의 개수
- 큰 동전부터 비교 -> 효율





<br>

### 순열구하기
- pm[L]=arr[i]





<br>

### 조합수 (메모이제이션)
- 조합수 구하기
- nCr=n-1Cr-1 + n-1Cr
- nCr=n!/(n-r)!r!





<br>

### 수열 추측하기
- 순열을 만들어두고 합과 정답 비교
- 1____2___3___4___5
- 4C0 4C1 4C2 4C3 4C4





<br>

### 조합 구하기






<br>

### 미로탐색 DFS
- M개 뽑는 것 = 조합
- 조합은 외워라 응용 많이 함
````
public void DFS(int L, int s){
		if(L==m){
			for(int x : combi) System.out.print(x+" ");
			System.out.println();
		}
		else{
			for(int i=s; i<=n; i++){
				combi[L]=i;
				DFS(L+1, i+1);
			}
		}
	}
````




<br>

### 미로의 최단거리 통로 BFS




<br>

### 토마토 BFS





<br>

### 섬나라 아일랜드 DFS





<br>

### 섬나라 아일랜드 BFS




<br>

### 피자배달거리 DFS



