package com.freeing.common.support.search;

/**
 * 字典树节点（小写）
 */
public class TrieNode {
    private char ch;

    /**
     * 该节点的属性，用来表示是否是一个完整的单词
     */
    private boolean isEnd;

    /**
     * 定义该节点的孩子节点，用一个长度为 26的TrieNode[]数组来存储指向下一个孩子节点的指针
     */
    private final TrieNode[] children;

    public TrieNode() {
        // 默认孩子节点有26个(英文字母有26个)，用一个长度为26的TrieNode[]数组来存储孩子节点的指针
        children = new TrieNode[26];
    }

    public char getCh() {
        return ch;
    }

    public void setCh(char ch) {
        this.ch = ch;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    public TrieNode[] getChildren() {
        return children;
    }
}
