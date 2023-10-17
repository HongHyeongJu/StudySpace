class Solution {
    public int solution(int n, int k) {
        int answer = 0;
        int service = n/10;
        if(service==0){
            return 12000*n + 2000*k;
        } else if (k<=service){
            return 12000*n;
        }  else if (service<k){
            return 12000*n+2000*(k-service);
        }
        
        
        return answer;
    }
}