class Solution {
    public int solution(int a, int b) {
        int answer = 0;
        //ab와 ba를 만든다.
        String strA = a+"";
        String strB = b+"";
        int aB = Integer.parseInt(strA+strB);
        int bA = Integer.parseInt(strB+strA);
        //비교한다
        Boolean bIsBig = (aB>bA);
        
        //큰 값을 리턴한다.(같다면 ab)
        if(bIsBig){
            return aB;
        } else {
            return bA;
        }
    }
}