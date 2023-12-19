
## 완주 못한 선수
```java

class Solution {
    public String solution(String[] participant, String[] completion) {
        String answer = "";
        HashMap<String, Integer> map = new HashMap<>();

        // participant 배열을 순회하며 이름별 카운트 증가
        for (String player : participant) {
            map.put(player, map.getOrDefault(player, 0) + 1);
        }

        // completion 배열을 순회하며 완주한 선수의 카운트 감소
        for (String player : completion) {
            map.put(player, map.get(player) - 1);
        }

        // 완주하지 못한 선수 찾기
        for (String key : map.keySet()) {
            if (map.get(key) > 0) {
                answer = key;
                break;
            }
        }
        return answer;
    }
}
```

<br>

## 데려갈 수 있는 포켓몬 수
```java
import java.util.HashSet;

import static java.lang.Integer.parseInt;

class Solution {
    public int solution(int[] nums) {
         HashSet<Integer> types = new HashSet<>();
        
        for(int num : nums) {
            types.add(num);
        }
        
        return Math.min(nums.length / 2, types.size());
    }
}

```



<br>

## 전화번호 목록

```java
import java.util.Arrays;
class Solution {
    public boolean solution(String[] phone_book) {
        // 전화번호부 정렬
        Arrays.sort(phone_book);

        // 각 번호를 다음 번호와 비교
        for (int i = 0; i < phone_book.length - 1; i++) {
            if (phone_book[i + 1].startsWith(phone_book[i])) {
                return false;
            }
        }

        return true;
    }
    
}
```






<br>

## 의상 종류
```java
import java.util.*;

import static java.lang.Integer.parseInt;

class Solution {

    public static void main(String[] args) {
        Solution sol = new Solution();
        String[][] arr = {{"yellow_hat", "headgear"}, {"blue_sunglasses", "eyewear"}, {"green_turban", "headgear"}};
        System.out.println(sol.solution(arr));;

    }

    public int solution(String[][] clothes) {
        int answer = 0;
        // 받은 행렬의 인덱스 1을 기준으로 종류 찾기. 개수*개수*개수 -1
        HashMap<String,Integer> map = new HashMap<>();
        for(String[] clothe : clothes){
            map.put(clothe[1],map.getOrDefault(clothe[1],0)+1);
        }
        int combinations = 1;
        for (int count : map.values()) {
            combinations *= (count + 1); // 각 종류별 옷을 입거나 입지 않는 경우
        }

        return combinations - 1; // 아무것도 입지 않는 경우 제외
        }




}

```






<br>

## 베스트 앨범
```java

import java.util.*;

import static java.lang.Integer.parseInt;

class Solution {

    public static void main(String[] args) {
        Solution sol = new Solution();
        String[] genres = {"classic", "pop", "classic", "classic", "pop"};
        int[] plays = {500, 600, 150, 800, 2500};

        sol.solution(genres, plays);
    }

    public int[] solution(String[] genres, int[] plays) {
        int[] answer;

        //음악 저장할 객체
        class Song implements Comparable<Song> {
            String type;
            int id;
            int play;

            Song(String type, int id, int play) {
                this.type = type;
                this.id = id;
                this.play = play;
            }

            @Override
            public int compareTo(Song other) {
                if(other.play==this.play){ // 재생 횟수 같으면 고유 아이디 오름차순 정렬
                    return this.id-other.id;
                }
                return other.play - this.play; // 재생 횟수에 따라 내림차순 정렬
            }

        }

        Map<String, Integer> playCountMap = new HashMap<>();
        Map<String, List<Song>> songsListMap = new HashMap<>();

        // 장르별 총 재생 횟수 계산 및 노래 목록 생성
        for (int i = 0; i < genres.length; i++) {
            playCountMap.put(genres[i], playCountMap.getOrDefault(genres[i], 0) + plays[i]);

            List<Song> genreSongs = songsListMap.getOrDefault(genres[i], new ArrayList<>());
            genreSongs.add(new Song(genres[i], i, plays[i]));
            songsListMap.put(genres[i], genreSongs);
        }

        // 각 장르별 노래 목록 정렬
        for (List<Song> songs : songsListMap.values()) {
            Collections.sort(songs);
        }

        // 장르별 총 재생 횟수에 따라 정렬
        List<String> sortedGenres = new ArrayList<>(playCountMap.keySet());
        //기본은 오름차순 정렬. 
        // 그러나 값을 (a, b)이런 순서로 제공하는데  비교할때는 b에 a를 대입해서(순서를 바꿔서) 비교하므로 내림차순 정렬됨
        sortedGenres.sort((a, b) -> playCountMap.get(b).compareTo(playCountMap.get(a)));

        // 각 장르별 상위 두 곡 선택
        List<Integer> answerList = new ArrayList<>();
        //장르별 재생횟수 높은 것부터 반복해서 이걸 key로 map에서 객체 꺼내기
        for (String genre : sortedGenres) {
            //해당 장르의 List 꺼내면 받을 공간 준비 List<Song>
            List<Song> songs = songsListMap.get(genre);
            //Math.min(songs.size(), 2)  0이나 1을 대비함. 아니면   최대 2개 
            for (int i = 0; i < Math.min(songs.size(), 2); i++) {
                answerList.add(songs.get(i).id);
            }
        }

        return answerList.stream().mapToInt(a->a).toArray();
    }


}

```