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
- 상하좌우 움직임
- static int[] dx={-1, 0, 1, 0};
- static int[] dy={0, 1, 0, -1};
- 지나간 길은 큐에 넣고, 지나간 0번 길을 1로 체크해주면서 거꾸로 가는 것을 방지
- 반복문으로 Point tmp=Q.poll(); 꺼내면서
- for(int i=0; i<4; i++) 반복문으로 
````
int nx=tmp.x+dx[i];   
int ny=tmp.y+dy[i];
if(nx>=1 && nx<=7 && ny>=1 && ny<=7 && board[nx] [ny]==0)
    {board[nx][ny]=1;
    Q.offer(new Point(nx, ny));
    dis[nx][ny]=dis[tmp.x][tmp.y]+1;
}
````

<br>

### 토마토 BFS
- 익은 토마토는 하루 뒤 인접한 토마토를 익히는 전염문제
- 위 문제와 유사함
- (추가)static int[][] board, dis;
- (추가)static int n, m;
````
public void BFS(){
		while(!Q.isEmpty()){
			Point tmp=Q.poll();
			for(int i=0; i<4; i++){
				int nx=tmp.x+dx[i];
				int ny=tmp.y+dy[i];
				if(nx>=0 && nx<n && ny>=0 && ny<m && board[nx][ny]==0){
					board[nx][ny]=1;
					Q.offer(new Point(nx, ny));
					dis[nx][ny]=dis[tmp.x][tmp.y]+1;
				}
			}
		}	
	}
````
- 모두 익지 못한 상황을 체크해야함.
- 배열이 2가지 필요함. 익은 토마토, 안익은 토마토, 빈자리 가 담긴 토마토판, 각 자리의 토마토가 며칠 걸려서 익는지 기록하는판
- BFS가 돌기 전에 익은 토마토를 1로 시작지점을 모두 체크해놔야함
- 토마토판에서 0이 팔견되면 안익은 토마토 존재하므로 -1 출력
- 메인에서 접근하므로 같이 쓰는 자원은 static
````
T.BFS();
boolean flag=true;
int answer=Integer.MIN_VALUE;
for(int i=0; i<n; i++){
	for(int j=0; j<m; j++){
		if(board[i][j]==0) flag=false;
	}
}
if(flag){
	for(int i=0; i<n; i++){
		for(int j=0; j<m; j++){
			answer=Math.max(answer, dis[i][j]);
		}
	}
	System.out.println(answer);
}
else System.out.println(-1);
````

<br>

### 섬나라 아일랜드 DFS





<br>

### 섬나라 아일랜드 BFS




<br>

### 피자배달거리 DFS



