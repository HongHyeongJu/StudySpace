import java.util.*;

class Solution {

    public int[] solution(int[] answers) {
        int[] answer = {};


        int[][] patterns = {
            {1, 2, 3, 4, 5},                // 1번 학생의 답안 패턴
            {2, 1, 2, 3, 2, 4, 2, 5},       // 2번 학생의 답안 패턴
            {3, 3, 1, 1, 2, 2, 4, 4, 5, 5}  // 3번 학생의 답안 패턴
        };

        //패턴애 따라 답을 넣으면서 동시에 답지와 비교하기
        int[] score = new int[3];
        for (int i = 0; i < answers.length; i++) {
            for (int j = 0; j < 3; j++) {
                if (answers[i] == patterns[j][i % patterns[j].length]) {
                    score[j]++;
                }
            }
        }

        //최대 정답률 찾기
        int maxScore = Arrays.stream(score).max().getAsInt();

        //최대 정답자 찾기
        ArrayList<Integer> answerList = new ArrayList<>();
        
        for (int i = 0; i < 3; i++) {
            if (score[i] == maxScore) {
                answerList.add(i + 1);
            }
        }
        
        return answerList.stream().mapToInt(i -> i).toArray();
    }

}