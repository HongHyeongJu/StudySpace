import java.util.*;

class Solution {
    public int solution(int k, int[] tangerine) {
        Map<Integer,Integer> map = new HashMap<>();
        for (int num:tangerine) {
            map.put(num,map.getOrDefault(num,0)+1);
        }


        // 개수만 추출하여 리스트에 저장한다.
        List<Integer> counts = new ArrayList<>(map.values());

        Collections.sort(counts, Collections.reverseOrder());

        int total = 0;
        int typeCount = 0;
        for (int count: counts) {
            total += count;
            typeCount++;
            if(total>=k){
                break;
            }
        }
    
        return typeCount;


    }
}