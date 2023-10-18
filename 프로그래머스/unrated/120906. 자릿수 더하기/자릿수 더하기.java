class Solution {
    public int solution(int n) {
        int answer = 0;
        while (n > 0) {
            int quotient = n / 10; // 나머지를 제외한 몫
            int remainder = n % 10; // 나머지 (각 자리 숫자)

            answer += remainder; // 나머지(자리 숫자)를 더함
            n = quotient; // 다음 자리로 이동
        }

        return answer;
    }
}