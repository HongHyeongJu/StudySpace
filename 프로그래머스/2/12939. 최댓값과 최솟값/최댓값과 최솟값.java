
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Solution {

    public static void main(String[] args) {
        Solution sol = new Solution();
        sol.solution("-1 -2 -3 -4");
    }

    public String solution(String s) {

        //공백을 구분해서 List<Integer>에 집어넣기

        List<Integer> numList = new ArrayList<>();

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            // 공백이 아니면 숫자를 stringBuilder에 추가
            if (c != ' ') {
                stringBuilder.append(c);
            }
            // 공백이면, 현재까지의 숫자를 리스트에 추가
            else {
                numList.add(Integer.parseInt(stringBuilder.toString()));
                stringBuilder = new StringBuilder(); // stringBuilder 초기화
            }

        }

        // 마지막 숫자 추가 (문자열의 끝에 도달했을 때 추가되지 않은 숫자를 처리)
        if (stringBuilder.length() > 0) {
            numList.add(Integer.parseInt(stringBuilder.toString()));
        }

        int maxStr = Collections.max(numList);
        int minStr = Collections.min(numList);

        return minStr + " " + maxStr;
    }
}