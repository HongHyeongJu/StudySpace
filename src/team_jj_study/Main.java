package team_jj_study;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        st = new StringTokenizer(br.readLine());
        int number = Integer.parseInt(st.nextToken());

        System.out.println(solution(number));
    }

    public static int solution(int totalWeight){
        int cnt = 0;
        int share = totalWeight/5;
        int rest = totalWeight%5;

        switch(rest){
            case 0: cnt = share; break;
            case 1: cnt = (share - 1 >= 0) ? share - 1 + 2 : -1 ; break;
            case 2: cnt = (share - 2 >= 0) ? share - 2 + 4 : -1 ; break;
            case 3: cnt = share + 1 ; break;
            case 4: cnt = (share - 1 >= 0) ? share - 1 + 3 : -1 ; break;
        }

        return cnt;
    }


}
