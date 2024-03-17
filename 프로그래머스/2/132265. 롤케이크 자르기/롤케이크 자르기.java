
import java.util.*;

class Solution {


    public int solution(int[] topping) {
        int answer = 0;

        Map<Integer,Integer> bro_1_Map = new HashMap<>();
        Map<Integer,Integer> bro_2_Map = new HashMap<>();

        // 초기 설정: 첫 번째 토핑은 형에게, 나머지는 동생에게
        bro_1_Map.put(topping[0],1);

        for (int i = 1; i < topping.length; i++) {
            bro_2_Map.put(topping[i], bro_2_Map.getOrDefault(topping[i],0) + 1);
        }

        for (int i = 1; i < topping.length-1; i++) {
            int currentTopping = topping[i];
            
            //형한테는 추가
            bro_1_Map.put(currentTopping, bro_1_Map.getOrDefault(currentTopping,0)+1);
            
            //동생한테는 제거
            if (bro_2_Map.get(currentTopping) == 1) {
                bro_2_Map.remove(currentTopping);
            } else {
                bro_2_Map.put(currentTopping, bro_2_Map.get(currentTopping) - 1);
            }

            // 토핑 종류 수 비교
            if (bro_1_Map.size() == bro_2_Map.size()) {
                answer++;
            }
        }

        return answer;

    }

}