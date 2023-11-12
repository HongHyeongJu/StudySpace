import java.util.StringJoiner;

class Solution {
    public String solution(String[] arr) {
        String answer = "";
        StringJoiner sj = new StringJoiner("");
        for(String str : arr) {
            sj.add(str);
        }
        return sj.toString();
    }
}