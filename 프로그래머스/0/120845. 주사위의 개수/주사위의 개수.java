class Solution {
    public int solution(int[] box, int n) {
        int answer = 0;

        // 주사위보다 작으면 return 0
        if(box[0]<n || box[1]<n || box[2]<n ) return 0;

        //1층 가능 개수
        int w = box[0]/n;
        int l = box[1]/n;
        int firstFloor = w*l;
        
        int h = box[2]/n;
               

        return firstFloor*h;
    }
}