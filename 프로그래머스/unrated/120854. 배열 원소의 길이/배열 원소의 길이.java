class Solution {
    public int[] solution(String[] strlist) {
                int strArrLen = strlist.length;
        int[] answer = new int[strArrLen];

        
        for(int i=0; i<strArrLen ; i++)
        {
            answer[i]=strlist[i].length();
        }
        return answer;
    }
}