import java.util.*;

class Solution {
    public int solution(String s) {
        int answer = 0;
        
        if(s.charAt(0)=='-'){
            answer = Integer.parseInt(s.substring(1));
            return -answer;
        } else {
            answer = Integer.parseInt(s);
        }
        
        return answer;
    }
}