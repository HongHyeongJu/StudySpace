import java.util.*;

class Solution {
   public int solution(int n, int[] lost, int[] reserve) {

        //잃어버린 학생 set만들기
        Set<Integer> losStudent = new HashSet();
        for (int i:lost ) {
            losStudent.add(i);
        }

        Set<Integer> shareStudent = new HashSet();
        //여분 체육복 있는데 도난당했으면 넌 나가(네가 입어야지 누굴 빌려주니 ㅠ)
        for (int i: reserve ) {
            if (losStudent.contains(i)) {
                losStudent.remove(i); //네가 입어야지 누굴 빌려주니 나가라
            } else {
                shareStudent.add(i); //여분 체육복 있니? 친구 빌려주자
            }
        }


        //잃어버린애한테 빌려줄 사람(앞뒤) 있는지 shareStudent에서 찾고 있으면 빌려주고 빼버려!
        List<Integer> borrowed = new ArrayList<>(); //최종확인 List
        
        for (int i : losStudent) {
            if (shareStudent.contains(i - 1)) {
                borrowed.add(i);
                shareStudent.remove(i - 1);
            } else if (shareStudent.contains(i + 1)) {
                borrowed.add(i);
                shareStudent.remove(i + 1);
            }
        }

        //결국 못빌린 학생만 남아라 나와...
        for (int student : borrowed) {
            losStudent.remove(student);
        }

       

        return  n - losStudent.size();
    }



}