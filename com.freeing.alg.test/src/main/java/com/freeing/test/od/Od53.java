package com.freeing.test.od;

import java.util.*;

/**
 * https://renjie.blog.csdn.net/article/details/128306773
 *
 * @author yanggy
 */
public class Od53 {
    /*
1 3 5 1
2 1 5 10
3 2 7 12
4 3 2 20
5 4 9 21
6 4 2 22
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            if (s.equals("")) {
                break;
            }
            String[] split = s.split(" ");
            tasks.add(new Task(split));
        }
        sechdule(tasks);
    }

    public static void sechdule(List<Task> taskList) {
        taskList.sort(Comparator.comparingInt(v -> v.reachTime));
        // 代表cpu正在处理的任务队列，top 就是cpu在执行的
        PriorityQueue<Task> queue = new PriorityQueue<>((v1, v2) -> v2.priority - v1.priority);
        queue.add(taskList.get(0));
        taskList.remove(0);
        ArrayList<Integer> result = new ArrayList<>();
        while (!queue.isEmpty()) {
             // 出队认为可以运行完，如果中途被抢占则重新入队
            Task task = queue.poll();
            int end = task.takeTime + task.reachTime;
            Iterator<Task> iterator = taskList.iterator();
            // 把中途可以运行的任务入队
            while (iterator.hasNext()) {
                Task next = iterator.next();
                if (next.reachTime <= end) {
                    queue.add(next);
                    iterator.remove();
                } else {
                    break;
                }
            }
            // 取出顶，看是否被抢占
            if (!queue.isEmpty()) {
                Task top = queue.element();
                int reachTime = top.reachTime;
                int priority = top.priority;
                // 被抢占了
                if (reachTime < end && priority > task.priority) {
                    task.takeTime = task.takeTime - (reachTime - task.reachTime);
                    task.reachTime = reachTime;
                    // 重新入队
                    queue.add(task);
                } else {
                    // 不被抢占也可以运行完，但是下个任务的执行结束时间不是end = task.takeTime + task.reachTime;
                    // 需要更新抵达时间
                    top.reachTime = end;
                    result.add(task.id);
                }
            } else {
                // 当前任务可以运行完
                result.add(task.id);
                // 取一个任务到任务队列
                if (!taskList.isEmpty()) {
                    queue.add(taskList.remove(0));
                }
            }
        }
        System.out.println(result);
    }

    public static class Task {
        int id;

        int priority;

        int takeTime;

        // 抵达时间或者开始执行时间
        int reachTime;

        public Task(String[] arr) {
            id = Integer.parseInt(arr[0]);
            priority = Integer.parseInt(arr[1]);
            takeTime = Integer.parseInt(arr[2]);
            reachTime = Integer.parseInt(arr[3]);
        }
    }
}
