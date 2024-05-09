import java.util.LinkedList;
import java.util.Queue;

class Solution {
    public int solution(int bridge_length, int weight, int[] truck_weights) {
        // 다리 위 트럭 큐
        Queue<Integer> bridge = new LinkedList<>(); 
        
        // 각 트럭의 다리 진입 시간을 저장
        int[] truckTimes = new int[truck_weights.length]; 
        
        int time = 0; // 경과 시간
        int index = 0; // 대기 중인 트럭의 인덱스

        while (true) {
            time++; // 시간 증가

            // 다리를 건너고 있는 트럭이 나갈 시간인지 확인하고, 나가면 큐에서 제거
            // 현재 다리에 트럭이 있는지 여부    
            // && 가장 먼저 들어온 트럭이 다리를 완전히 지나가는 시간이 현재 시간과 같은지
            if (!bridge.isEmpty() && truckTimes[bridge.peek()] + bridge_length == time) {
                int truckIndex = bridge.poll();
            }

            // index : 새로운 트럭 있는지
            // && 새로운 트럭이 다리에 진입할 수 있는지(무게 길이)
            if (index < truck_weights.length && getWeightOnBridge(bridge, truck_weights) + truck_weights[index] <= weight) {
                bridge.add(index);
                truckTimes[index] = time;
                index++;
            }

            // 모든 트럭이 다리를 지나갔는지?
            if (bridge.isEmpty()) {
                break;
            }
        }

        return time;
    }

    // 현재 다리 위 트럭의 총 무게를 계산
    int getWeightOnBridge(Queue<Integer> bridge, int[] truck_weights) {
        int totalWeight = 0;
        for (int truckIndex : bridge) {
            totalWeight += truck_weights[truckIndex];
        }
        return totalWeight;
    }
}
