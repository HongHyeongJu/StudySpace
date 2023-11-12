class Solution {
    public long[] solution(int x, int n) {
        long[] answer = new long[n];
        long changeNum = x; // 초기값 설정
        
        for (int i = 0; i < n; i++) {
            answer[i] = changeNum;
            changeNum += x;
        }
        
        return answer;
    }
}
