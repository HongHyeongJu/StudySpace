import java.util.*;

class Solution {

    public String solution(int[] numbers) {
        String answer = "";
        //String 배열 만들기
        String[] strNumbers = new String[numbers.length];

        //일단 String배열에 집어넣기
        for (int i = 0; i < numbers.length; i++) {
            strNumbers[i] = String.valueOf(numbers[i]);
        }

        //정렬 (기준: 큰숫자를 만들 수 있도록)
        Arrays.sort(strNumbers, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return (o2+o1).compareTo(o1+o2);
            }
        });

        // 모든 숫자가 0인 경우를 처리
        if (strNumbers[0].equals("0")) {
            return "0";
        }        
        
        for (String str: strNumbers) {
            answer+=str;
        }
        return answer;
    }

}