

import java.util.*;

class Solution {
    public int solution(String s) {
        int answer = 0;
        int strN = s.length();
        StringBuilder stringBuilder = new StringBuilder(s);
        for (int i = 0; i < strN; i++) {
            if (isOk(stringBuilder.toString())) {
                answer++;
            }
            
            char last = stringBuilder.charAt(strN-1); //맨끝문자 기록
            stringBuilder.deleteCharAt(strN - 1); //맨끝 삭제
            stringBuilder.insert(0, last); //삭제한것 맨앞에 추가
        }
        return answer;
    }
    
    
    /*stack으로 괄호 비교하는 것 부터*/
    boolean isOk (String s){
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {

            char c = s.charAt(i);
            
            // 여는 괄호는 stack에 push
            if (c == '{' || c == '[' || c == '(') {
                stack.push(c);
            } else if (c == '}' || c == ']' || c == ')') {
                // 닫는 괄호 등장 시, stack이 비어있거나 짝이 맞지 않으면 false
                if (stack.isEmpty()) return false;
    
                char top = stack.peek();
                if ((c == '}' && top == '{') || (c == ']' && top == '[') || (c == ')' && top == '(')) {
                    stack.pop();
                } else {
                    return false; // 짝이 맞지 않는 경우
                }
            }
        }
        
        return stack.isEmpty();
    }
    
}