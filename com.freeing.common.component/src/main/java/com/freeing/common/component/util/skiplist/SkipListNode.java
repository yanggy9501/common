package com.freeing.common.component.util.skiplist;

/**
 * 跳表 node
 *
 * @param <K> node 的 key
 * @param <V> node 的 value
 */
public class SkipListNode<K extends Comparable<K>, V> {
    private K key;

    private V value;

    /**
     * level层的向前和向后 node 指针
     */
    SkipListNode<K, V> next, prev;

    /**
     * 下一层的 node 指针
     */
    SkipListNode<K, V> down;

    public SkipListNode(K key, V value) {
        this.key = key;
        this.value = value;
    }
}
