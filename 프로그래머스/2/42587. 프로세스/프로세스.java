import java.util.*;

import java.util.stream.Collectors;


class Solution {
    
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
        Program(int name, int priority ){
            this.name = name;
            this.priority = priority;
        }

    }
    
}