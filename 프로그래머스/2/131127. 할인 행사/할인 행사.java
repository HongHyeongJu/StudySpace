import java.util.*;

class Solution {
    public int solution(String[] want, int[] number, String[] discount) {
        int answer = 0;
        Map<String, Integer> wantMap = new HashMap<>();
        Map<String, Integer> martMap = new HashMap<>();

        for (int i = 0; i < want.length; i++) {
            wantMap.put(want[i], number[i]);
        }

        for (int i = 0; i <= 9; i++) {
            martMap.put(discount[i], martMap.getOrDefault(discount[i], 0) + 1);
        }

        //해당 날짜에 모든 물건 충족하면 true
        for (int i = 9; i < discount.length - 1; i++) {
            if (isOk(want, wantMap, martMap)) {
                answer++;
            }
            martMap.put(discount[i - 9], martMap.getOrDefault(discount[i - 9], 0) - 1);
            martMap.put(discount[i + 1], martMap.getOrDefault(discount[i + 1], 0) + 1);
        }
        if (isOk(want, wantMap, martMap)) {
            answer++;
        }


        return answer;
    }

    //해당 날짜에 모든 물건 충족하면 true

    boolean isOk(String[] want, Map<String, Integer> wantMap, Map<String, Integer> discountMap) {
        for (String wantStuff : want) {
            if (wantMap.get(wantStuff) > discountMap.getOrDefault(wantStuff, 0)) {
                return false;
            }
        }
        return true;
    }

}