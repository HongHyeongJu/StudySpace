

public class Solution {
    
    public int solution(int[] info, int[][] edges) {
        int answer = 0;
        boolean[] visited = new boolean[info.length]; // 방문 배열 만들기

        // 초기 값 세팅
        visited[0] = true;

        // DFS 탐색 (초기 값은 최상위 노드를 탐색한 상태로 시작)
        answer = dfs(1, 0, visited, info, edges, answer);

        return answer;
    }
    
    
    public int dfs(int sheepCount, int wolfCount, boolean[] visited, int[] info, int[][] edges, int answer) {
        // Back Tracking 조건 (양이 잡아먹힘)
        if (sheepCount <= wolfCount) {
            return answer;
        }

        // 최대 값이라면 갱신
        answer = Math.max(answer, sheepCount);
        
        
        for(int[] edge : edges) {
            int parent = edge[0];
            int child = edge[1];           
            
            if(visited[parent] && !visited[child]){
                visited[child] = true;
                
                if(info[child]==0){
                    answer =  dfs(sheepCount+1, wolfCount, visited, info, edges, answer);
                } else {
                    answer =  dfs(sheepCount, wolfCount+1, visited, info, edges, answer);
                }
                
                visited[child] = false;
            }
        }      
            return answer;
        
    }
    
}