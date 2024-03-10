
import java.util.*;
class Solution {

    public int[] solution(int n, String[] words) {

        //단어 Set
        Set<String> wordSet = new HashSet<>();
        wordSet.add(words[0]);

        //마지막 단어 ""
        String lastWord = words[0];

        for (int i = 1; i < words.length; i++) {
            //return조건 : 이미 나온 단어 || 끝말잇기 조건 충족 X
            if(wordSet.contains(words[i]) || lastWord.charAt(lastWord.length()-1)!=words[i].charAt(0)){
                int personNumber = (i % n) + 1; // 탈락자 번호
                int turn = (i / n) + 1; // 탈락자의 차례
                return new int[]{personNumber , turn };
            } else {
                lastWord = words[i];
                wordSet.add(words[i]);
            }
        }

        return new int[] {0,0};

    }

}