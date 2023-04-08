package com.common.support.filter;

import com.freeing.common.support.chain.v1.AbstractHandler;

/**
 * @author yanggy
 */
public class TestChainV1 {
    public static void main(String[] args) {
        HeadHandler<String> head = new HeadHandler<>();

        AbstractHandler<String> h2 = new AbstractHandler<String>() {
            @Override
            protected void handler(String obj) {
                System.out.println("2: " + obj);
            }
        };

        AbstractHandler<String> h3 = new AbstractHandler<String>() {
            @Override
            protected void handler(String obj) {
                System.out.println("3: " + obj);
            }
        };

        AbstractHandler<String> h4 = new AbstractHandler<String>() {
            @Override
            protected void handler(String obj) {
                System.out.println("4: " + obj);
            }
        };

        head.setNext(h2);
        h2.setNext(h3);
        h3.setNext(h4);
        head.chain("hello");
    }
}
