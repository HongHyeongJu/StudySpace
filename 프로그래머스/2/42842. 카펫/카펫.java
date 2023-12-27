class Solution {

    public int[] solution(int brown, int yellow) {
        int[] answer = {};

        int weight;
        int height;
        //갈색+노랑 = w*h  약수 구하기 하다가 카페트면 바로 리턴!
        int wh = brown + yellow;
        for (int i = 1; i*i <= wh; i++) {
            if(wh % i==0){
                int factor = wh/i;
                if(isCarpet(brown, yellow,i,factor)){
                    return  i>=factor ? new int[]{i, factor} : new int[]{factor,i}  ;
                }
            }
        }



        return answer;
    }


    boolean isCarpet(int brown, int yellow, int w, int h){
        if(w<=2||h<=2) return false;
        if(brown==(2*w+2*(h-2))&&yellow==((w-2)*(h-2))) return true;
        return false;
    }
}