public class UnbalancedBST {
    public static class Node {
        int val;
        Node left, right;

        Node(int val) {
            this.val = val;
        }
    }

    Node root;

    public void insert(int val) {
        root = insertRec(root, val);
    }

    private Node insertRec(Node node, int val) {
        if (node == null) return new Node(val);
        if (val < node.val) node.left = insertRec(node.left, val);
        else node.right = insertRec(node.right, val);
        return node;
    }

    public boolean contains(int val) {
        Node cur = root;
        while (cur != null) {
            if (val == cur.val) return true;
            cur = val < cur.val ? cur.left : cur.right;
        }
        return false;
    }

    public void remove(int val) {
        root = removeRec(root, val);
    }

    private Node removeRec(Node node, int val) {
        if (node == null) return null;
        if (val < node.val) node.left = removeRec(node.left, val);
        else if (val > node.val) node.right = removeRec(node.right, val);
        else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            Node min = findMin(node.right);
            node.val = min.val;
            node.right = removeRec(node.right, min.val);
        }
        return node;
    }

    private Node findMin(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    public Node getRoot() {
        return root;
    }
}
