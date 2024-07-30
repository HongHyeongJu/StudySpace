package team_jj_study.july2024;

import static java.lang.Integer.parseInt;

public class Study5073 {

    public static void test() {
        String str = "6 3 2";
        System.out.println(solution(str));
    }

    public static String solution(String str){
        String[] strArr = str.split(" ");
        int length1 = parseInt(strArr[0]);
        int length2 = parseInt(strArr[1]);
        int length3 = parseInt(strArr[2]);

        //000 체크
        if(length1==0 && length2==0 && length3==0){
            return null;
        }

        //삼각형 되는지 판별
        int maxLength = Integer.max(length1, length2);
        maxLength = Integer.max(maxLength, length3);
        int elseLength = length1+length2+length3 - maxLength;
        if(maxLength>=elseLength){
            return "Scalene";
        } //어떤 삼각형인지 판별
          else if(length1==length2 && length1==length3){ //세 변의 길이가 같을 때
            return "Equilateral";
        } else if(length1==length2 && length1!=length3 || length2==length3 && length1!=length2 || length1==length3 && length1!=length2){ //두변만 같을 때
            return "Isosceles";
        } else if (length1!=length2 && length2!=length3 && length3!=length1) { //세변의 길이가 모두 다를 때
            return "Scalene";
        }

        return null;
    }


}

