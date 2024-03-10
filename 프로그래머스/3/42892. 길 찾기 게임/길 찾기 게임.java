
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {

    List<Integer> preorderList = new ArrayList<>();
    List<Integer> postorderList = new ArrayList<>();

    public int[][] solution(int[][] nodeinfo) {
        Node[] nodes = new Node[nodeinfo.length];

        for (int i = 0; i < nodeinfo.length; i++) {
            nodes[i] = new Node(i + 1, nodeinfo[i][0], nodeinfo[i][1]);
        }
        Arrays.sort(nodes);

        Node root = nodes[0];

        for (int i = 1; i < nodes.length; i++) {
            //addNode로 이진트리 완성하기
            addNode(root, nodes[i]);
        }

        /*-----이진트리 완성-----*/

        //전위순회
        preorder(root);
        //후위순회
        postorder(root);

        int[][] answer = new int[2][nodeinfo.length]; //전위순회, 후위순회 list담을 것임
        for (int i = 0; i < nodeinfo.length; i++) {
            answer[0][i] = preorderList.get(i);
            answer[1][i] = postorderList.get(i);
        }



        return answer;
    }

    void preorder(Node node) {
        if (node == null) return;
        preorderList.add(node.num);
        preorder(node.left);
        preorder(node.right);
    }

    void postorder(Node node) {
        if (node == null) return;
        postorder(node.left);
        postorder(node.right);
        postorderList.add(node.num);
    }


    void addNode(Node parent, Node child) {
        //x값 비교
        if (child.x < parent.x) {
            //parent의 left비었으면 바로 넣기
            if (parent.left == null) parent.left = child;
            //비어있지 않으면 재귀
            else addNode(parent.left, child);
        } else {
            //parent의 right 비었으면 바로 넣기
            if (parent.right == null) parent.right = child;
            //비어있지 않으면 재귀
            else addNode(parent.right, child);
        }
    }

    class Node implements Comparable<Node> {
        int num, x, y;
        Node left, right;

        Node(int num, int x, int y) {
            this.num = num;
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Node other) {
            if (this.y == other.y) {
                return this.x - other.x;
            }
            return other.y - this.y;
        }
    }

}