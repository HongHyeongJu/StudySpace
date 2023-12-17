import java.util.HashMap;

class Solution {
    public String solution(String[] participant, String[] completion) {
        String answer = "";
        HashMap<String, Integer> map = new HashMap<>();

        // participant 배열을 순회하며 이름별 카운트 증가
        for(String player : participant){
            map.put(player, map.getOrDefault(player, 0) + 1);
        }

        // completion 배열을 순회하며 완주한 선수의 카운트 감소
        for(String player : completion){
            map.put(player, map.get(player) - 1);
        }

        // 완주하지 못한 선수 찾기
        for (String key : map.keySet()) {
            if (map.get(key) == 1) {
                answer = key;
                break;  // 완주하지 못한 선수를 찾으면 루프를 종료합니다.
            }
        }

        return answer;  // 수정된 위치에 return문을 배치합니다.
    }
}
