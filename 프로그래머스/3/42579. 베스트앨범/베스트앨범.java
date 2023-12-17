import java.util.*;

class Solution {
    public int[] solution(String[] genres, int[] plays) {
        int[] answer;

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
                if(other.play==this.play){
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
        sortedGenres.sort((a, b) -> playCountMap.get(b).compareTo(playCountMap.get(a)));

        // 각 장르별 상위 두 곡 선택
        List<Integer> answerList = new ArrayList<>();
        for (String genre : sortedGenres) {
            List<Song> songs = songsListMap.get(genre);
            for (int i = 0; i < Math.min(songs.size(), 2); i++) {
                answerList.add(songs.get(i).id);
            }
        }

        return answerList.stream().mapToInt(a->a).toArray();
    }
}