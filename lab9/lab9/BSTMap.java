package lab9;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */
    private Object[] keyArray;

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        } else if (key.compareTo(p.key) == 0) {
            return p.value;
        } else if (key.compareTo(p.key) < 0) {
            return getHelper(key, p.left);
        } else {
            return getHelper(key, p.right);
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) { //有问题？？？？
        if (p == null) {
            size += 1;
            return new Node(key, value);
        }
        if (key.compareTo(p.key) == 0) {
            p.value = value;
        } else if (key.compareTo(p.key) < 0) {
            p.left = putHelper(key, value, p.left);
        } else {
            p.right = putHelper(key, value, p.right);
        }
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        root = putHelper(key, value, root);
        Set<K> keySet = keySet();
        keyArray = keySet.toArray();
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////
    private void keySetHelper(Set<K> key, Node p) {
        if (p == null) {
            return;
        }
        key.add(p.key);
        keySetHelper(key, p.left);
        keySetHelper(key, p.right);
    }

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        keySetHelper(keySet, root);
        return keySet;
    }

    /**
     * Returns a node with the maximum key in a subtree rooted from `node`
     */
    private Node maxNode(Node node) {
        if (node.right == null) {
            return node;
        } else {
            return maxNode(node.right);
        }
    }

    /**
     * 返回删除满足结点后的BST
     */
    private Node removeHelper(K key, Node node) {
        if (key.compareTo(node.key) == 0) {
            if (node.left == null && node.right == null) {
                return null;
            } else if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else { // 找出左子树中key值最大的结点，将右子树连接到该结点，就可以达到删除该结点的目的
                Node maxNodeOnleft = maxNode(node.left);
                K maxKeyOnleft = maxNodeOnleft.key;
                V maxValueOnleft = maxNodeOnleft.value;
                node.key = maxKeyOnleft;
                node.value = maxValueOnleft;
                node.left = removeHelper(maxKeyOnleft, node.left); // 将左子树中最大的结点删除
                return node;
            }
        }
        if (key.compareTo(node.key) < 0) {
            node.left = removeHelper(key, node.left);
        } else {
            node.right = removeHelper(key, node.right);
        }
        return node;
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        V removed = get(key);
        if (removed == null) {
            return null;
        }
        root = removeHelper(key, root);
        size -= 1;
        Set<K> keySet = keySet();
        keyArray = keySet.toArray();
        return removed;
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        V removed = get(key);
        if (removed != value || removed == null) {
            return null;
        }
        size -= 1;
        root = removeHelper(key, root);
        Set<K> keySet = keySet();
        keyArray = keySet.toArray();
        return removed;
    }

    private class BSTMapIterator implements Iterator<K> {
        private int pos;

        public BSTMapIterator() {
            pos = 0;
        }

        @Override
        public boolean hasNext() {
            return pos < keyArray.length;
        }

        @Override
        public K next() { //创建一个Arraylist，将keySet中保存的key值存储在Arraylist中，以便于根据索引迭代
            K returnItem = (K) keyArray[pos];
            pos += 1;
            return returnItem;
        }
    }

    @Override
    //iterator is a method that return returns an iterator object
    public Iterator<K> iterator() {
        return new BSTMapIterator();
    }

    public static void main(String[] args) {
        BSTMap<String, Integer> bstmap = new BSTMap<>();
        bstmap.put("hello", 5);
        bstmap.put("cat", 10);
        bstmap.put("fish", 22);
        bstmap.put("zebra", 90);

        Iterator a = bstmap.iterator();

        for (String key : bstmap) {
            System.out.println(key); // ???? BSTMap的iterator有问题
        }

        System.out.println("________");
        System.out.println(bstmap.size);

        while (a.hasNext()) {
            System.out.println(a.next());
        }
    }
}
