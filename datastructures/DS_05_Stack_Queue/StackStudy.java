package DS_05_Stack_Queue;
import java.util.Stack;

// 문서 이력관리
public class StackStudy {
    private Stack<String> history;

    public StackStudy() {
        history = new Stack<>();
    }

    // 새로운 변경 사항 추가
    public void addChange(String state) {
        history.push(state);
    }

    // 최근 변경 사항 되돌리기
    public String undoChange() {
        return history.isEmpty() ? "No changes to undo" : history.pop();
    }

    // 현재 문서 상태 보기
    public String getCurrentState() {
        return history.isEmpty() ? "No changes made yet" : history.peek();
    }
}

class Main {
    public static void main(String[] args) {


        StackStudy documentHistory = new StackStudy();

        documentHistory.addChange("문서 초안");
        documentHistory.addChange("문서 수정");
        documentHistory.addChange("문서 작성 완료");

        // 현재 문서 상태 확인
        System.out.println("Current state: " + documentHistory.getCurrentState());

        // 변경 사항 되돌리기
        System.out.println("Undoing change: " + documentHistory.undoChange());
        System.out.println("Current state: " + documentHistory.getCurrentState());
    }
}

