package maps;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;


public class TreeBasedMap<K, V> implements CustomMutableMap<K, V> {

    public TreeMapNode<K, V> rootNode;

    public Comparator<K> keyComparator;

    public TreeBasedMap(Comparator<K> keyComp, TreeMapNode<K, V> root) {
        this.keyComparator = keyComp;
        this.rootNode = root;

    }

    @Override
    public Iterable<Entry<K, V>> getEntries() {


    }

    private getEntriesHelper

    @Override
    public Iterable<K> getKeys() {

    }

    @Override
    public Iterable<V> getValues() {

    }


    @Nullable
    @Override
    public V set(K key, V value) {
        TreeMapNode<K,V> parent = null;
        TreeMapNode<K,V> current = this.rootNode;
        while(current != null){
            int compVal = keyComparator.compare(key, current.getKey());
            switch (compVal) {
                case 0 :
                    V oldVal = current.getValue();
                    current.setValue(value);
                    return oldVal;
                case 1 :
                    parent = current;
                    current = parent.getRight();
                case -1:
                    parent = current;
                    current = parent.getLeft();
            }
        }

        if (keyComparator.compare(key, parent.getKey()) == 1){
            parent.setRight(TreeMapNode<K,V>(key,value,null,null))
        }
        else{
            parent.setLeft(TreeMapNode<K,V>(key,value,null))
        }

        return null;
    }

    @Override
    public V put(K key, V value) {
        return this.set(key, value);
    }

    @Override
    public V get(K key) {
        return getHelper(key, this.rootNode);
    }

    @Nullable
    private V getHelper(K k, TreeMapNode<K, V> currNode) {
        int compVal = keyComparator.compare(k, currNode.getKey());
        return switch (compVal) {
            case 0 -> currNode.getValue();
            case 1 -> getHelper(k, currNode.getRight());
            case -1 -> getHelper(k, currNode.getLeft());
            default -> null;
        };
    }


    public V remove(K key) {
        TreeMapNode<K,V> parent = null;
        TreeMapNode<K,V> current = this.rootNode;
        while(current != null) {
            int compVal = keyComparator.compare(key, current.getKey());
            if (compVal == 1) {
                parent = current;
                current = parent.getRight();
            }
            else if(compVal == -1) {
                parent = current;
                current = parent.getLeft();
            }
            else {
                V oldVal = current.getValue();
                parent = deleteNode(current,parent);
                return oldVal;

            }
        }
        return null;
    }



    private TreeMapNode<K,V> deleteNode(TreeMapNode<K,V> deleted
            ,TreeMapNode<K,V> parent){
        int compVal = keyComparator.compare(deleted.getKey(), parent.getKey());

        TreeMapNode<K,V> tempParent  = parent;
        TreeMapNode<K,V> tempCurrent = null;

        if(deleted.leftNode == null && deleted.rightNode == null){
            switch(compVal){
                case 1:
                    parent.rightNode = null;
                case -1:
                    parent.leftNode = null;
            }
            return parent;
        }

        else if (deleted.leftNode == null && deleted.rightNode != null){
            switch(compVal){
                case 1:
                    parent.rightNode = deleted.rightNode;
                case -1:
                    parent.leftNode = deleted.rightNode;


            }
        }
        else if(deleted.leftNode != null && deleted.rightNode == null){
            switch(compVal){
                case 1:
                    parent.rightNode = deleted.leftNode;
                case -1:
                    parent.leftNode = deleted.leftNode;
            }
        }

        else{
            tempCurrent = deleted.leftNode;
        }

        boolean Condition = true;

        while(Condition){
            deleted
        }



    }





    @Override
    public boolean contains(K key) {
        TreeMapNode<K,V> current = this.rootNode;
        while(current != null){
            if(current.getKey() == key){
                return true;
            }
            else if(keyComparator.compare(key , current.getKey()) == 1){
                current = current.getRight();
            }
            else{
                current = current.getLeft();
            }
        }
        return false;
    }

    @Nullable
    @Override
    public V put(@NotNull Entry<K, V> entry) {
        return this.set(entry.getKey(),entry.getValue());
    }
}

