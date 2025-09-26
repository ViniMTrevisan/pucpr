public class AVLTree {
    public static class Node {
        int val, height;
        Node left, right;

        Node(int val) { this.val = val; height = 1; }
    }

    Node root;

    private int height(Node n) { return n == null ? 0 : n.height; }

    private int balanceFactor(Node n) { return n == null ? 0 : height(n.left) - height(n.right); }

    private Node rotateRight(Node y) {
        Node x = y.left;
        Node T2 = x.right;
        x.right = y;
        y.left = T2;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        return x;
    }

    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node T2 = y.left;
        y.left = x;
        x.right = T2;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        return y;
    }

    public void insert(int val) {
        root = insertRec(root, val);
    }

    private Node insertRec(Node node, int val) {
        if (node == null) return new Node(val);
        if (val < node.val) node.left = insertRec(node.left, val);
        else node.right = insertRec(node.right, val);

        node.height = 1 + Math.max(height(node.left), height(node.right));
        int balance = balanceFactor(node);

        if (balance > 1 && val < node.left.val) return rotateRight(node);
        if (balance < -1 && val > node.right.val) return rotateLeft(node);
        if (balance > 1 && val > node.left.val) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        if (balance < -1 && val < node.right.val) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        return node;
    }

    public void remove(int val) {
        root = removeRec(root, val);
    }

    private Node removeRec(Node node, int val) {
        if (node == null) return null;
        if (val < node.val) node.left = removeRec(node.left, val);
        else if (val > node.val) node.right = removeRec(node.right, val);
        else {
            if (node.left == null || node.right == null) {
                Node temp = node.left != null ? node.left : node.right;
                if (temp == null) {
                    node = null;
                } else node = temp;
            } else {
                Node temp = findMin(node.right);
                node.val = temp.val;
                node.right = removeRec(node.right, temp.val);
            }
        }

        if (node == null) return null;

        node.height = 1 + Math.max(height(node.left), height(node.right));
        int balance = balanceFactor(node);

        if (balance > 1 && balanceFactor(node.left) >= 0) return rotateRight(node);
        if (balance > 1 && balanceFactor(node.left) < 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        if (balance < -1 && balanceFactor(node.right) <= 0) return rotateLeft(node);
        if (balance < -1 && balanceFactor(node.right) > 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        return node;
    }

    private Node findMin(Node node) { while (node.left != null) node = node.left; return node; }

    public boolean contains(int val) {
        Node cur = root;
        while (cur != null) {
            if (val == cur.val) return true;
            cur = val < cur.val ? cur.left : cur.right;
        }
        return false;
    }

    public Node getRoot() { return root; }
}
