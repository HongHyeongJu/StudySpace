class Solution {
    boolean solution(String s) {
        //단어 소문자로 변경
        String word = s.toLowerCase();
        
        //int pCount, yCount
        int pCount = 0;
        int yCount = 0;

        for (int i = 0; i < word.length(); i++) {
            if(word.charAt(i)=='y') yCount++;
            if(word.charAt(i)=='p') pCount++;
        }

        return pCount==yCount;
    }
    
}