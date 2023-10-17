class Solution {
    public int solution(int[] dot) {
        int answer = 0;
        //[정수, 정수]
        
        if(dot[0]<0){
            if(dot[1]<0){
                return 3;
            } else {
                return 2;
            }
        } else {
            if(dot[1]<0){
              return 4;
            } else {
              return 1;  
            }
        }
        
    }
}