<br>

# Recursive, Tree, Graph(DFS, BFS 기초)
- 문제풀이 흐름 알기
- 이론 배운 것 코드 구현 정도의 문제

<br>

### 재귀함수(스택프레임 이해)
- 자기 자신을 호출하는 함수
````
public void 재귀함수( int n ) {
    if(멈출 조건) return;
    else {
        반복 행위
        재귀함수(n-1);
    }
}

````


<br>

### 이진출력(재귀)




<br>

### 팩토리얼




<br>

### 피보나치 재귀(메모이제이션) 중요
````Java


````




<br>

### 이진트리 순회 (DFS)  Depth-First Search
- 그림 그리며 스택에서 쌓이는 함수 생각하기
- 노드는 lt, rt 보유
````
class Node{ 
    int data; 
    Node lt, rt; 
    public Node(int val) { 
        data=val; 
        lt=rt=null; 
    } 
} 
````
- 전위 순회 출력
````
System.out.print(root.data+" ");
DFS(root.lt);
DFS(root.rt);
````
- 중위 순회 출력
````
DFS(root.lt);
System.out.print(root.data+" ");
DFS(root.rt);
````
- 후위 순회 출력
````
DFS(root.lt);
DFS(root.rt);
System.out.print(root.data+" ");
````


<br>

### 부분집합 구하기 (DFS)
- 부분집합의 개수 == 2^원소의 개수
- 첫번째 원소를 루트로 왼쪽은 원소 사용O, 오른쪽은 원소 사용X로 생각하는 것 : 상태트리
- static 변수 선언  static int n; 	static int[] ch;
````
//else 내용
ch[L]=1;
DFS(L+1);
ch[L]=0;
DFS(L+1);
````
````
if(L==n+1){
	String tmp="";
	for(int i=1; i<=n; i++){
        if(ch[i]==1) tmp+=(i+" ");
	}
	if(tmp.length()>0) System.out.println(tmp); //공집합 제외 출력
}
````

<br>

### 이진트리 탐색레벨 (BFS) Breadth-First Search
- 넓이 우선 탐색, 레벨 탐색
- 레벨 순으로 탐색
- 출발점에서 어떤 조건으로 갈 때의 최단 거리
- 값-값 사이의 선을 간선 이라고 함(1조작)
- Queue를 이용함


<br>

### 송아지 찾기 1 (BFS)
```
int answer=0;
int[] dis={1, -1, 5};  // 움직일 수 있는 값  
int[] ch;   //체크
```
[ public int BFS(int s, int e) ]
````
ch=new int[10001];    //10000까지니까 배열은 10001까지
ch[s]=1;      //시작 위치
Q.offer(s);   //큐에 시작 위치 넣기
int level=0;    //루트 노드의 레벨은 0으로 지정
````
#### while(!Q.isEmpty()) 안에서 작업 시작
- 레벨 탐색을 위한 길이 구하기
````
int len=Q.size();  //레벨에 있는 원소 개수
````
- 들어있는 원소 개수만큼 반복문 
- [for i=0; i<len; i++]
````
int x=Q.poll();  //원소 꺼내기
for(int j=0; j<3 ; j++){     
    int next_X = x + dis[j];       //j로 움직일 수 있는 경우 모두 대입하기 int[] dis={1, -1, 5}
    //자식을 찾고 넣어주려고 할 때 답인경우 바로 리턴
    if(next_X==e) return level+1;   
    //만들어지는 3가지 노드 중 조건에 맞는 것만 넣어주기 ex) 음수X, 이미 있는수X 10000 넘는 수X
    if(next_X>=1 && next_X<=10000 && ch[next_X]==0)
    ch[next_X]=1;   //배열 체크
    Q.offer(next_X);   //큐에 넣기
}
그리고 level++;
````
#### while반복문 끝//



<br>

### 트리 말단 노드까지의 가장 짧은 경로
- 최단거리로 가는 경로는 BFS!
- DFS는 제약있음. 포화 이진 트리여야함
 
- [DFS로도 풀어는 보기;;]
````
public int DFS(int L, Node root){ 
    if(root.lt==null && root.rt==null) return L;
	else return Math.min(DFS(L+1, root.lt), DFS(L+1, root.rt));
} 
````
- 진짜 [BFS로 문풀]
````
public int BFS(Node root){ 
	Queue<Node> Q=new LinkedList<>();
		Q.offer(root);
		int L=0;
		while(!Q.isEmpty()){
			int len=Q.size();
			for(int i=0; i<len; i++){
				Node cur=Q.poll();
				if(cur.lt==null && cur.rt==null) return L;
				if(cur.lt!=null) Q.offer(cur.lt);
				if(cur.rt!=null) Q.offer(cur.rt);
			}
			L++;
		}
		return 0;	
    } 
````





<br>

### 그래프와 인접 행렬
#### 무방향 그래프
- 그래프 (노드,엣지) 
- G(V,E) 로 표현
- 무방향그래프==양방향그래프
- O-O 그냥 연결되어 있음
- 인접 행렬(2차원 배열 그래프)로 잡기. 0은 사용 X
- a b 입력받으면 graph[a][b]=1, graph[b][a]=1 체크
- 나중에 1번 정점과 연결된 것 찾고 싶으면 1행에 값이 1인 열(index)만 확인하면 됨
#### 단방향 그래프
- 1 -> 2는 1 2로 입력
- a b 입력받으면 graph[a][b]=1만 체크
- 행 -> 열 로 해석하는 규칙
#### 가중치 그래프
- 행 -> 열로 해석하지만 1이외의 숫자로 비교(비용 등으로 해석할 수 있음)
#### 주의점
- 정점이 많을 때 인접행렬을 하면 메모리 낭비가 크다
- 이럴 때는 인접 그래프를 이용한다.



<br>

### 경로탐색 (DFS) - 인접행렬
- 경로의 조건. 한 번 방문한 노드는 방문하지 않고 가야함
- DFS재귀함수의 매개변수는 방문 노드번호
- 방문했던 곳은 check배열에 체크
- 되돌아갈 때 check 풀어주는 것 잊지말기
- '백 트레킹'
### 경로탐색(인접리스트, ArrayList)
- 위의 인접 행렬은 정점이 많을 수록 성능 나쁨
- 각 정점마다 list를 만들어서 갈 수 있는 번호를 추가함
````
static int n, m, answer=0;
static ArrayList<ArrayList<Integer>> graph;
static int[] check;
````
- main()
```` 
graph = new ArrayList<ArrayList<Integer>>();
for(int i=0; i<=n; i++){
	graph.add(new ArrayList<Integer>());
}
check=new int[n+1];
for(int i=0; i<m; i++){
	int a=kb.nextInt();
	int b=kb.nextInt();
	graph.get(a).add(b);
}
ch[1]=1;
T.DFS(1);
````
````
public void DFS(int v){
	if(v==n) answer++;
	else{
		for(int nv : graph.get(v)){
			if(check[nv]==0){
				check[nv]=1;
				DFS(nv);
				check[nv]=0;
			}
		}
	}
}

````


<br>

### 그래프 최단거리(BFS)
- 최단거리 = BFS!
- 






















