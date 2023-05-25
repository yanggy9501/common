package com.freeing.test.od.od1;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yanggy
 */
public class Od05 {
    /*
01202021,75;01201033,95;01202008,80;01203006,90;01203088,100
01202008,70;01203088,85;01202111,80;01202021,75;01201100,88
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] split = scanner.nextLine().split(";");
        String[] split2 = scanner.nextLine().split(";");
        compute(split2, split);
    }

    public static void compute(String[] array1, String[] array2) {
        HashMap<String, Student> map = new HashMap<>();
        for (String s : array1) {
            String[] split = s.split(",");
            Student student = new Student();
            student.StudentNo = split[0];
            student.classNo = split[0].substring(0, 6);
            student.score = Integer.parseInt(split[1]);
            map.put(split[0], student);
        }
        ArrayList<Student> list = new ArrayList<>();
        for (String s : array2) {
            String[] split = s.split(",");
            if (map.containsKey(split[0])){
                Student student = map.get(split[0]);
                student.score += Integer.parseInt(split[1]);
                list.add(student);
            }
        }
        if (list.isEmpty()) {
            System.out.println("NULL");
            return;
        }
        Map<String, List<Student>> groupBy = list.stream().collect(Collectors.groupingBy(item -> item.classNo));
        List<Map.Entry<String, List<Student>>> list2 = groupBy.entrySet()
            .stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toList());
        for (Map.Entry<String, List<Student>> entry : list2) {
            System.out.println(entry.getKey());
            entry.getValue().sort((s1, s2) -> {
                if (s1.score == s2.score) {
                    return s1.StudentNo.compareTo(s2.StudentNo);
                }
                return s2.score - s1.score;
            });
            String collect = entry.getValue().stream().map(item -> item.StudentNo).collect(Collectors.joining(";"));
            System.out.println(collect);
        }
    }

    static class Student {
        String StudentNo;

        String classNo;

        int score;

        @Override
        public String toString() {
            return "Student{" +
                "StudentNo='" + StudentNo + '\'' +
                ", classNo='" + classNo + '\'' +
                ", score=" + score +
                '}';
        }
    }
}
