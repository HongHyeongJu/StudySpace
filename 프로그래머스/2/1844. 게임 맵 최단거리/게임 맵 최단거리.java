import java.util.*;

class Solution {
    
    int rowLength;
    int colLength;

    public int solution(int[][] maps) {
        rowLength = maps.length;
        colLength = maps[0].length;

        //시작정점 큐에 넣으며 bfs 시작
        return bfs(0, 0, maps);
    }

    
    int bfs(int r, int c, int[][] maps) {
        Queue<int[]> queue = new LinkedList<>();

        queue.offer(new int[]{r, c});

        //상하좌우
        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, 1, 0, -1};

        while (!queue.isEmpty()) {
            //하나 꺼내기
            int[] curr = queue.poll();
            
            //값 정의하기
            int curRow = curr[0];
            int curCol = curr[1];

            
            for (int i = 0; i < 4; i++) {
                //다음방향 지정
                int nextRow = curRow + dx[i];
                int nextCol = curCol + dy[i];

                if (nextRow >= 0 && nextCol >= 0 
                    && nextRow < rowLength && nextCol < colLength
                    && maps[nextRow][nextCol] == 1) {
                        //큐에 넣고 
                        queue.offer(new int[]{nextRow, nextCol});
                        //현재 거리로 업데이트
                        maps[nextRow][nextCol] = maps[curRow][curCol] + 1;
                }
            }
        }

        // 목적지에 도달한 경우 해당 위치의 값 반환, 도달하지 못했으면 -1 반환
        return maps[rowLength - 1][colLength - 1] > 1 ? maps[rowLength - 1][colLength - 1] : -1;
    }

}
