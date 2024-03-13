import java.util.*;

class Solution {

    public int[] solution(String msg) {
        List<Integer> result  = new ArrayList<>();
        Map<String, Integer> dictionary = new HashMap<>();

        for (char ch = 'A'; ch <= 'Z'; ch++) {
            dictionary.put(String.valueOf(ch), ch - 'A' + 1);
        }

        for (int i = 0; i < msg.length(); ) {
            String chAt = "";
            int lastIndex = i + 1;  //subString에 추가 안되는 인덱스 값

            //현재 입력으로 시작하는 가장 긴 문자열 찾기
            //     인덱스 범위 벗어나지 않기
            while (lastIndex<=msg.length() && dictionary.containsKey(msg.substring(i,lastIndex))){
                chAt = msg.substring(i,lastIndex);
                //그다음 단어까지 찾아보자고~
                lastIndex++;
            }

            //현재 문자열의 색인 번호 찾아서 입력
            result.add(dictionary.get(chAt));
            
            //다음글자가 있고, 현재단어+다음글자를  사전에 추가
            //lastIndex++; 를 wile문 마지막에 이미 해줌
            if(lastIndex<=msg.length()){
                dictionary.put(msg.substring(i,lastIndex), dictionary.size()+1);
            }

            //i증감식  현재입력단어 길이만큼
            i += chAt.length();

        }


        return result.stream().mapToInt(Integer::intValue).toArray();
    }

}