import java.util.*;

class Solution {

    public String solution(String number, int k) {
        String answer="";

        Stack<Character> stack = new Stack<>();

        for(char current : number.toCharArray()){
            //스택에 값이 있을때만 비교 && 지울수 있는 기회가 있어야함 && 현재 넣을 숫자가 peek한것보다 크면 pop해서 지워버려
            while (!stack.isEmpty()&&(k>0)&&(stack.peek()<current)){
                stack.pop();
                k--;
            }
            stack.push(current);
        }

        //다 돌았는데 k가 남았다? 끝부터 지워
        while (k>0){
            stack.pop();
            k--;
        }

        StringBuilder answerString = new StringBuilder();
        for(char ch : stack){
            answerString.append(ch);
        }

        return answerString.toString();
    }
}