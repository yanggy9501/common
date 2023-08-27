package com.freeing.test.od.od1;

import java.util.Scanner;

/**
 * @author yanggy
 */
public class Od33 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        int t = scanner.nextInt();

        // x, y方向的速度
        int x_step = 1;
        int y_step = 1;

        int width = 800;
        int height = 800;

        // 方向的移动可以分解为两个方向，每个方向触底都会在该方向反弹，速度方向
        for (int i = 0; i < t; i++) {
            if (x == 0) {
                x_step = 1;
            }
            if (x + 50 == width) {
                x_step = -1;
            }

            if (y == 0) {
                y_step = 1;
            }
            if (y + 25 == height) {
                y_step = -1;
            }

            x += x_step;
            y += y_step;
        }
        System.out.println(x + " " + y);
    }
}
