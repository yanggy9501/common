package com.freeing.common.support.trie;

import org.apache.commons.collections4.trie.PatriciaTrie;

public class TrieTest {
    public static void main(String[] args) {
        PatriciaTrie<String> trie = new PatriciaTrie<>();
        trie.put("abigail", "student");
        trie.put("abi", "student");
        System.out.println(trie.containsKey("abi"));
    }
}
