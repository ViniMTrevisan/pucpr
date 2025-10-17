import java.util.*;

public class BST {
    Node root;

    public void insert(int key) {
        root = insertRec(root, key);
    }

    private Node insertRec(Node node, int key) {
        if (node == null) return new Node(key);
        if (key < node.key) node.left = insertRec(node.left, key);
        else if (key > node.key) node.right = insertRec(node.right, key);
        return node;
    }

    public void remove(int key) {
        root = removeRec(root, key);
    }

    private Node removeRec(Node node, int key) {
        if (node == null) return null;
        if (key < node.key) node.left = removeRec(node.left, key);
        else if (key > node.key) node.right = removeRec(node.right, key);
        else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            Node succ = minNode(node.right);
            node.key = succ.key;
            node.right = removeRec(node.right, succ.key);
        }
        return node;
    }

    private Node minNode(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    public void printTree() {
        printRec(root, "", true);
    }

    private void printRec(Node node, String prefix, boolean isTail) {
        if (node == null) return;
        if (node.right != null) printRec(node.right, prefix + (isTail ? "\t" : "|\t"), false);
        System.out.println(prefix + (isTail ? "└── " : "┌── ") + node.key);
        if (node.left != null) printRec(node.left, prefix + (isTail ? "\t" : "|\t"), true);
    }

    public int height() {
        return heightRec(root);
    }

    private int heightRec(Node node) {
        if (node == null) return -1; 
        return 1 + Math.max(heightRec(node.left), heightRec(node.right));
    }

    public Map<Integer, Integer> depths() {
        Map<Integer, Integer> map = new LinkedHashMap<>();
        depthsRec(root, 0, map);
        return map;
    }

    private void depthsRec(Node node, int d, Map<Integer,Integer> map) {
        if (node == null) return;
        map.put(node.key, d);
        depthsRec(node.left, d+1, map);
        depthsRec(node.right, d+1, map);
    }

    public List<Integer> parents() {
        List<Integer> res = new ArrayList<>();
        parentsRec(root, res);
        return res;
    }

    private void parentsRec(Node node, List<Integer> res) {
        if (node == null) return;
        if (node.left != null) res.add(node.key);
        if (node.right != null) res.add(node.key);
        parentsRec(node.left, res);
        parentsRec(node.right, res);
    }

    public List<Integer> children() {
        List<Integer> res = new ArrayList<>();
        childrenRec(root, res);
        return res;
    }

    private void childrenRec(Node node, List<Integer> res) {
        if (node == null) return;
        if (node.left != null) res.add(node.left.key);
        if (node.right != null) res.add(node.right.key);
        childrenRec(node.left, res);
        childrenRec(node.right, res);
    }

    public List<Integer> leaves() {
        List<Integer> res = new ArrayList<>();
        leavesRec(root, res);
        return res;
    }

    private void leavesRec(Node node, List<Integer> res) {
        if (node == null) return;
        if (node.left == null && node.right == null) res.add(node.key);
        leavesRec(node.left, res);
        leavesRec(node.right, res);
    }
}
