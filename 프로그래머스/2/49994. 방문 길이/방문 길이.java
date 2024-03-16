import java.util.HashSet;

class Solution {
    public int solution(String dirs) {
        HashSet<String> xyStringSet = new HashSet<>();
        int x = 0;
        int y = 0;

        for (char currentChar: dirs.toCharArray()) {
            int nx = x;
            int ny = y;
            
            switch (currentChar) {
                case 'U' : if(y<5) ny++; break;
                case 'D' : if(y>-5) ny--; break;
                case 'R' : if(x<5) nx++; break;
                case 'L' : if(x>-5) nx--; break;
            }
            
            //움직임 있음
            if(nx!=x || ny!=y){
                String path1 = x+":"+y+"->"+nx+":"+ny;
                String path2 = nx+":"+ny+"->"+x+":"+y;
                xyStringSet.add(path1);
                xyStringSet.add(path2);
                
                x=nx;
                y=ny;
            }
            
            
        }

        return xyStringSet.size()/2;
    }


}