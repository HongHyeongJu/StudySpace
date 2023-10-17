class Solution {
    public String solution(String my_string) {
        String answer = "";
        int strLen = my_string.length();
        char[] charArr = new char[strLen];
        for(int i=0;i<strLen;i++){
            charArr[i]=my_string.charAt(strLen-i-1);
        }
        return new String(charArr);
    }
}