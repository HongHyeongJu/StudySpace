import java.util.Arrays;
class Solution {
    public int solution(int[] A, int[] B) {
        Arrays.sort(A);
        Arrays.sort(B);
        int answer = 0;
        int arrLength = A.length;
        for (int i = 0; i < arrLength; i++) {
            int bIndex = Math.abs(arrLength-1 - i);
            answer += A[i]*B[bIndex];
            
        }


        return answer;
    }
}