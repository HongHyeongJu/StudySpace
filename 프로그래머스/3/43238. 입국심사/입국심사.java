import java.util.*;

class Solution {
    public long solution(int n, int[] times) {
        Arrays.sort(times);
        long left = 1;
        int length = times.length-1;
        long right = (long) times[length] * n;
        long answer = right; //최대값으로 초기화


        while (left <= right) {
            long mid = (left + right) / 2;
            if(isPass(mid,n,times)){
                answer = mid;
                right = mid - 1;
            } else {
                left = mid +1;
            }
        }
        return answer;
    }



    boolean isPass(long midTime, int n, int[] passArr) {
        long countPeople = 0;
        for (long passTime : passArr) {
            countPeople += midTime / passTime;
        }
        return countPeople >= n;
    }


}