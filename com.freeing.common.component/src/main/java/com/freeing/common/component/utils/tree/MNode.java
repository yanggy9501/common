package com.freeing.common.component.utils.tree;

import java.util.HashMap;
import java.util.List;

/**
 *
 *
 * @author yanggy
 */
public class MNode<K, V> extends HashMap<K, V> {
    private K nodeId;

    private K parentId;

    private List<K> parentPath;

}
