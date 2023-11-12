import java.util.Scanner;

public class ex0109 {

	public static void main(String[] args){
		ex0109 T = new ex0109();
		Scanner kb = new Scanner(System.in);
		String str=kb.next();
		System.out.print(T.solution(str));
	}

    public int solution(String s){
		int answer=0;
		for(char x : s.toCharArray()){

            //**answer = answer \*10 + (x - 48)** 외우기
            if(x>=48 && x<=57) answer=answer*10+(x-48);

			if(Character.isDigit(x)){
				answer=answer*10+ Character.getNumericValue(x);
			}

		}
		return answer;
	}


        public int solution2(String s){
		String answer="";
		for(char x : s.toCharArray()){

			if(Character.isDigit(x)) answer+=x;
		}
		return Integer.parseInt(answer);
	}


}
