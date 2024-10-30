package com.freeing.common.support.search;

public class DictTrie {
    private final TrieNode root;

    public DictTrie() {
        // 构造一个空的节点作为根节点
        root = new TrieNode();
    }

    public void insert(final String lowerCaseWord) {
        String word = lowerCaseWord;
        TrieNode curDictNode = root;
        for (int i = 0, len = word.length(); i < len; i++) {
            // c为要保存字符的ASCII码值
            char ch = word.charAt(i);
            int chAt = word.charAt(i) - 'a';
            // 如果当前节点的值为c的孩子节点不存在，则新建该孩子节点并指向它
            if (curDictNode.getChildren()[chAt] == null) {
                curDictNode.getChildren()[chAt] = new TrieNode();
                curDictNode.getChildren()[chAt].setCh(ch);
            }
            // 如果当前节点的值为c的孩子节点存在，则cur指向该孩子节点
            curDictNode = curDictNode.getChildren()[chAt];
        }
        // 当所有节点都遍历完成，将当前节点，即最后一个节点的标识符置为true，表示从根节点遍历到该节点的字母组成了一个单词
        curDictNode.setEnd(true);
    }

    public boolean search(final String lowerCaseWord) {
        String word = lowerCaseWord;
        TrieNode curDictNode = root;
        for (int i = 0, len = word.length(); i < len; i++) {
            int chAt = word.charAt(i) - 'a';
            if (curDictNode.getChildren()[chAt] == null) {
                return false;
            }
            curDictNode = curDictNode.getChildren()[chAt];
        }
        return curDictNode.isEnd();
    }

    public boolean startsWith(String lowerCaseWordPrefix) {
        String word = lowerCaseWordPrefix;
        TrieNode curDictNode = root;
        for (int i = 0, len = word.length(); i < len; i++) {
            int chAt = word.charAt(i) - 'a';
            if (curDictNode.getChildren()[chAt] == null) {
                return false;
            }
            curDictNode = curDictNode.getChildren()[chAt];
        }
        return true;
    }
}
