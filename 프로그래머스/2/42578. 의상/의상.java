import java.util.*;
class Solution {
    public int solution(String[][] clothes) {
        int answer = 0;
        // 받은 행렬의 인덱스 1을 기준으로 종류 찾기. 개수*개수*개수 -1
        HashMap<String,Integer> map = new HashMap<>();
        for(String[] clothe : clothes){
            map.put(clothe[1],map.getOrDefault(clothe[1],0)+1);
        }
        int combinations = 1;
        for (int count : map.values()) {
            combinations *= (count + 1); // 각 종류별 옷을 입거나 입지 않는 경우
        }
    
        return combinations - 1; // 아무것도 입지 않는 경우 제외import java.util.*;
    }
}