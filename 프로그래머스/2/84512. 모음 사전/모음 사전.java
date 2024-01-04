import java.util.*;

class Solution {
    

    
   public int solution(String word) {
        int answer = 0;
        int[] jump = {781, 156, 31, 6, 1}; // 각 자리가 변화될때 넘겨야할 경우의수
                   //625+125+25+5+1    125+25+5+1   25+5+1    5+1    1
        char[] vowels = {'A', 'E', 'I', 'O', 'U'};

        for (int i = 0; i < word.length(); i++) {
            for (int j = 0; j < 5; j++) {
                if (word.charAt(i) == vowels[j]) {
                    answer += 1 + j * jump[i];
                }
            }
        }

        return answer;
            
        }

}