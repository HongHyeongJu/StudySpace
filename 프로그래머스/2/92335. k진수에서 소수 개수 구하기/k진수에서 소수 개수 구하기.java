
class Solution {
    public int solution(int n, int k) {
        int answer = 0;
        String integerStr = Integer.toString(n, k);
        String[] strArr = integerStr.split("0");
        for (String str : strArr) {
            if (str.equals("")) {
                continue;
            } else if (isPrime(Long.parseLong(str))) {
                answer++;
            }
        }

        return answer;
    }

    public boolean isPrime(long num) {
         // 1과 0은 소수가 아님. 나가라!
        if (num <= 1) return false;

        for (long i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) return false; // 나누어 떨어지면 소수가 아님
        }
        return true;
    }


}