package com.freeing.test.od;

import java.util.HashMap;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // 处理输入
        Scanner in = new Scanner(System.in);
        String content = in.nextLine();
        String word = in.nextLine();
        System.out.println(contin(content, word));
    }

    public static int contin(String content, String word){
        if(content.length() < word.length())
            return 0;
        if(word.length() == 0)
            return 0;
        HashMap<Character, Integer> content_map = new HashMap<Character, Integer>();
        HashMap<Character, Integer> word_map = new HashMap<Character, Integer>();
        //先统计出word中的字符组成
        for (int i=0;i<word.length();i++)
            word_map.put(word.toCharArray()[i], word_map.getOrDefault(word.toCharArray()[i], 0) + 1);

        char[] content_arr = content.toCharArray();
        int word_char_kind = word_map.size();
        int right = 0;
        int content_child_char_kind = 0;
        int result = 0;
        while(right < content.length()){
            if(right >= word.length()){
                int left = right - word.length();
                if (word_map.containsKey(content_arr[left]) && word_map.get(content_arr[left]) == content_map.get(content_arr[left]))
                    content_child_char_kind -=1 ;
                content_map.put(content_arr[left], content_map.getOrDefault(content_arr[left], 0) - 1);

            }

            content_map.put(content_arr[right], content_map.getOrDefault(content_arr[right], 0) + 1);
            if (word_map.containsKey(content_arr[right]) && word_map.get(content_arr[right]) == content_map.get(content_arr[right]))
                content_child_char_kind+=1;
            right+=1;
            if (content_child_char_kind == word_char_kind) {
                result += 1;
            }
        }
        return result;
    }

}