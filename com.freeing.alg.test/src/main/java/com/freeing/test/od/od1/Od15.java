package com.freeing.test.od.od1;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author yanggy
 */
public class Od15 {
    /*
4
rolling3 stone4 like1 a2

8
gifts6 and7 Exchanging1 all2 precious5 things8 kinds3 of4
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = Integer.parseInt(scanner.nextLine());
        String[] array = new String[m];
        String[] split = scanner.nextLine().split(" ");
        func(split);

    }

    public static void func(String[] array) {
        ArrayList<Pair<String, Integer>> list = new ArrayList<>();
        String regex = "^(\\D+)(\\d+)$";
        for (String s : array) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(s);
            if (matcher.matches()) {
                Pair<String, Integer> pair = new Pair<>(matcher.group(1), Integer.parseInt(matcher.group(2)));
                list.add(pair);
            } else {
                throw new IllegalArgumentException();
            }
        }
        list.sort(Comparator.comparing(Pair::getValue));
        String r = list.stream().map(Pair::getKey).collect(Collectors.joining(" "));
        System.out.println(r);
    }
}
