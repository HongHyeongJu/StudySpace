import java.util.StringJoiner;

class Solution {
    public String solution(String my_string, int k) {
        String answer = "";
        StringJoiner sj = new StringJoiner("");
        for(int i = 0; i<k ; i++){
            sj.add(my_string);
            
        }
         return sj.toString();
        
       
    }
}