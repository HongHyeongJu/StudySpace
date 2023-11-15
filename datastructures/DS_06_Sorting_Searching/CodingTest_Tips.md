<br>

# Sorting & Searching
- 문제풀이 흐름 알기

<br>

### 선택정렬
- 값 하나를 선택하고 배열 끝까지 돌면서 비교하는 것
- 2중 for문으로 돌 때, arr[i]값을 기준으로 int idx = i로 시작.
- 안쪽 반복문은 j=i+1
- 제일 작은 것을 arr[j] for문으로 찾고나서 arr[idx]값을 arr[i]에 넣어두기
- 값 바꾸기를 위해 빈값 tmp 사용 ㅇㅇ



<br>

### 버블정렬
- 인접한 두 값을 비교하고 옆으로 옮겨가며 2개씩 비교하는 것
- 한 번 바꾸고 나면 맨 뒷자리 숫자가 가장 큰값으로 자리잡음 


<br>

### 삽입정렬
- i는 1부터 시작하고 j는 i-1하고 j--하면서 앞으로 비교하는게 특징
- 변수 범위 주의
```Java
public int[] solution(int n, int[] arr){
		for(int i=1; i<n; i++){
			int tmp=arr[i], j;
			for(j=i-1; j>=0; j--){
				if(arr[j]>tmp) arr[j+1]=arr[j];
				else break;
			}
			arr[j+1]=tmp;
		}
		return arr;
	}
```


<br>

### LPU(캐시, 카카오 변형)
- ArrayList도 가능하지만 배열로 구현하면서 능력 기르기
- 땡겨주기 cache[i]=cache[i-1];
- 이미 꽉 찼을 때는 히트난 하나씩 뒤로 밀기
- 이미 있는 캐쉬가 히트나면 그 자리까지 하나밀고 맨 앞에 넣기
````Java
public int[] solution(int size, int n, int[] arr){
		int[] cache=new int[size];
		for(int x : arr){
			int pos=-1;
			for(int i=0; i<size; i++) if(x==cache[i]) pos=i;
			if(pos==-1){
				for(int i=size-1; i>=1; i--){
					cache[i]=cache[i-1];
				}
			}
			else{
				for(int i=pos; i>=1; i--){
					cache[i]=cache[i-1];
				}
			}
			cache[0]=x;
		}
		return cache;
	}
````



<br>

### 중복확인
- 해쉬맵이 더 좋음
- 정렬로도 가능하다는 것 알고만 넘어가기 
- Arrats.sort(arr)


<br>

### 키순서번호
- 깊은 복사로 tmp배열 만들기 int tmp = arr.clone();
- 한 배열을 정렬하고 두 배열의 값 비교했을 때 다르면 자리 바꾼 사람




<br>

### 좌표정렬
- 좌표정렬 기술 익히기
- Order By colX, colY
````Java
class Point implements Comparable<Point>{ 
	public int x, y;
	Point(int x, int y){
		this.x=x;
		this.y=y;
	}
	@Override
	public int compareTo(Point o){
		if(this.x==o.x) return this.y-o.y;
		else return this.x-o.x;
	}
}
class Main {	
	public static void main(String[] args){
		Scanner kb = new Scanner(System.in);
		int n=kb.nextInt();
		ArrayList<Point> arr=new ArrayList<>();
		for(int i=0; i<n; i++){
			int x=kb.nextInt();
			int y=kb.nextInt();
			arr.add(new Point(x, y));
		}
		Collections.sort(arr);
		for(Point o : arr) System.out.println(o.x+" "+o.y);
	}
}
````         



<br>

### 이분검색
- lt=0, rt=n-1
- mid = (lt+rt)/2;    
- ex) 3 = (0+n)/2;
- 찾아야 할 숫자 M과 mid 비교해서 mid보다 작다면
- rt=mid-1; 로 인덱스 수정
- mid비교해서 작으면 rt수정 , 크면 lt수정
- while(lt<=rt) 찾을 때까지 반복
````Java
	public int solution(int n, int m, int[] arr){
		int answer=0;
		Arrays.sort(arr);
		int lt=0, rt=n-1;
		while(lt<=rt){
			int mid=(lt+rt)/2;
			if(arr[mid]==m){
				answer=mid+1;
				break;
			}
			if(arr[mid]>m) rt=mid-1;
			else lt=mid+1;
		}
		return answer;
	}
````





<br>

### 결정 알고리즘 - 뮤직비디오 DVD 크기
- 요구하는 정답이 lt와 rt 사이에 있다는 확신이 있을 때 사용할 수 있는 문제
- DVD중 가장 큰 값이 lt
- rt는 모든것을 합한 값 
- mid = (lt+rt)/2
- 결정 알고리즘은 더 좋은 답을 위해서 좁혀가는 것
- rt = mid-1 조건에서 다시 확인
- Arrauy.stream(arr).max().getAsInt();
````Java
class Main {
	public int count(int[] arr, int capacity){
		int cnt=1, sum=0;
		for(int x : arr){
			if(sum+x>capacity){
				cnt++;
				sum=x;
			}
			else sum+=x;
		}
		return cnt;
	}

	public int solution(int n, int m, int[] arr){
		int answer=0;
		int lt=Arrays.stream(arr).max().getAsInt();
		int rt=Arrays.stream(arr).sum();
		while(lt<=rt){
			int mid=(lt+rt)/2;
			if(count(arr, mid)<=m){
				answer=mid;
				rt=mid-1;
			}
			else lt=mid+1;
		}
		return answer;
	}
	public static void main(String[] args){
		Main T = new Main();
		Scanner kb = new Scanner(System.in);
		int n=kb.nextInt();
		int m=kb.nextInt();
		int[] arr=new int[n];
		for(int i=0; i<n; i++) arr[i]=kb.nextInt();
		System.out.println(T.solution(n, m, arr));
	}
}
````



<br>

### 결정 알고리즘 - 마구간 정하기
- lt=1, rt=최대값
- lt를 마구간의 최소좌표로 지정하면 안됨. 무조건 마구간 사이의 최소거리인 1로 지정
- 첫번째 마구간은 항상 lt에 넣는 것이 유리함
- 가장 최근에 지정한 마구간 endPosition ep지정
- 배치한 말의 수 찾는 count메서드 만들기
- [거리 구하는 이분검색]
````Java
public int solution(int n, int c, int[] arr){
		int answer=0;
		Arrays.sort(arr);
		int lt=1;
		int rt=arr[n-1];
		while(lt<=rt){
			int mid=(lt+rt)/2;
			if(count(arr, mid)>=c){  //유효함수로 카운트  C마리 이상인가?
				answer=mid;
				lt=mid+1;   //더 좋은 답이 있는지 좁혀가기
			}
			else rt=mid-1;   //답이 유효하지 않다면 더 작아져야함
		}
		return answer;
	}
````
- [유효여부 판단하는 함수]
````Java
public int count(int[] arr, int dist){
		int cnt=1;  //배치한 말의 수
		int ep=arr[0];   //1마리 배치했다 가장 왼쪽 좌표에 배치
		for(int i=1; i<arr.length; i++){
			if(arr[i]-ep>=dist){   //새로 선택한 마구간과 마지막에 배치한 마구간의 좌표 거리 구하기
				cnt++;    //가능하다면 말 한 마리 늘리고
				ep=arr[i];   //마지막 마구간 좌표 대입해주기
			}
		}
		return cnt;
	}
````

<br>

- 이분 검색은 이 코드 꼭 외우기
````Java
while(lt<=rt){
			int mid=(lt+rt)/2;
			if(count(arr, mid)>=c){
				answer=mid;
				lt=mid+1;
			}
			else rt=mid-1;
		}
````







