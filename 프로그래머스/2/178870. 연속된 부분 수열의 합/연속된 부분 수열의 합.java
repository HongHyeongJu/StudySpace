class Solution {
   public int[] solution(int[] sequence, int k) {
        int n = sequence.length;
        //투포인터 방법 사용
        int start = 0, end = 0;
        int sum = 0;
        int minLength = Integer.MAX_VALUE;
       
        //정답 배열
        int[] result = new int[2];

        // 투 포인터 알고리즘 시작
        while (end < n) {
            
            // end 포인터가 가리키는 요소를 sum에 더하기
            sum += sequence[end];

            // sum이 k 이상일 때까지 start를 이동
            while (sum >= k) {
                if (sum == k) {
                    // 현재 부분 수열의 길이
                    int length = end - start + 1;
                    if (length < minLength) {
                        minLength = length;
                        result[0] = start;
                        result[1] = end;
                    }
                }
                sum -= sequence[start++];
            }
            end++;
        }

        return result;
    }
}

//
//수열 가능한 예시 DFS 찾으면서 타겟 값과 같은지 비교
//찾은 조합 List에 담기, 찾은 순서도
//가장 짧은 수열 
//여러개면 찾은 순서가 앞인 것
