package com.freeing.common.chain.impl;

import com.freeing.common.chain.Chain;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BaseCatalog {

    /**
     * <p>The map of named {@link Chain}s, keyed by name.
     */
    protected Map<String, Chain> commands = new ConcurrentHashMap<>();

    /**
     * Create an empty catalog.
     */
    public BaseCatalog() {
    }

    public void addCommand(String name, Chain chain) {
        commands.put(name, chain);
    }

    public Chain getCommand(String name) {
        return commands.get(name);
    }

    public Iterator<String> getNames() {
        return (commands.keySet().iterator());

    }

    public String toString() {
        Iterator<String> names = getNames();
        StringBuffer str =
                new StringBuffer("[" + this.getClass().getName() + ": ");
        while (names.hasNext()) {
            str.append(names.next());
            if (names.hasNext()) {
                str.append(", ");
            }
        }
        str.append("]");
        return str.toString();
    }
}
