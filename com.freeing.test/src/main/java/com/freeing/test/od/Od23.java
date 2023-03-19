package com.freeing.test.od;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * https://blog.csdn.net/misayaaaaa/article/details/127947829
 *
 * @author yanggy
 */
public class Od23 {

    /*
      区间组：
[1,10],[15,20],[18,30],[33,40]
[5,4,3,2]
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        // 将一个普通的String类编译成正则表达式对象
        String regex = "(\\[\\d+,\\d+\\])";
        Pattern compile = Pattern.compile(regex);
        // 包含正则和普通字符串，以及匹配模式
        Matcher matcher = compile.matcher(s);

        ArrayList<Section> sectionList = new ArrayList<>();
        while (matcher.find()) {
            String[] split = matcher.group()
                .replace("[", "")
                .replace("]", "")
                .split(",");
            Section section = new Section(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
            sectionList.add(section);
        }

        PriorityQueue<Section> heap = new PriorityQueue<>();
        sectionList.sort(Comparator.comparingInt(s2 -> s2.left));

        ArrayList<Section> res = new ArrayList<>();
        for (Section section : sectionList) {
            if (heap.isEmpty()) {
                heap.add(section);
                continue;
            }
            // 先删除不可能在会有交集的区间
            Iterator<Section> iterator = heap.iterator();
            while (iterator.hasNext()) {
                Section sec = iterator.next();
                if (sec.right < section.left) {
                    res.add(sec);
                    iterator.remove();
                } else {
                    break;
                }
            }
            // 剩下都是相交的，合并成更大的区间
            int max = section.right;
            int min = section.left;
            for (Section peer : heap) {
                max = Math.max(peer.right, max);
                min = Math.min(peer.left, min);
            }
            heap.clear();
            heap.add(new Section(min, max));
        }
        res.add(heap.poll());
        // 计算区间的间隔
        ArrayList<Integer> subList = new ArrayList<>();
        for (int i = 1; i < res.size(); i++) {
            subList.add(res.get(i).left - res.get(i - 1).right);
        }
        subList.sort(Comparator.comparingInt(s1 -> s1));
        // 分苹果问题
        String[] ranges = scanner.nextLine().replace("[", "").replace("]", "").split(",");
        int[] linkArr = new int[ranges.length];
        int[] usedArr = new int[ranges.length];
        for (int i = 0; i < ranges.length; i++) {
            linkArr[i] = Integer.parseInt(ranges[i]);
        }
        Arrays.sort(linkArr);
        int count  = 0;
        for (Integer sub : subList) {
            for (int i = 0; i < linkArr.length; i++) {
                if (linkArr[i] >= sub && usedArr[i] == 0) {
                    usedArr[i] = 1;
                    count++;
                    break;
                }
            }
        }
        System.out.println(res.size() - count);
    }

    /**
     * 区间
     */
    static class Section {
        int left;

        int right;

        public Section(int left, int right) {
            this.left = left;
            this.right = right;
        }

        public int getLeft() {
            return left;
        }

        public void setLeft(int left) {
            this.left = left;
        }

        public int getRight() {
            return right;
        }

        public void setRight(int right) {
            this.right = right;
        }

        @Override
        public String toString() {
            return "Section{" +
                "left=" + left +
                ", right=" + right +
                '}';
        }
    }
}
