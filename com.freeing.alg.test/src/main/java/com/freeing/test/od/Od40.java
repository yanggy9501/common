package com.freeing.test.od;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * https://renjie.blog.csdn.net/article/details/128401959
 * 正则
 *
 * @author yanggy
 */
public class Od40 {
    /*
1
100CNY

1
3000fen

2
20CNY53fen
53HKD87cents
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int i = Integer.parseInt(scanner.nextLine());
        int r = 0;
        for (int i1 = 0; i1 < i; i1++) {
            String s = scanner.nextLine();
            r += translate(s);
        }
        System.out.println(r);
    }

    static final String regex = "(\\d+)([a-z|A-Z]+)";
    public static int translate(String str) {
        int r = 0;
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(str);
        while (matcher.find()) {
            int number = Integer.parseInt(matcher.group(1));
            String unit = matcher.group(2);
            r += doCompute(number, unit);
        }
        return r;
    }

    public static int doCompute(int num, String unit) {
        int r = 0;
        switch (unit) {
            case "CNY":
                r += num * 100;
                break;
            case "JPY":
                r += num * 100 * 100 / 1825;
                break;
            case "HKD":
                r += num * 100 * 100 / 123;
                break;
            case "cents":
                r += num * 100 / 123;
                break;
            case "EUR":
                r += num * 100 * 100 / 14;
                break;
            case "eurocents":
                r += num * 100 / 14;
                break;
            case "GBP":
                r += num  * 100 * 100 / 12;
                break;
            case "pence":
                r += num  * 100 / 12;
                break;
            default:
                r += num;
                break;
        }
        return r;
    }

    public static String normal(String str) {
        StringBuilder number = new StringBuilder();
        StringBuilder unit = new StringBuilder();
        int count = 0;
        for (char ch : str.toCharArray()) {
            count++;
            if (ch >= '0' && ch <= '9') {
                if (unit.length() > 0) {
                    int num = Integer.parseInt(number.toString());
                    System.out.println(num);
                    System.out.println(unit);
                    unit.delete(0, unit.length());
                    number.delete(0, number.length());
                }
                number.append(ch);
            } else {
                unit.append(ch);
                if (count == str.length()) {
                    int num = Integer.parseInt(number.toString());
                    System.out.println(num);
                    System.out.println(unit);
                }
            }
        }
        return "";
    }
}
