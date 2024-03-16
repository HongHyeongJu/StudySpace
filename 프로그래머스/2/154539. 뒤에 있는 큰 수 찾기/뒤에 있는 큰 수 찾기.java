import java.util.Stack;

class Solution {
    public int[] solution(int[] numbers) {
        int n = numbers.length;
        int[] answer = new int[n]; // 모든 원소에 대한 뒷 큰수를 저장할 배열
        Stack<Integer> stack = new Stack<>(); // 인덱스를 저장할 스택

        // answer 배열을 -1로 초기화
        for (int i = 0; i < n; i++) {
            answer[i] = -1;
        }

        // 배열의 첫 원소부터 시작하여 각 원소에 대해 처리
        for (int i = 0; i < n; i++) {
            // 스택이 비어있지 않고 현재 원소가 스택의 top에 있는 원소(인덱스)보다 큰 경우
            // 스택에서 원소(인덱스)를 제거하고 해당 인덱스의 뒷 큰수를 현재 원소로 설정
            while (!stack.isEmpty() && numbers[stack.peek()] < numbers[i]) {
                answer[stack.pop()] = numbers[i];
            }

            // 현재 원소의 인덱스를 스택에 추가
            stack.push(i);
        }

        // 스택에 남아있는 인덱스는 뒷 큰수를 찾지 못한 원소들로, answer 배열에서 -1로 유지됨

        return answer;
    }
}
