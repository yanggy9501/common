package com.freeing.common.test.lock;

import java.util.concurrent.Exchanger;

public class ExchangerTest {
    public static void main(String[] args) throws InterruptedException {
        Exchanger exchanger = new Exchanger();
        exchanger.exchange("hello");
    }
}
