package com.freeing.common.support.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DictTrie {
    /**
     * 字典树的 root(空字符)，仅做操作入口，使得所有节点操作统一
     */
    private final TrieNode root;

    public DictTrie() {
        // 构造一个空字符的节点作为根节点
        root = new TrieNode((char) 0);
    }

    /**
     * 插入一个分词到字典树中：
     * chars = word.toCharArray()
     * 从 curNode = root 开始出发，判断 curNode 是否存在 chars[i] (类似排序树的遍历)，无着创建
     *
     * @param word
     */
    public void insert(final String word) {
        // “排序树访问节点”
        TrieNode curParentDictNode = root;
        for (int i = 0, len = word.length(); i < len; i++) {
            char ch = word.charAt(i);
            if (!curParentDictNode.getChildren().containsKey(ch)) {
                TrieNode chNode = new TrieNode(ch);
                curParentDictNode.getChildren().put(ch, chNode);
            }
            // 【下一个 parent】如果当前节点的值为c的孩子节点存在，则cur指向该孩子节点
            curParentDictNode = curParentDictNode.getChildren().get(ch);
        }
        // 当所有节点都遍历完成，将当前节点，即最后一个节点的标识符置为true，表示从根节点遍历到该节点的字母组成了一个单词
        curParentDictNode.setEnd(true);
    }

    /**
     * 查找 prefixWord 开头的所有单词
     *
     * @param prefixWord
     * @return
     */
    public List<String> search(String prefixWord) {
        ArrayList<String> retList = new ArrayList<>();

        // 前半部安装 prefixWord 查找
        TrieNode curDictNode = root;
        for (int i = 0, len = prefixWord.length(); i < len; i++) {
            char ch = prefixWord.charAt(i);
            if (!curDictNode.getChildren().containsKey(ch)) {
                return retList;
            }
            curDictNode = curDictNode.getChildren().get(ch);
        }

        // 前序遍历获取所有单词
        if (curDictNode != root) {
            StringBuilder pathChar = new StringBuilder(prefixWord);
            for (TrieNode itemNode : curDictNode.getChildren().values()) {
                preOrderVisit(itemNode, pathChar, retList);
            }
        }
        return retList;
    }

    private void preOrderVisit(TrieNode node, StringBuilder pathChar, List<String> retList) {
        // 访问当前节点
        pathChar.append(node.getCh());

        if (node.isEnd()) {
            retList.add(pathChar.toString());
        }

        // 访问孩子节点
        for (TrieNode itemNode : node.getChildren().values()) {
            preOrderVisit(itemNode, pathChar, retList);
        }

        pathChar.deleteCharAt(pathChar.length() - 1);
    }

    /**
     * 判断字典树中是否存在 word
     *
     * @param word
     * @return
     */
    public boolean hasWord(final String word) {
        TrieNode curDictNode = root;
        for (int i = 0, len = word.length(); i < len; i++) {
            char ch = word.charAt(i);
            if (!curDictNode.getChildren().containsKey(ch)) {
                return false;
            }
            curDictNode = curDictNode.getChildren().get(ch);
        }
        return curDictNode != root && curDictNode.isEnd();
    }

    /**
     * 判断字典树中是否有 prefixWord 前缀
     *
     * @param prefixWord
     * @return
     */
    public boolean startsWith(String prefixWord) {
        TrieNode curDictNode = root;
        for (int i = 0, len = prefixWord.length(); i < len; i++) {
            char ch = prefixWord.charAt(i);
            if (!curDictNode.getChildren().containsKey(ch)) {
                return false;
            }
            curDictNode = curDictNode.getChildren().get(ch);
        }

        return curDictNode != root;
    }


    /* ----------------------------------------------------- class ------------------------------------------------ */

    static class TrieNode {
        private Character ch;

        /**
         * 该节点的属性，用来表示是否是一个完整的单词
         */
        private boolean isEnd;

        /**
         * 定义该节点的孩子节点
         */
        private final Map<Character, TrieNode> children;

        private TrieNode() {
            this.children = new ConcurrentHashMap<>();
        }

        public TrieNode(Character ch) {
            this();
            this.ch = ch;
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

        public Map<Character, TrieNode> getChildren() {
            return children;
        }
    }
}
