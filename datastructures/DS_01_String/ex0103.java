import java.util.Scanner;

public class ex0103 {

	public static void main(String[] args){
		ex0103 T = new ex0103();
		Scanner kb = new Scanner(System.in);
		String str=kb.nextLine();
		System.out.print(T.solution(str));
	}

	public String solution(String str){
		String answer="";
		int m=Integer.MIN_VALUE;
		String[] s = str.split(" ");
		for(String x : s){
			int len=x.length();
			if(len>m){
				m=len;
				answer=x;
			}
		}
		return answer;
	}


}
