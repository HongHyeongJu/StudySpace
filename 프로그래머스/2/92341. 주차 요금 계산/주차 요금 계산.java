import java.util.*;

class Solution {
    public int[] solution(int[] fees, String[] records) {
        // 차량 번호를 키로 하고, 입차 시간을 값으로 하는 Map
        Map<String, Integer> parking = new HashMap<>();
        // 차량 번호를 키로 하고, 누적 주차 시간(분)을 값으로 하는 Map
        Map<String, Integer> times = new TreeMap<>();

        for (String record : records) {
            String[] split = record.split(" ");
            String time = split[0];
            String carNumber = split[1];
            String status = split[2];

            int minutes = timeToMinutes(time);

            if (status.equals("IN")) {
                parking.put(carNumber, minutes);
            } else {
                int inTime = parking.remove(carNumber);
                times.put(carNumber, times.getOrDefault(carNumber, 0) + minutes - inTime);
            }
        }

        // 출차되지 않은 차량 처리
        for (Map.Entry<String, Integer> entry : parking.entrySet()) {
            String carNumber = entry.getKey();
            int inTime = entry.getValue();
            times.put(carNumber, times.getOrDefault(carNumber, 0) + (23 * 60 + 59) - inTime);
        }

        int[] answer = new int[times.size()];
        int idx = 0;

        for (int time : times.values()) {
            answer[idx++] = calculateFee(time, fees);
        }

        return answer;
    }

    private int timeToMinutes(String time) {
        String[] hm = time.split(":");
        int hour = Integer.parseInt(hm[0]);
        int minute = Integer.parseInt(hm[1]);
        return hour * 60 + minute;
    }

    private int calculateFee(int minutes, int[] fees) {
        if (minutes <= fees[0]) {
            return fees[1];
        } else {
            return fees[1] + (int)Math.ceil((double)(minutes - fees[0]) / fees[2]) * fees[3];
        }
    }
}
