import java.util.*;

class Solution {
    public int solution(int[] elements) {

        int answer = 0;
        int eleLength = elements.length;
        int[] circleElements = new int[2*eleLength];
        Set<Integer> set = new HashSet<>();

        for (int i = 0; i < eleLength; i++) {
            circleElements[i] = circleElements[i+eleLength] = elements[i];
        }

        for (int start = 0; start < eleLength; start++) {
            for (int end = 0; end < start+eleLength-1 ; end++) {
                int sum = 0;
                for (int k = start; k <= end; k++) {
                    sum += circleElements[k];
                }
                set.add(sum);
            }
        }
        

        return set.size();

    }
}