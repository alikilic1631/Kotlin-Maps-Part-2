package maps;

import org.jetbrains.annotations.Nullable;

public class TreeMapNode<K, V> {

    private K key;
    private V value;
    private TreeMapNode<K, V> leftNode;
    private TreeMapNode<K, V> rightNode;

    public TreeMapNode(K key,V value, TreeMapNode<K, V> left, TreeMapNode<K, V> right) {
        this.key = key;
        this.value = value;
        this.leftNode = left;
        this.rightNode = right;
    }

    public void setKey(K key){
        this.key = key;
    }

    public void setValue(V value){
        this.value = value;
    }

    public void setLeft(TreeMapNode<K, V> left) {
        this.leftNode = left;
    }

    public void setRight(TreeMapNode<K, V> right) {
        this.rightNode = right;
    }

    public TreeMapNode<K, V> getLeft(){
        return this.leftNode;
    }

    public TreeMapNode<K, V> getRight(){
        return this.rightNode;
    }

    public K getKey(){
        return this.key;
    }

    public V getValue(){
        return this.value;
    }




}
