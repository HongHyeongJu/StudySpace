class Solution {
    int[] fibonaciArr;
    public int solution(int n) {
        fibonaciArr = new int[n+1];

        fibonaciArr[0] = 0;
        fibonaciArr[1] = 1;

        for (int i = 2; i <=n ; i++) {
             fibonaciArr[i] = (fibonaciArr[i-1] % 1234567 + fibonaciArr[i-2] % 1234567) % 1234567;
        }

        return fibonaciArr[n] % 1234567;
    }
}