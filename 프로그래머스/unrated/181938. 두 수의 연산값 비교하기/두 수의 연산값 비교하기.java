class Solution {
    public int solution(int a, int b) {
        int answer = 0;
        //a+b 계산, 2*a*b계산
        int ab = Integer.parseInt((a+"")+(b+""));
        int ab2 = 2*a*b;      
        
        //비교+리턴
        return ab>ab2 ? ab : ab2;
        
        
    }
}