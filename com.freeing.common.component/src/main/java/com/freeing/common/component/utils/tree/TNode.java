package com.freeing.common.component.utils.tree;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 *
 * @author yanggy
 */
public class TNode<K, V> {
    private K nodeId;

    private K parentId;

    private V data;

    private List<TNode<K, V>> children;

    private Map<String, Objects> extra;

    public K getNodeId() {
        return nodeId;
    }

    public void setNodeId(K nodeId) {
        this.nodeId = nodeId;
    }

    public K getParentId() {
        return parentId;
    }

    public void setParentId(K parentId) {
        this.parentId = parentId;
    }

    public V getData() {
        return data;
    }

    public void setData(V data) {
        this.data = data;
    }

    public List<TNode<K, V>> getChildren() {
        return children;
    }

    public void setChildren(List<TNode<K, V>> children) {
        this.children = children;
    }

    public Map<String, Objects> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, Objects> extra) {
        this.extra = extra;
    }
}
