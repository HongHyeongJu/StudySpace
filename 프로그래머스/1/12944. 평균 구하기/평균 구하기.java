class Solution {
    public double solution(int[] arr) {
        double answer = 0;
        int leng = arr.length;
        
        for(int i : arr){
            answer+=i;
        }
        
        return (double)(answer/leng);
    }
}