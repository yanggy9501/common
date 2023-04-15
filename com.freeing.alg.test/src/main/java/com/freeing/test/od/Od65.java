package com.freeing.test.od;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * https://renjie.blog.csdn.net/article/details/128364098
 *
 * @author yanggy
 */
public class Od65 {
/*
考察正则表达式
2.5.1-C
1.4.2-D

2.5.1
1.4.2


00002.0005.1
1.0004.2

 */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String v1 = scanner.nextLine();
        String v2 = scanner.nextLine();
        System.out.println(compareVersion(v1, v2));
    }

    public static String compareVersion(String v1, String v2) {
        // 版本号定义: <主版本>.<次版本>.<增量版本>-<里程碑版本>, 如：1.4.2-D
        String regex = "(\\d+).(\\d+).(\\d+)-(\\D)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher1 = pattern.matcher(v1);
        Matcher matcher2 = pattern.matcher(v2);
        while (matcher1.find() && matcher2.find()) {
            String vs1 = matcher1.group();
            String vs2 = matcher2.group();
            if (doCompare(vs1, vs2) > 0) {
                return v1;
            } else if (doCompare(vs1, vs2) < 0) {
                return v2;
            } else {
                continue;
            }
        }
        return v1;
    }

    public static int doCompare(String v1, String v2) {
        try {
            int n1 = Integer.parseInt(v1);
            int n2 = Integer.parseInt(v2);
            return n1 - n2;
        } catch (Exception e) {
            return v1.compareTo(v2);
        }
    }
}
