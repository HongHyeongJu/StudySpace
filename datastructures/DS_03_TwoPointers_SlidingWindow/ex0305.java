package DS_03_TwoPointers_SlidingWindow;

import java.util.*;
public class ex0305 {


	public static void main(String[] args){
		ex0305 T = new ex0305();
		Scanner kb = new Scanner(System.in);
		int n=kb.nextInt();
		System.out.print(T.solution(n));
	}

	//[투포인터로 풀기]
	public int solution(int n){
		int answer=0, sum=0;
		int m=n/2+1;
		int[] arr=new int[m];
		for(int i=0; i<m; i++) arr[i]=i+1;
		int lt=0;
		for(int rt=0; rt<m; rt++){
			sum+=arr[rt];            //rt는   rt++하고  /  sum에 arr[rt] 더하기
			if(sum==n) answer++;
			while(sum>=n){
				sum-=arr[lt++];      //lt는   sum에서 arr[lt] 빼고 /   lt++
				if(sum==n) answer++;
			}
		}
		return answer;
	}

	//[수학으로 풀기]
	//while문이 다시돌면서 1+2+에 cnt++;로 인해서 +3을 해주고 / M에는 -3 해주는 것을 반복
	public int solution2(int n){
		int answer=0, cnt=1;
		n--;
		while(n>0){
			cnt++;
			n=n-cnt;
			if(n%cnt==0) answer++;
		}
		return answer;
	}


}