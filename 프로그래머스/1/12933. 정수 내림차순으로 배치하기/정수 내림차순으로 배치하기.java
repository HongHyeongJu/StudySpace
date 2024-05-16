import java.util.Arrays;
import java.util.Collections;

class Solution {
    public long solution(long n) {
        // 숫자 -> 문자
        String s = String.valueOf(n);

        // 문자열 -> 문자배열
        Character[] charArr = s.chars().mapToObj(c -> (char)c).toArray(Character[]::new); 

        // 정렬
        Arrays.sort(charArr, Collections.reverseOrder());

        StringBuilder sb = new StringBuilder(charArr.length);
        for (char c : charArr) {
            sb.append(c);
        }

        return Long.parseLong(sb.toString());
    }


}
