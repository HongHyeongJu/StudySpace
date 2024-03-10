class Solution {
    public int solution(int n) {

        int answer = 0;
        int sum = 0;

        for (int strat = 1; strat <= n; strat++) {
            sum = 0;
            for (int  j= strat; j <=n ; j++) {
                sum += j;
                if(sum == n){
                    answer++;
                    break;
                }
                if(sum > n) break;
            }
            

        }



        return answer;
    }
}