import java.util.*;

class Solution {

    public int solution(int N, int number) {
//        N은 1 이상 9 이하입니다.
//        number는 1 이상 32,000 이하입니다.
//        N 사용횟수의 최솟값을 return;
        if(N==number){return 1;}
        int answer = 0;

        //N번 사용 Set 만들기. 그리고 5 55 555 등으로 add하기
        Set<Integer>[] dpSet = new Set[9];
        int base = N;
        for (int i = 1; i <= 8; i++) {
            dpSet[i] = new HashSet<>();
            dpSet[i].add(base);
            base = base*10 + N;
        }


        //횟수(1~8) 늘려가기 ->  횟수 가능한 조합 만들기(1+3,2+2,3+1)  -> 횟수에 따른 && 사칙연산 조합  추가하기
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j < i; j++) {
                for (int num1 : dpSet[j]) {
                    for (int num2 :  dpSet[i-j]) {
                        dpSet[i].add(num1+num2);
                        dpSet[i].add(num1-num2);
                        dpSet[i].add(num1*num2);
                        if (num2 != 0) dpSet[i].add(num1 / num2);
                    }

                }
            }
            if (dpSet[i].contains(number)) {
            return i;
            }

        }



        return -1;
    }
}