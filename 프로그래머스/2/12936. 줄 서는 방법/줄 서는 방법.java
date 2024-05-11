import java.util.*;
class Solution {
    public int[] solution(int n, long k) {
        int[] answer = new int[n]; 
        //방법 저장 리스트
        List<Integer> numbers = new ArrayList<>();
        //팩토리얼 저장소
        long[] factorials = new long[n + 1];
        
        // 팩토리얼 배열을 미리 계산해두기
        factorials[0] = 1;
        for (int i = 1; i <= n; i++) {
            factorials[i] = factorials[i - 1] * i;
        }      
        
        // 숫자 리스트 채워넣기 1부터 n까지
        for (int i = 1; i <= n; i++) {
            numbers.add(i);
        }
        
        // k를 인덱스로 쓰려면 하나 빼기
        k--;
        
        // k에 따라 숫자를 선택
        for (int i = 0; i < n; i++) {
            // i : , 순열의 각 위치에 적합한 숫자를 선택
            int index = (int) (k / factorials[n - 1 - i]); 
                                //factorials[n - 1 - i] : 현재 단계에서 찾을 수 있는 조합
            answer[i] = numbers.get(index); //현재 단계에서 선택된 숫자를 가져와서 answer 배열의 해당 위치에 할당
            numbers.remove(index);  //사용한 숫자는 지우기
            
            // 남은 순열 중에서 현재 위치를 기준으로 k 값을 조정하기
            //index는 현재 선택한 숫자의 위치
            k -= index * factorials[n - 1 - i];
        }
        
        return answer;
    }
}