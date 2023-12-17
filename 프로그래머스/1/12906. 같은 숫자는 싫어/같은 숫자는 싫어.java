import java.util.*;

public class Solution {
    public int[] solution(int []arr) {
        int[] answer = {};

        //스택을 만든다
        Stack<Integer> intStack = new Stack<>();

        //배열 반복하면서 하나씩 push한다.
        for(int nextNumber : arr){
            //peek한거랑 같으면 지나간다. 다르면 넣는다
            // 스택이 비어 있거나, 스택의 top과 현재 숫자가 다른 경우에만 push
            if(intStack.isEmpty() || intStack.peek() != nextNumber){
                intStack.push(nextNumber);
            }
        }

        //스택을 출력한다
        int[] intArray = new int[intStack.size()];
        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = intStack.get(i); // stack.elementAt(i)를 사용해도 됩니다.

        }

        return intArray;
    }
}