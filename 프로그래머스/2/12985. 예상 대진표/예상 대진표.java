class Solution
{
    public int solution(int n, int a, int b)
    {
        int round = 0;

        while (a != b){
           a = (a + 1) / 2; // A의 다음 라운드 번호
            b = (b + 1) / 2; // B의 다음 라운드 번호
            round++; // 라운드 증가
        }


        return round;
    }
}