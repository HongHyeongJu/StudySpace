class Solution {
    public String solution(String s) {
        
        StringBuilder sb = new StringBuilder();

        // 첫 문자를 처리하기 위해 초기 상태를 공백으로 설정
        char prevChar = ' ';
        for (int i = 0; i < s.length(); i++) {
            char currentChar = s.charAt(i);
            
            // 이전 문자가 공백이고 현재 문자가 알파벳이라면 대문자로 변환
            if (prevChar == ' ' && Character.isLetter(currentChar)) {
                sb.append(Character.toUpperCase(currentChar));
            } else {
                // 그 외의 경우는 모두 소문자로 변환
                sb.append(Character.toLowerCase(currentChar));
            }
            prevChar = currentChar;
        }

        return sb.toString();
    }
}