import java.util.Stack;


class Solution
{
    public int solution(String s)
    {
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char current = s.charAt(i);
            if(!stack.isEmpty()&&stack.peek()==current){
                stack.pop();
            } else {
                stack.push(s.charAt(i));
            }
        }

        //스택으로 비교!


        return stack.isEmpty() ? 1 : 0;
    }
}