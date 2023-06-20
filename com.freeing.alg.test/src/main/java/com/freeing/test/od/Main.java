package com.freeing.test.od;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 1; i++) {
            executorService.execute(() -> {
                System.out.println("==============");
            });
        }
    }
}