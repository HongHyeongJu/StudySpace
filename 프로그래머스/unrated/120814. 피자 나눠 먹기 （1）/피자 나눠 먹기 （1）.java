class Solution {
    public int solution(int n) {
        int answer = 0;
        int pizza = 0;
        for(int i=0; i<n ; i++)
        {
            if(n<=7){
                return 1;
            }
            
            if((7*i)>=n){
                return i;
            }
        }
        return answer;
    }
}