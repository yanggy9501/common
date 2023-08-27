package com.freeing.test.od.od1;

import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * https://renjie.blog.csdn.net/article/details/131023709
 *
 * @author yanggy
 */
public class Od38 {
    /*
(10,1),(20,1),(30,2),(40,3)

     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();

        String regex = "\\(\\d+,\\d+\\)";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(s);
        PriorityQueue<Pair> queue = new PriorityQueue<>(new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                return o2.priority - o1.priority;
            }
        });
        while (matcher.find()) {
            String[] split = matcher.group().replace("(", "").replace(")", "").split(",");
            queue.add(new Pair(split[0], Integer.parseInt(split[1])));
        }

        Pair pre = null;
        while (!queue.isEmpty()) {
            Pair poll = queue.poll();
            if (poll.equals(pre)) {
                continue;
            }
            System.out.println(poll.value);
            pre = poll;
        }
    }

    static class Pair {
        String value;

        int priority;

        public Pair(String value, int priority) {
            this.value = value;
            this.priority = priority;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Pair)) return false;

            Pair pair = (Pair) o;

            if (priority != pair.priority) return false;
            return Objects.equals(value, pair.value);
        }

        @Override
        public int hashCode() {
            int result = value != null ? value.hashCode() : 0;
            result = 31 * result + priority;
            return result;
        }
    }
}
