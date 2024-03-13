
import java.util.*;

class Solution {


    public int solution(String str1, String str2) {

        //소문자 변환
        str1 = str1.toLowerCase();
        str2 = str2.toLowerCase();

        HashMap<String,Integer> map1 = new HashMap<>();
        HashMap<String,Integer> map2 = new HashMap<>();

        Set<String> set = new HashSet<>();

        for (int i = 0; i < str1.length() -1 ; i++) {
            String subStr = str1.substring(i,i+2);
            if(Character.isAlphabetic(subStr.charAt(0))&&
                Character.isAlphabetic(subStr.charAt(1))){
                map1.put(subStr, map1.getOrDefault(subStr, 0)+1);
                set.add(subStr);
            }
        }

        for (int i = 0; i < str2.length() -1 ; i++) {
            String subStr = str2.substring(i,i+2);
            if(Character.isAlphabetic(subStr.charAt(0))&&
                Character.isAlphabetic(subStr.charAt(1))){
                map2.put(subStr, map2.getOrDefault(subStr, 0)+1);
                set.add(subStr);
            }
        }

        int unionCount = 0;
        for (String str : set) {
            unionCount += Math.max(map1.getOrDefault(str, 0),map2.getOrDefault(str, 0));
        }

        int intersect = 0;
        for (String str : map1.keySet()) {
            if(map2.containsKey(str)) {
                intersect += Math.min(map1.get(str), map2.get(str));
            }
        }

        // 자카드 유사도 계산
        double jaccard = unionCount==0 ? 1 : (double) intersect / unionCount;


        return (int) (jaccard * 65536);
    }


}