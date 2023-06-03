package com.freeing.test.od.od1;

import java.util.*;

/**
 * https://renjie.blog.csdn.net/article/details/130957179
 *
 * @author yanggy
 */
public class Od12 {
    /*
2 22 1 11 4 44 5 55 3 33
1 7 2 3

5 64 11 64 9 97
9 11 4 9
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] msgs = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int[] css = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        Msg[] msgs1 = new Msg[msgs.length / 2];
        int index = 0;
        for (int i = 0; i < msgs.length; i += 2) {
            Msg msg = new Msg();
            msg.sendTime = msgs[i];
            msg.msg = msgs[i + 1];
            msgs1[index++] = msg;
        }
        Comsumer[] csss = new Comsumer[css.length / 2];
        index = 0;
        for (int i = 0; i < css.length; i += 2) {
            Comsumer comsumer = new Comsumer();
            comsumer.proority = i;
            comsumer.subTime = css[i];
            comsumer.unsubTime = css[i + 1];
            csss[index++] = comsumer;
        }
        compute(msgs1, csss);
    }

    public static void compute(Msg[] msgs, Comsumer[] cs) {
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < cs.length; i++) {
            map.put(cs[i].proority, new ArrayList<>());
        }

        for (Msg msg : msgs) {
            int sendTime = msg.sendTime;
            for (int i = cs.length - 1; i >= 0; i--) {
                Comsumer c = cs[i];
                if (sendTime >= c.subTime && sendTime < c.unsubTime) {
                    List<Integer> list = map.get(c.proority);
                    list.add(msg.msg);
                    break;
                }
            }
        }
        for (int i = 0; i < cs.length; i++) {
            System.out.println(map.get(cs[i].proority));
        }
    }

    static class Msg {
        int sendTime;

        int msg;
    }

    static class Comsumer {
        int subTime;
        int unsubTime;
        int proority;
    }
}
