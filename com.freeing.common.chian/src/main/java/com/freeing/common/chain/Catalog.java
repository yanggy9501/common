package com.freeing.common.chain;

import java.util.Iterator;

public interface Catalog {

    void addCommand(String name, Chain chain);

    Chain getCommand(String name);

    Iterator<String> getNames();
}
