<br>

## 같은 숫자는 싫어
```java
package Programers;

import java.util.*;

import static java.lang.Integer.parseInt;

class Solution {

    public static void main(String[] args) {
        Solution sol = new Solution();
        int[] arrS = {1,1,3,3,0,1,1};
        sol.solution(arrS);
    }


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
            System.out.println("intStack.get(i) "+intArray[i]);
        }

        return intArray;
    }



}

```



<br>

## 기능 개발
```java
package Programers;

import java.util.*;

import static java.lang.Integer.parseInt;

class Solution {

    public static void main(String[] args) {
        Solution sol = new Solution();
        int[] arrS = {95, 90, 99, 99, 80, 99};
        int[] arrS2 = {1, 1, 1, 1, 1, 1};
/*        for (int i = 0; i <arrS.length ; i++) {
            System.out.println("workingDays "+sol.workingDays(arrS[i],arrS2[i]));
        }*/
        sol.solution(arrS, arrS2);
    }


    public int[] solution(int[] progresses, int[] speeds) {
        int[] answer = {};

        //각 기능이 며칠뒤에 완성되는지 배열로 만들기 결과 ex) [5일, 10일, 1일, 1일, 20일, 1일]
        //작업일 :  n일  > (100-기존퍼센트)/speed 몫
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i <progresses.length ; i++) {
            queue.offer(workingDays(progresses[i], speeds[i]));
            System.out.println("["+i+"] workingDays "+workingDays(progresses[i], speeds[i]));
        }

        System.out.println("=====================================");
        ArrayList<Integer> aList = new ArrayList<>();
        int tmp = Integer.MAX_VALUE;
        int cntSystem=0; //새로운 배포 주기 시작
        int current = queue.peek();
        while (!queue.isEmpty()) {
            if (current >= queue.peek()) {
                System.out.println("if");
                queue.poll();
                cntSystem++;
            } else {
                System.out.println("else");
                aList.add(cntSystem);
                current = queue.poll();
                cntSystem = 1;  // 새로운 배포 주기 시작
            }
        }
        aList.add(cntSystem);  // 마지막 배포 추가
        System.out.println("============");
        int[] intArray = new int[aList.size()];
        for (int i = 0; i < aList.size(); i++) {
            intArray[i] = aList.get(i); // 자동 언박싱으로 Integer를 int로 변환
            System.out.println(intArray[i]);
        }
        return intArray;
    }

    int workingDays(int percent, int speed){
        return (100 - percent + speed - 1) / speed;
    }



}

```




<br>

## 괄호 닫기
### stack이용. 그러나 시간 효율성 탈락
```java
import java.util.*;

import static java.lang.Integer.parseInt;

class Solution {

    public static void main(String[] args) {
        Solution sol = new Solution();
        int[] arrS = {95, 90, 99, 99, 80, 99};
        int[] arrS2 = {1, 1, 1, 1, 1, 1};
/*        for (int i = 0; i <arrS.length ; i++) {
            System.out.println("workingDays "+sol.workingDays(arrS[i],arrS2[i]));
        }*/
        System.out.println(sol.solution("(()))))"));
    }

    boolean solution(String s) {
        boolean answer = true;

        // (를 만나면 반복문으로 stack에 집어넣는다.
        // )를 만나면 stack에서 (를 하나 pop()한다
        // )를 만났는데 (가 없거나 배열다 소진했는데  isEmpty가 false면 false
        Stack<Character> stack = new Stack<>();
        int cnt = 0; //효율성테스트 탈락해서 추가함
        for(Character chr : s.toCharArray()){
            if(cnt<0)  return false;
            if(chr.equals('(')){
                stack.push(chr);
                cnt ++;
            } else if(chr.equals(')')) {
                if(cnt<=0)  return false;
                // 꺼낼 때 비어있지 않아야함
                if (!stack.isEmpty() && stack.peek().equals('(')){
                     stack.pop();
                     cnt --;
                } else {
                    return false;
                }
            }
        }

        if (!stack.isEmpty())  return false;

        return answer;
    }



}

```
### 간단한 방법
```java

class Solution {

    public static void main(String[] args) {
        Solution sol = new Solution();
        int[] arrS = {95, 90, 99, 99, 80, 99};
        int[] arrS2 = {1, 1, 1, 1, 1, 1};
/*        for (int i = 0; i <arrS.length ; i++) {
            System.out.println("workingDays "+sol.workingDays(arrS[i],arrS2[i]));
        }*/
        System.out.println(sol.solution("(()))))"));
    }

    boolean solution(String s) {
        int counter = 0;
    
        for (char chr : s.toCharArray()) {
            //(를 만나면 +
            if (chr == '(') {
                counter++;
            } else {
                //)를 만나면 )  그런데 이미 0이면 -1 되므로...
                if (counter == 0) return false;  // 더 이상 닫을 괄호가 없음
                counter--;
            }
        }
    
        return counter == 0;
    }



}
```



<br>

## 프로세스 실행
```java
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

class Solution {

    public static void main(String[] args) {
        Solution sol = new Solution();
        int[] arrS = {2, 1, 3, 2};
        int[] arrS2 = {1, 1, 1, 1, 1, 1};
/*        for (int i = 0; i <arrS.length ; i++) {
            System.out.println("workingDays "+sol.workingDays(arrS[i],arrS2[i]));
        }*/
//        System.out.println(sol.solution("(()))))"));
//        sol.solution(arrS,2);
        System.out.println(sol.solution(arrS, 0));
    }

    public int solution(int[] priorities, int location) {

        Queue<Program> queue = new LinkedList<>();   //프로그램 담을 큐
        ArrayList<Integer> arrayList = new ArrayList<>();  //우선순위 순서


        for (int i = 0; i < priorities.length; i++) {
            queue.offer(new Program(i, priorities[i]));  // new Program(프로그램이름, 우선순위)
            arrayList.add(priorities[i]); //set에 우선순위를 담기
        }

        ArrayList<Integer> sortedList = arrayList.stream()  //스트림 변환
                                                //.distinct()  //중복제거 앗 중복제거 하면 안되는구나....헐
                                                .sorted(Collections.reverseOrder())  //내림차순 정렬
                                                .collect(Collectors.toCollection(ArrayList::new));
                                                //새로운 컬렉션으로 반환 하면 우선순위만 남은 배열 완성
        int maxValue = sortedList.get(0);
        int pollCount = 0;

        //Queue가 빌때까지 반복한다
        while (!queue.isEmpty()) {
            //선입된것을 꺼낸다. poll
            Program currentPG = queue.poll();

            //꺼낸것의 우선순위가 제일 높은거라면
            if(maxValue==currentPG.priority){
                //실행횟수 추가
                pollCount++;
                //찾는 정답이라면 return
                if(currentPG.name==location) return pollCount;

                if(sortedList.size()>1) {
                    sortedList.remove(0);
                    maxValue = sortedList.get(0);
                } else  {
                //남은 우선순위 하나 남았으면 이제 비교할 것 없는것
                }
            } else {
                queue.offer(currentPG);
            }
        }
        return pollCount;
    }

    class Program {
        int name;
        int priority;

        Program(int name, int priority) {
            this.name = name;
            this.priority = priority;
        }

    }



}
```




<br>

## 트럭문제 (문제 설명 이상함)
```java

class Solution {

    public static void main(String[] args) {
        Solution sol = new Solution();
        int[] arrS = {7, 4, 5, 6};
        int[] arrS2 = {10,10,10,10,10,10,10,10,10,10};
/*        for (int i = 0; i <arrS.length ; i++) {
            System.out.println("workingDays "+sol.workingDays(arrS[i],arrS2[i]));
        }*/
//        System.out.println(sol.solution("(()))))"));
        sol.solution(100, 100, arrS2);
//        System.out.println(sol.solution(arrS, 0));
    }

    class Truck {
        int weight;
        int stay = 0;

        Truck(int weight) {
            this.weight = weight;
            this.stay = 0;
        }
    }

    class Bridge {
        int bridge_length;
        int weight;

        Bridge(int bridge_length, int weight) {
            this.bridge_length = bridge_length;
            this.weight = weight;
        }


    }

    public int solution(int bridge_length, int weight, int[] truck_weights) {
        int passTime = 0;
        Bridge currentBridge = new Bridge(0, 0);
        Queue<Truck> waitQueue = new LinkedList<>();
        Queue<Truck> bridgeQueue = new LinkedList<>();

        //반복문으로 대기 트럭 큐에 트럭들을 담습니다
        for (int i = 0; i < truck_weights.length; i++) {
            waitQueue.offer(new Truck(truck_weights[i]));
        }

        int currentBridgeWeight  = 0;
        //대기 큐가 빌때까지 반복합니다.

        while (!waitQueue.isEmpty() || !bridgeQueue.isEmpty()) {
            System.out.println("1초가 지납니다");
            //1초가 지납니다
            passTime++;


            System.out.println("2초 채우신 분들은 나가주세요");
            //2초 채우신 분들은 나가주세요
            while (!bridgeQueue.isEmpty() && bridgeQueue.peek().stay == 2) {
                Truck pollTruck = bridgeQueue.poll();
                currentBridgeWeight -= pollTruck.weight;
            }
            System.out.println("정리 완료요");
            //정리 완료요


            if (!waitQueue.isEmpty()) {
                Truck nextTruck = waitQueue.peek();
                if (currentBridgeWeight + nextTruck.weight <= weight && bridgeQueue.size() <= bridge_length) {
                    waitQueue.poll();
                    bridgeQueue.offer(nextTruck);
                    currentBridgeWeight += nextTruck.weight;
                }
            }
            //peek했는데 다리길이, 중량 ok? 그러면 바로 poll => offer. 다리 중량 방금 트럭만큼 추가추가

            System.out.println("=====추가추가===");
            //다리에 있는 트럭들 stay를 모두+1합니다.
            for (Truck truck : bridgeQueue) {
                System.out.println("트럭 "+ truck.weight);
                truck.stay++;
            }
            System.out.println("현재 초 "+passTime);
            System.out.println("===============");

        }

        System.out.println("passTime " + passTime);

        return passTime;
    }


}

```




<br>

## 주식가격
```java
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

class Solution {

    public static void main(String[] args) {
        Solution sol = new Solution();
        int[] arrS = {1, 2, 3, 2, 3};
        int[] arrS2 = {10,10,10,10,10,10,10,10,10,10};
/*        for (int i = 0; i <arrS.length ; i++) {
            System.out.println("workingDays "+sol.workingDays(arrS[i],arrS2[i]));
        }*/
//        System.out.println(sol.solution("(()))))"));
        sol.solution(arrS);
//        System.out.println(sol.solution(arrS, 0));
    }

    public int[] solution(int[] prices) {
        int[] answer = new int[prices.length];


        Arrays.fill(answer,0);

        //하나 꺼내서 current랑  next랑 비교. (꺼낼게 있는지 꼭 확인하고)next가 나보다 같거나 크면  poll,하고 upTime++
        for (int i = 0; i <prices.length ; i++) {
            System.out.println(prices[i]+" 반복문");
            int upTime=1;
            for (int j = i+1; j < prices.length; j++) {
                if(prices[i] <= prices[j]) {
                    System.out.println(prices[i]+"와 "+j);
                    answer[i] = upTime++;
                } else {
                    System.out.println("?");
                    answer[i] = upTime;
                    break;
                }
            }
        }

        //반복문은 꺼낼 큐가 없으면 종료됨
        //위의 비교식 끝나면 종료큐에 추가
        Arrays.stream(answer).forEach(System.out::print);
        //큐를 int[]로 리턴하기

        return answer;
    }

}
```






