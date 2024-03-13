
import java.util.*;

class Solution {
    
    public int[] solution(String str) {
        str = str.substring(1, str.length() -1);
        String[] strArr1 = str.split("},\\{");

        Map<String, Integer> countMap = new HashMap<>();

        for (String ss : strArr1){
            String cut = ss.replaceAll("[{}]","");
            String[] srtArr2 = cut.split(",");
            for (String num : srtArr2){
                countMap.put(num, countMap.getOrDefault(num,0)+1);
            }
        }


        // 등장 횟수에 따라 정렬하여 튜플 복원
        List< Map.Entry<String, Integer> > entryList = new ArrayList<>(countMap.entrySet());
        //Entry는 .getKey(),  .getValue()로 값 출력 형태는 A=3 이런형태
        entryList.sort((o1, o2) -> o2.getValue() - o1.getValue());
        //많이 저장 된 순으로 저장

        int[] answer = new int[countMap.size()];
        int idx = 0;
        for (Map.Entry<String, Integer> entry : entryList) {
            answer[idx++] = Integer.parseInt(entry.getKey());
        }

        return answer;
    }


}
