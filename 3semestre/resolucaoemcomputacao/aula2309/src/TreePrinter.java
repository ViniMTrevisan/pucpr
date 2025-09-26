import java.util.ArrayList;
import java.util.List;

public class TreePrinter {
    public static List<String> toLines(UnbalancedBST.Node root) {
        return toLines((node)-> node==null?null: new NodeWrapper(node.val, node.left, node.right), root);
    }

    public static List<String> toLines(AVLTree.Node root) {
        return toLines((node)-> node==null?null: new NodeWrapper(node.val, node.left, node.right), root);
    }

    private static <T> List<String> toLines(FunctionLike<T> mapper, T root) {
        NodeWrapper r = mapper.map(root);
        List<String> lines = new ArrayList<>();
        buildLines(r, lines, "", true);
        return lines;
    }

    private static void buildLines(NodeWrapper node, List<String> lines, String prefix, boolean isTail) {
        if (node == null) {
            lines.add(prefix + (isTail ? "└── " : "├── ") + "null");
            return;
        }
        if (node.right != null || node.left != null) {
            buildLines(node.rightWrapper(), lines, prefix + (isTail ? "    " : "│   "), false);
        }
        lines.add(prefix + (isTail ? "└── " : "├── ") + node.val);
        if (node.left != null || node.right != null) {
            buildLines(node.leftWrapper(), lines, prefix + (isTail ? "    " : "│   "), true);
        }
    }

    private static class NodeWrapper {
        int val;
        Object left, right;
        NodeWrapper(int val, Object left, Object right) { this.val = val; this.left = left; this.right = right; }
        NodeWrapper leftWrapper() { return wrap(left); }
        NodeWrapper rightWrapper() { return wrap(right); }
        private NodeWrapper wrap(Object o) {
            if (o == null) return null;
            if (o instanceof UnbalancedBST.Node) {
                UnbalancedBST.Node n = (UnbalancedBST.Node)o;
                return new NodeWrapper(n.val, n.left, n.right);
            }
            if (o instanceof AVLTree.Node) {
                AVLTree.Node n = (AVLTree.Node)o;
                return new NodeWrapper(n.val, n.left, n.right);
            }
            return null;
        }
    }

    private interface FunctionLike<T> { NodeWrapper map(T t); }
}
