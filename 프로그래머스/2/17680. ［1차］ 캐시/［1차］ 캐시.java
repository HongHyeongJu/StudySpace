
import java.util.*;

class Solution {
    public int solution(int cacheSize, String[] cities) {

        LinkedList<String> cache = new LinkedList<>();
        int answer = 0;


        if (cacheSize == 0) {
            return cities.length*5;
        }

        for (String city : cities) {
            String lowerCity = city.toLowerCase();

            //있으면 삭제 true
            if (cache.remove(lowerCity)) { // cache hit
                answer += 1;
            } else { // cache miss
                if (cache.size() == cacheSize) {  //최대캐시에 도달했으면 오래된것 제거
                    cache.poll(); // 가장 오래된 캐시 제거
                }
                answer += 5;
            }
            //사용했든 아무튼 추가추가(최근 사용을 맨뒤에 추가)
            cache.offer(lowerCity); // 최근 사용된 캐시를 맨 뒤로 추가
        }


        return answer;
    }
}