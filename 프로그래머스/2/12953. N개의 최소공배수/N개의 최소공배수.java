
class Solution {
    public int solution(int[] arr) {
        int answer = 0;
        int checkNum = arr[0];

        for(int i = 1; i < arr.length; i++) {
            checkNum = lcm(checkNum,arr[i] );
        }


        return checkNum;
    }

    // 최소공배수를 구하는 함수
    int lcm(int a, int b){
        return a * b / gcd(a, b); // 두 숫자의 곱을 그 숫자들의 최대공약수로 나눈 것
    }

    int gcd(int a, int b){
        while (b!=0){
            int r = a%b;
            a=b;
            b=r;
        }
        return a;
    }

}