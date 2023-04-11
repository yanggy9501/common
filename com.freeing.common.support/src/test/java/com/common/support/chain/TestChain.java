package com.common.support.chain;

import com.freeing.common.support.chain.v2.Handler;
import com.freeing.common.support.chain.v2.HandlerChain;

/**
 * @author yanggy
 */
public class TestChain {
    public static void main(String[] args) {
        HandlerChain<String> chain = new HandlerChain<>();
        chain.addHandler(new Handler<String>() {
            @Override
            public void doHandle(String obj, HandlerChain<String> chain) {
                System.out.println("1111" + obj);
                super.doHandle(obj, chain);
            }
        });
        chain.addHandler(new Handler<String>() {
            @Override
            public void doHandle(String obj, HandlerChain<String> chain) {
                System.out.println("2222" + obj);
                super.doHandle(obj, chain);
            }
        });

        chain.doHandle("hello world");
    }
}
