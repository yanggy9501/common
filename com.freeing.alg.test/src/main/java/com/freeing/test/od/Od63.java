package com.freeing.test.od;

import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * https://renjie.blog.csdn.net/article/details/128352026
 *
 * 思想：递归
 *
 * @author yanggy
 */
public class Od63 {
    /*
1 3
1 =A1+C1 3
A1:C1


1 3
=C1 =A1+C1 3
A1:C1
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] params = scanner.nextLine().split(" ");
        int rows = Integer.parseInt(params[0]);
        int clos = Integer.parseInt(params[1]);
        String[][] matrix = new String[rows][clos];
        for (int i = 0; i < rows; i++) {
            matrix[i] = scanner.nextLine().split(" ");
        }
        String region = scanner.nextLine();
        System.out.println(computeRegion(matrix, region));
    }

    public static int computeRegion(String[][] arr, String region) {
        String regex = "(\\w\\d+):(\\w\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(region);
        if (matcher.matches()) {
            String cell1 = matcher.group(1);
            String cell2 = matcher.group(2);
            int r1 = Integer.parseInt(cell1.substring(1)) - 1;
            int c1 = cell1.charAt(0) - 'A';

            int r2 = Integer.parseInt(cell2.substring(1)) - 1;
            int c2 = cell2.charAt(0) - 'A';
            int sum = 0;
            for (int i = r1; i <= r2; i++) {
                for (int j = c1; j <= c2; j++) {
                    sum += parse(arr, arr[i][j]);
                }
            }
            return sum;
        }
        return 0;
    }

    public static int parse(String[][] arr, String exp) {
        try {
            // 1
            return Integer.parseInt(exp);
        } catch (Exception ignored) {
            // =B12 解析解析
            String regex = "=([A-Z]\\d+)";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(exp);
            if (matcher.matches()) {
                String cell = matcher.group(1);
                // 表达式对应的单元格
                int r = Integer.parseInt(cell.substring(1)) - 1;
                int c = cell.charAt(0) - 'A';
                return parse(arr, arr[r][c]);
            } else {
                // B1+1，100-B1，C1+C2
                String regex1 = "=([A-Z]?\\d+)([+|-])([A-Z]?\\d+)";
                Pattern pattern1 = Pattern.compile(regex1);
                Matcher matcher1 = pattern1.matcher(exp);
                if (matcher1.matches()) {
                    String cell1 = matcher1.group(1);
                    String operator = matcher1.group(2);
                    String cell2 = matcher1.group(3);
                    return computeCell2(arr, cell1, cell2, operator);
                }
                return 0;
            }
        }
    }

    public static int computeCell2(String[][] arr, String cell1, String cell2, String operator) {
        // B1+1，100-B1
        if (isNumeric(cell1) || isNumeric(cell2)) {
            if (Objects.equals(operator, "+")) {
                return isNumeric(cell1) ? Integer.parseInt(cell1) + parse(arr, cell2) : parse(arr, cell1) + Integer.parseInt(cell2);
            } else {
                return isNumeric(cell1) ? Integer.parseInt(cell1) - parse(arr, cell2) : parse(arr, cell1) -  Integer.parseInt(cell2);
            }
        }

        // C1+C2
        int r1 = Integer.parseInt(cell1.substring(1)) - 1;
        int c1 = cell1.charAt(0) - 'A';

        int r2 = Integer.parseInt(cell2.substring(1)) - 1;
        int c2 = cell2.charAt(0) - 'A';
        if (Objects.equals(operator, "+")) {
            return parse(arr, arr[r1][c1]) + parse(arr, arr[r2][c2]);
        } else {
            return parse(arr, arr[r1][c1]) - parse(arr, arr[r2][c2]);
        }
    }

    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
