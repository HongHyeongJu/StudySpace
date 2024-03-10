class Solution {
    public int[] solution(String s) {
        int transCount = 0;
        int removedZeros  = 0;

        while (!s.equals("1")){
            int lenBefore = s.length(); // 현재 문자열의 길이(0을 제거하기 전)

            s = s.replaceAll("0", ""); // 문자열 s에서 모든 '0' 제거
            int lenAfter = s.length(); // '0'을 제거한 후의 문자열 길이

            removedZeros  += (lenBefore - lenAfter); // 제거된 0의 개수 계산
            s = Integer.toBinaryString(lenAfter); // 길이를 2진수로 변환하여 s에 할당

            transCount++; // 이진 변환 횟수 증가
        }

        int[] answer = new int[]{transCount, removedZeros };

        return answer;
    }
}