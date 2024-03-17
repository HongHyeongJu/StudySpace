
class Solution {
    public int solution(String skill, String[] skill_trees) {
        int answer = 0;

        for (String tree : skill_trees) {
            if (isValidSkillTree(skill, tree)) {
                answer++;
            }
        }
        return answer;
    }


    //확인하는 로직을 메서드로 빼기
    private boolean isValidSkillTree(String skill, String tree) {
        int skillIndex = 0; // 다음으로 필요한 스킬의 인덱스를 추적

        for (int i = 0; i < tree.length(); i++) {
            int pos = skill.indexOf(tree.charAt(i)); // 현재 스킬이 skill 문자열에서의 위치
            //if  "AECB"에서 A,E는 continue하고 C의 차례, "CBD"skill에서 0번째

            // 스킬트리에 있는 스킬이 선행 스킬 순서에 포함되지 않는 경우 무시
            if (pos == -1) {
                continue;
            }

            // 스킬트리의 스킬이 선행 스킬 순서에서 다음으로 필요한 스킬이 아니라면 false 반환
            if (pos != skillIndex) {
                return false;
            }
            // int skillIndex = 0; 과  int pos 같아야함.
            
            // 다음으로 필요한 스킬로 업데이트  // C확인했으면 이제 B 차례
            skillIndex++;
        }

        // 모든 검사를 통과했다면 true 반환
        return true;
    }
}
