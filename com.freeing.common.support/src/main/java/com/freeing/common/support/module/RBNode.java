package com.freeing.common.support.module;

/**
 * 红黑树节点
 *
 * @param <K> 权重
 * @param <V> 值
 * @author yanggy
 */
public class RBNode<K extends Comparable<K>, V>  {

    public RBNode() {

    }

   public RBNode(boolean color, K key, V value) {
        this(color, key, value, null, null, null);
    }

    public RBNode(boolean color, K key, V value, RBNode<K, V> parent) {
        this(color, key, value, parent, null, null);
    }

    public RBNode(boolean color, K key, V value, RBNode<K, V> parent, RBNode<K, V> left, RBNode<K, V> right) {
        this.color = color;
        this.key = key;
        this.value = value;
        this.parent = parent;
        this.left = left;
        this.right = right;
    }

    /**
     * 节点类型: true=黑节点，false=红节点
     */
    private boolean color;

    /**
     * 权值，节点的大小关系值
     */
    private K key;

    /**
     * 值
     */
    private V value;

    /**
     * 父节点
     */
    private RBNode<K, V> parent;

    /**
     * 左孩子
     */
    private RBNode<K, V> left;

    /**
     * 右孩子
     */
    private RBNode<K, V> right;

    public boolean color() {
        return color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public RBNode<K, V> getParent() {
        return parent;
    }

    public void setParent(RBNode<K, V> parent) {
        this.parent = parent;
    }

    public RBNode<K, V> getLeft() {
        return left;
    }

    public void setLeft(RBNode<K, V> left) {
        this.left = left;
    }

    public RBNode<K, V> getRight() {
        return right;
    }

    public void setRight(RBNode<K, V> right) {
        this.right = right;
    }
}
