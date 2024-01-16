class Solution {
   public int solution(int m, int n, int[][] puddles) {
        int answer = 0;

        //가도되는 길인지 표시할 배열. 일단 다 0으로 채우기
        int[][] dpArr = new int[m + 1][n + 1];
//        Arrays.fill(checkArr,0);

        //물웅덩이 체크 [[2, 2]]  물웅덩이는 -1
        for (int[] pArr : puddles) {
            dpArr[pArr[0]][pArr[1]] = -1;
        }

        //시작 표시
        dpArr[1][1] = 1;


        //현재 칸에 오기까지 가능한 경우의 수 저장하기
        for (int x = 1; x <= m; x++) {
            for (int y = 1; y <= n; y++) {
                //시작점이거나 물웅덩이면 넘어가자
                if((x==1&&y==1)||dpArr[x][y]==-1){
                    continue;
                }

                //아니면 올 수 있는 경우의 수 다 더해주세요
            // 모듈러 연산 추가
            if (x > 1 && dpArr[x-1][y] != -1) dpArr[x][y] = (dpArr[x][y] + dpArr[x-1][y]) % 1000000007;
            if (y > 1 && dpArr[x][y-1] != -1) dpArr[x][y] = (dpArr[x][y] + dpArr[x][y-1]) % 1000000007;

            }
        }


        return dpArr[m][n];
    }
}