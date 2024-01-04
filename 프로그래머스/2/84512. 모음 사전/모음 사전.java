import java.util.*;

class Solution {
    
        private static final char[] VOWELS = {'A', 'E', 'I', 'O', 'U'};
    private static int count = 0;
    private static String target;
    private static int answer = 0;

    
    
   public int solution(String word) {
               target = word;

               DFS("");

        return answer;
    }

    public void DFS(String currentStr){
        //현재 단어가 찾던 단어이면 count값을 answer에 대입하고 메서드 나가기
        if(currentStr.equals(target)) {answer = count; return;}

        //현재 단어의 길이가 5이면 더 붙일것도 없음 나가라
        if(currentStr.length()==5) {return;}


        //아직 여유가 있다면 for each 반복문으로 current에 단어 하나 더 붙이기
        for(char a : VOWELS){
            count++;  //횟수 추가하고
            DFS(currentStr + a); //단어 붙여서 재귀호출
            //이미 찾았을 때 남은 재귀호출 실행하지 않도록 하기
            if(answer!=0) return;
            
        }

    }
}