class Solution {
    public int solution(int[] numbers, int target) {
                /*문제배열 , 결과적으로 만들 수, 시작인덱스, 합계*/
        return dfs(numbers, target, 0, 0);
    }
    
       private int dfs(int[] numbers, int target, int index, int sum) {

        // 탐색이 배열의 끝에 도달했을 때, 타겟 넘버와 합계가 같은지 확인
        if (index == numbers.length) {
            if (sum == target) {
                return 1; // 타겟 넘버 적중 1 반환
            }
            return 0;
        }

        // 현재 숫자를 더하는 경우와 빼는 경우를 모두 탐색
        int count = 0;
         // 더하는 경우
        count += dfs(numbers, target, index + 1, sum + numbers[index]); // + numbers[index]
        // 빼는 경우
        count += dfs(numbers, target, index + 1, sum - numbers[index]); // - numbers[index]

        return count;
    }
}