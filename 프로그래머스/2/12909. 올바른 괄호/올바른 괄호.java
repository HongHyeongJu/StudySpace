import java.util.*;

class Solution {
    boolean solution(String s) {
        int counter = 0;

    for (char chr : s.toCharArray()) {
        if (chr == '(') {
            counter++;
        } else {
            if (counter == 0) return false;  // 더 이상 닫을 괄호가 없음
            counter--;
        }
    }

    return counter == 0;
    }
}