
import java.util.Arrays;

class Solution {
    int solution(int[][] land) {
        int answer = 0;

        for (int i = 1; i < land.length; i++) {
            for (int j = 0; j < 4; j++) {
                int maxNum = 0;
                for (int k = 0; k < 4; k++) {
                    if(j!=k){
                        maxNum = Math.max(maxNum, land[i-1][k]);
                    }

                }
                land[i][j] += maxNum;
            }

        }
        int[] answerArr =  new int[4];
        answerArr = Arrays.stream(land[land.length-1]).toArray();
        Arrays.sort(answerArr);

        return answerArr[3];
    }
}