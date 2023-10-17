class Solution {
    public double solution(int[] numbers) {
        double answer = 0;
        int sumNum = 0;
        
        for(int arrNum : numbers)
        {
            sumNum += arrNum;
        }
        answer =  (double)(sumNum*10)/numbers.length/10;

        return answer;
    }
}