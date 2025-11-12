public class BSTDemo {
    static class Node{String key; Node left,right; Node(String k){key=k;}}
    static class BST{
        Node root;
        void insert(String k){root=insertRec(root,k);}        
        Node insertRec(Node node,String k){
            if(node==null) return new Node(k);
            if(k.compareTo(node.key)<=0) node.left=insertRec(node.left,k);
            else node.right=insertRec(node.right,k);
            return node;
        }
        boolean search(String k){return searchRec(root,k);}        
        boolean searchRec(Node node,String k){
            if(node==null) return false;
            int cmp=k.compareTo(node.key);
            if(cmp==0) return true;
            if(cmp<0) return searchRec(node.left,k);
            return searchRec(node.right,k);
        }
    }
    public static void main(String[] args){
        String[] keys={"Ana","Pedro","Maria","João","Lucas","Beatriz","Rafael","Carlos","Sofia","Marcos"};
        BST bst=new BST();
        for(String k:keys) bst.insert(k);
        long t1=System.nanoTime();
        boolean found=bst.search("Maria");
        long t2=System.nanoTime();
        java.util.TreeSet<String> tree=new java.util.TreeSet<>();
        for(String k:keys) tree.add(k);
        long t3=System.nanoTime();
        boolean found2=tree.contains("Maria");
        long t4=System.nanoTime();
        System.out.println("BST busca Maria: "+found+" tempo ns: "+(t2-t1));
        System.out.println("TreeSet busca Maria: "+found2+" tempo ns: "+(t4-t3));
    }
}
