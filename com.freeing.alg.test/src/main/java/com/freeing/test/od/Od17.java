package com.freeing.test.od;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * https://renjie.blog.csdn.net/article/details/128203614
 * 优先级队列 或者 说是排序
 *
 * @author yanggy
 */
public class Od17 {
    /*
7
IN 1 1
IN 1 2
IN 1 3
IN 2 1
OUT 1
OUT 2
OUT 2

     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String numberStr = scanner.nextLine();
        int number = Integer.parseInt(numberStr);
        HashMap<String, PriorityQueue<PrintTask>> map = new HashMap<>();
        for (int i = 1; i <= number; i++) {
            String[] arr = scanner.nextLine().split(" ");
            String printer = arr[1];
            if (arr[0].startsWith("IN")) {
                int priority =  Integer.parseInt(arr[2]);
                PriorityQueue<PrintTask> printTasks =
                    map.computeIfAbsent(printer, key -> new PriorityQueue<PrintTask>((v1, v2) -> v2.priority - v1.priority));
                printTasks.add(new PrintTask(printer, priority, i));
            } else {
                PriorityQueue<PrintTask> tasks = map.get(printer);
                if (tasks == null || tasks.isEmpty()) {
                    System.out.println("NULL");
                    continue;
                }
                PrintTask task = tasks.remove();
                System.out.println(task.no);
            }
        }
    }

    static class PrintTask {
        String printer;

        int priority;

        int no;

        public PrintTask(String printer, int priority, int no) {
            this.printer = printer;
            this.priority = priority;
            this.no = no;
        }
    }
}
