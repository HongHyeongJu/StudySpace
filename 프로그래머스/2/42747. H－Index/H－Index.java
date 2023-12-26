import java.util.*;


class Solution {
public int solution(int[] citations) {
        int answer = 0;
        Arrays.sort(citations);
        System.out.println(Arrays.toString(citations));
        System.out.println("=================");


        for (int i = 0; i < citations.length; i++) {
            int hIndex = Math.min(citations[i],citations.length-i);
            if(answer < hIndex){
                answer=hIndex;
                // System.out.println("hIndex "+ hIndex);
            }
        }

        // System.out.println("출력 "+answer);
        return answer;
    }
}