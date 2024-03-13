
class Solution {


    public String solution(int n, int t, int m, int p) {
        StringBuilder answer = new StringBuilder();
        StringBuilder numbers = new StringBuilder("0"); // N진수로 변환된 숫자들을 저장할 StringBuilder
        int number = 1; // 변환할 숫자 시작

        while (numbers.length() < m * t+1 ) {  // m * t : 인원 * 말해야하는 숫자 개수
            numbers.append(Integer.toString(number, n));
            number++;
        }

        for (int i = p; i < numbers.length();i += m) {
            answer.append(numbers.charAt(i-1));
            if (answer.length() >= t) break; 
        }



        return answer.toString().toUpperCase();
    }


}