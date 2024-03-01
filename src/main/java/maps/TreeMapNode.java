package maps;

import org.jetbrains.annotations.Nullable;

public class TreeMapNode<K, V> {
    public K key;
    public V value;

    @Nullable
    public TreeMapNode<K, V> leftNode;

    @Nullable
    public TreeMapNode<K, V> rightNode;

    void setLeft(TreeMapNode<K, V> left) {
        this.leftNode = left;
    }

    void setRight(TreeMapNode<K, V> right) {
        this.rightNode = right;
    }

    @Nullable
    TreeMapNode<K, V> getLeft() {
        return this.leftNode;
    }

    @Nullable
    TreeMapNode<K, V> getRight() {
        return this.rightNode;
    }

    K getKey() {
        return this.key;
    }

    V getValue() {
        return this.value;
    }

    void setKey(K nodeKey) {
        this.key = nodeKey;
    }

    void setValue(V nodeValue) {
        this.value = nodeValue;
    }
}
