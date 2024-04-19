import java.util.*;
class Solution {
   public int solution(int x, int y, int n) {
        if (x == y) return 0;
        int answer = -1;

        //만든 숫자 배열
        Queue<int[]> queue = new LinkedList<>();
        HashSet<Integer> visited = new HashSet<>();

        //첫 숫자 넣기
        queue.offer(new int[]{x, 0});
        visited.add(x);

        // 큐에 아이템이 있을 동안 반복
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int currentValue = current[0];  //숫자
            int currentStep = current[1];  //연산 횟수

            //가능한 모든 연산 수행 Go
            int[] nextValues = new int[]{currentValue + n, currentValue * 2, currentValue * 3};  //3가지 연산 모두 증가하는 연산

            //검사 후 넣기
            for (int next : nextValues) {
                //연산했는데 만들려던 Y값이라면?
                if (next == y) return currentStep + 1;

                //목표보다 큰숫자거나 이미 있는 숫자면 넘어가기
                if (next > y || visited.contains(next)) continue;

                //다음에 연산하게 집어넣기~
                queue.offer(new int[]{next, currentStep + 1});
                visited.add(next);

            }
        }

        return answer;
    }

}