import java.util.*;
class Solution {
    public int[] solution(int[] progresses, int[] speeds) {
        int[] answer = {};

        //각 기능이 며칠뒤에 완성되는지 배열로 만들기 결과 ex) [5일, 10일, 1일, 1일, 20일, 1일]
        //작업일 :  n일  > (100-기존퍼센트)/speed 몫
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i <progresses.length ; i++) {
            queue.offer(workingDays(progresses[i], speeds[i]));
        }

        ArrayList<Integer> aList = new ArrayList<>();
        int tmp = Integer.MAX_VALUE;
        int cntSystem=0; //새로운 배포 주기 시작
        int current = queue.peek();
        while (!queue.isEmpty()) {
            if (current >= queue.peek()) {
                queue.poll();
                cntSystem++;
            } else {
                aList.add(cntSystem);
                current = queue.poll();
                cntSystem = 1;  // 새로운 배포 주기 시작
            }
        }
        aList.add(cntSystem);  // 마지막 배포 추가
        int[] intArray = new int[aList.size()];
        for (int i = 0; i < aList.size(); i++) {
            intArray[i] = aList.get(i); // 자동 언박싱으로 Integer를 int로 변환
        }
        return intArray;
    }

    int workingDays(int percent, int speed){
        return (100 - percent + speed - 1) / speed;
    }
}