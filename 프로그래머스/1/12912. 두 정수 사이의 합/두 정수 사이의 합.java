class Solution {
    public long solution(int a, int b) {
        long answer = 0;
        
        if (a == b) {
            return a;
        }
      
        long count = Math.abs((long)a - (long)b) + 1; 
        
        answer = (long)(a + b) * count / 2; // 등차수열
        
        return answer;
    }
}