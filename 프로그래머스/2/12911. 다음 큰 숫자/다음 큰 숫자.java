class Solution {
    public int solution(int n) {
        int answer = 0;

        int oneCount = Integer.bitCount(n);
        for (int i = n+1; i <= 1000000 ; i++) {
            if(oneCount == Integer.bitCount(i)){
                answer = i;
                break;
            }
        }

        return answer;
    }
}