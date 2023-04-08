package com.freeing.common.support.chain.v2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yanggy
 */
public class HandlerChain <T> {

    private final List<Handler<T>> chain;

    private int position;

    public HandlerChain() {
        chain = new ArrayList<>();
        position = 0;
    }

    public void addHandler(Handler<T> handler) {
        chain.add(handler);
    }

    public void doHandle(T obj) {
        if (position >= 0 && position < chain.size()) {
            chain.get(position++).doHandle(obj, this);
        }
    }
}
