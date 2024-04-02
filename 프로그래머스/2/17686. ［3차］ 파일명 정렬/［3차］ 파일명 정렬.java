
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class Solution {
    public String[] solution(String[] files) {
        String[] answer;
        List<FileName> fileList = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            fileList.add(new FileName(files[i], i));
        }
        Collections.sort(fileList);
        answer = fileList.stream().map(f -> f.toString()).toArray(String[]::new);
        return answer;
    }
    
    class FileName implements Comparable<FileName> {

        String head; //파일헤드
        String numberStr;
        int number; //숫자
        String tail; //마지막부분
        int seq; //입력순서

        public FileName(String fileName, int seq) {
            this.seq = seq;
            Matcher matcher = Pattern.compile("([a-zA-Z\\s.-]+)(\\d+)(.*)").matcher(fileName);
            if (matcher.find()) {
                this.head = matcher.group(1);
                this.numberStr = matcher.group(2); // 숫자 부분을 문자열로 저장
                this.number = Integer.parseInt(matcher.group(2));
                this.tail = matcher.group(3);
            }
        }
        @Override
        public int compareTo(FileName o) {
            int headCompare = this.head.toLowerCase().compareTo(o.head.toLowerCase());
            if (headCompare != 0) return headCompare;
            int numberCompare = Integer.compare(this.number, o.number);
            if (numberCompare != 0) {
                return numberCompare;
            }
            return Integer.compare(this.seq, o.seq);
        }

        public String toString() {
            return head + numberStr + tail;
        }
    }
    
}