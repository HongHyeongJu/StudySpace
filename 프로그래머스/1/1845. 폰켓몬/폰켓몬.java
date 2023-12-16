import java.util.HashSet;

class Solution {
    public int solution(int[] nums) {
         HashSet<Integer> types = new HashSet<>();
        
        for(int num : nums) {
            types.add(num);
        }
        
        return Math.min(nums.length / 2, types.size());
    }
}