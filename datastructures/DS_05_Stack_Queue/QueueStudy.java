package DS_05_Stack_Queue;
import java.util.LinkedList;
import java.util.Queue;

public class QueueStudy {
    public static void main(String[] args) {
        Queue<String> printerQueue = new LinkedList<>();

        // 인쇄 대기열에 문서 추가
        printerQueue.offer("문서1");
        printerQueue.offer("문서2");
        printerQueue.offer("문서3");

        // 인쇄 대기열에서 문서 인쇄
        while (!printerQueue.isEmpty()) {
            String currentDoc = printerQueue.poll();
            System.out.println(currentDoc + " 인쇄 중...");

            // 인쇄 프로세스를 시뮬레이션하기 위한 대기 시간
            try {
                Thread.sleep(1000); // 1초 대기
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("모든 문서 인쇄 완료.");


    }
}
