import java.util.*;

class Solution {
    public int solution(int[] order) {
        int answer = 0; // 성공적으로 트럭에 실린 상자의 수
        Queue<Integer> container = new LinkedList<>();
        Stack<Integer> subContainer = new Stack<>();
        
        // 초기 컨테이너에 상자 넣기
        for (int i = 1; i <= order.length; i++) {
            container.offer(i);
        }

        int index = 0; // 처리할 order의 인덱스
        while (!container.isEmpty() || !subContainer.isEmpty()) {
            // 보조 컨테이너의 top이 현재 순서와 일치하면 처리
            while (!subContainer.isEmpty() && subContainer.peek() == order[index]) {
                subContainer.pop();
                index++;
                answer++;
            }

            if (container.isEmpty()) {
                break;
            }

            int current = container.poll();
            if (current == order[index]) { // 현재 컨테이너의 상자가 처리할 순서와 일치
                index++;
                answer++;
            } else { // 보조 컨테이너로 이동
                subContainer.push(current);
            }
        }

        return answer;
    }
}
