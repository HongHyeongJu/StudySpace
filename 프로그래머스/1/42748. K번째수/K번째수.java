import java.util.*;

class Solution {
    public int[] solution(int[] array, int[][] commands) {
        int[] answer = {};
        List<Integer> answerList = new ArrayList<>();


        for (int i = 0; i < commands.length; i++) {
            List<Integer> list = new ArrayList<>();
            for (int j = commands[i][0]-1; j <= commands[i][1]-1; j++) {
                list.add(array[j]);
            }
            Collections.sort(list);
            answerList.add(list.get(commands[i][2]-1));
//            list.stream().forEach(System.out::print);
//            System.out.println();
        }

        answer = answerList.stream().mapToInt(Integer::intValue).toArray();
        return answer;
    }
}