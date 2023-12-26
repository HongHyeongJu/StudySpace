class Solution {

    public int solution(int[][] sizes) {
        int answer = 0;

        //출력확인
//        for (int[] arr: sizes) {
//            System.out.println(arr[0]+","+arr[1]);
//        }
//        System.out.println("======================");

        //[0]이 [1]보다 작게 정렬
        for (int i = 0; i < sizes.length; i++) {
            int first = sizes[i][0];
            int second = sizes[i][1];
            if(first>second) { sizes[i] = new int[]{second,first};}

        }
        //출력확인
//        for (int[] arr: sizes) {
//            System.out.println(arr[0]+","+arr[1]);
//        }

        //[0]중에 최고값 찾기  [1]중에 최고값 찾기
        int maxA = sizes[0][0];
        int maxB = sizes[0][1];

        for (int i = 0; i < sizes.length; i++) {
            if(maxA<sizes[i][0]) {maxA=sizes[i][0];}
            if(maxB<sizes[i][1]) {maxB=sizes[i][1];}
        }

        return maxA*maxB;
    }

}