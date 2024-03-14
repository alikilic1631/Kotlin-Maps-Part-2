package maps;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;
import java.util.stream.Stream;


public class TreeBasedMap<K, V> implements CustomMutableMap<K, V> {

    private final Comparator<K> keyComparator;
    private TreeMapNode<K, V> rootNode;


    private class EntriesIterator implements Iterator<Entry<K, V>> {

        private Deque<TreeMapNode<K, V>> stack;
        private TreeMapNode<K, V> current;

        public EntriesIterator() {
            current = rootNode;
            stack = new LinkedList<>();
        }

        @Override
        public boolean hasNext() {
            return (!(stack.isEmpty()) || (current != null));
        }

        @Override
        public Entry<K, V> next() {
            TreeMapNode<K, V> nextNode;
            while (current != null) {
                stack.push(current);
                current = current.getLeft();
            }
            nextNode = stack.pop();
            current = nextNode.getRight();

            return new Entry<>(nextNode.getKey(), nextNode.getValue());
        }
    }

    public TreeBasedMap(Comparator<K> keyComparator) {
        this.keyComparator = keyComparator;
    }

    @NotNull
    @Override
    public ArrayList<Entry<K, V>> getEntries() {
        ArrayList<Entry<K, V>> entries = new ArrayList<>();
        EntriesIterator entriesIterator = new EntriesIterator();

        while (entriesIterator.hasNext()) {
            entries.add(entriesIterator.next());
        }

        return entries;
    }

    @NotNull
    @Override
    public ArrayList<K> getKeys() {
        ArrayList<K> keys = new ArrayList<>();

        for (Entry<K, V> entry : getEntries()) {
            keys.add(entry.getKey());
        }
        return keys;
    }

    @NotNull
    @Override
    public Iterable<V> getValues() {
        ArrayList<V> values = new ArrayList<>();

        for (Entry<K, V> entry : getEntries()) {
            values.add(entry.getValue());
        }
        return values;
    }

    @Override
    public boolean contains(K key) {
        return get(key) != null;
    }

    @Nullable
    @Override
    public V get(K key) {
        TreeMapNode<K, V> currNode = this.rootNode;
        while (currNode != null) {
            switch (keyComparator.compare(currNode.getKey(), key)) {
                case (0):
                    return currNode.getValue();
                case (1):
                    currNode = currNode.getRight();
                case (-1):
                    currNode = currNode.getLeft();
            }
        }
        return null;
    }

    @Nullable
    @Override
    public V set(K key, V value) {
        TreeMapNode<K, V> newNode = new TreeMapNode<>(key, value
                , null, null);

        TreeMapNode<K, V> currNode = this.rootNode;
        V oldval = null;

        while (currNode != null) {
            switch (keyComparator.compare(currNode.getKey(), key)) {
                case (0):
                    newNode.setLeft(currNode.getLeft());
                    newNode.setRight(currNode.getRight());
                    oldval = currNode.getValue();
                    break;
                case (1):
                    if (currNode.getLeft() == null) {
                        currNode.setLeft(newNode);
                        return oldval;
                    } else {
                        currNode = currNode.getLeft();
                    }
                case (-1):
                    if (currNode.getRight() == null) {
                        currNode.setRight(newNode);
                        return oldval;
                    } else {
                        currNode = currNode.getRight();
                    }
            }
        }
        return oldval;
    }

    @Nullable
    @Override
    public V put(@NotNull Entry<K, V> entry) {
        return this.set(entry.getKey(), entry.getValue());
    }

    @Nullable
    @Override
    public V put(K key, V value) {
        return this.set(key, value);
    }

    @Nullable
    @Override
    public V remove(K key) {
        TreeMapNode<K, V> removed = null;
        TreeMapNode<K, V> currNode = this.rootNode;

        while (currNode != null) {
            switch (keyComparator.compare(currNode.getKey(), key)) {
                case (0):
                    removed = currNode;
                    break;
                case (1):
                    currNode = currNode.getRight();
                case (-1):
                    currNode = currNode.getLeft();
            }
        }
        if (removed == null) {
            return null;
        }

        V removedValue = removed.getValue();
        TreeMapNode<K, V> tempNode;

        if (removed.getLeft() != null) {
            tempNode = removed.getLeft();
            while (tempNode.getRight() != null) {
                tempNode = tempNode.getRight();
            }
            removed = tempNode;
            tempNode = null;
        } else if (removed.getRight() != null) {
            tempNode = removed.getRight();
            while (tempNode.getLeft() != null) {
                tempNode = tempNode.getLeft();
            }
            removed = tempNode;
            tempNode = null;

        } else {
            removed = null;
        }
        return removedValue;
    }


}


