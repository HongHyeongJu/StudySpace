class Solution {
    public int solution(int n) {
        int answer = 0;
        boolean same = false;
        for(int i=1 ;i<=n; i++){
            int tmp = n/i;
            if(i*tmp==n){
                answer++;
                if(i==tmp){
                    same=true;

                }
            }
        }
        return answer;
    }
}