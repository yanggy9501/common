package com.freeing.common.component.util.skiplist;

import java.util.Random;

// https://www.cnblogs.com/bigsai/p/14193225.html
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

    //其他方法
}
