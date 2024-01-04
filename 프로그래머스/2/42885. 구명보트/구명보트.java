import java.util.*;

class Solution {
    public int solution(int[] people, int limit) {
        int answer = 0;
        int lt=0;
        int rt=people.length-1;

        Arrays.sort(people);

        while (lt<=rt){
            if(people[lt]+people[rt]<=limit){
                answer++;
                lt++;
                rt--;
            } else {
                answer++;
                rt--;
            }
        }

        return answer;
    }
}