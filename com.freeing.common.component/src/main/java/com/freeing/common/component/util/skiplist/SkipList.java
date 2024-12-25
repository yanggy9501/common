package com.freeing.common.component.util.skiplist;

import java.util.Random;

public class SkipList<K extends Comparable<K>, V> {
    /**
     * 最大的层
     */
    static final int MAX_LEVEL = 32;

    /**
     * 头节点，入口
     */
    private SkipListNode<K, V> headNode;

    /**
     * 当前跳表索引层数
     */
    private int highLevel;

    /**
     * 用于投掷硬币
     */
    Random random;

    public SkipList(K key, V value) {
        random = new Random();
        headNode = new SkipListNode<>(key, value);
        highLevel = 0;
    }


    /**
     * 查找 SkipListNode.key = key 的节点
     *
     * @param key
     * @return SkipListNode
     */
    public SkipListNode<K, V> search(K key) {
        SkipListNode<K, V> pn = headNode;
        while (pn != null) {
            if (pn.key.compareTo(key) == 0) {
                return pn;
            }
            else if (pn.next == null) { //右侧没有了，只能下降
                pn = pn.down;
            }
            else if (pn.next.key.compareTo(key) > 0) { // 需要下降去寻找
                pn = pn.down;
            }
            else {
                pn = pn.prev;
            }
        }
        return null;
    }

    /**
     * 删除 SkipListNode.key = key 的节点
     *
     * @param key
     */
    public void delete(K key) {
        SkipListNode<K, V> pn = headNode;
        while (pn != null) {
            if (pn.next == null) { // //右侧没有了，说明这一层找到，没有只能下降
                pn = pn.down;
            }
            else if (pn.next.key.compareTo(key) == 0) { //删除右侧节点
                pn.next = pn.next.next;
                pn = pn.down; // 向下继续查找删除
            }
            else if (pn.next.key.compareTo(key) > 0) {
                pn = pn.down;
            } else {
                pn = pn.prev; // 节点还在右侧
            }
        }
    }
}
