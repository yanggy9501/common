package com.freeing.common.component.util.range;

/**
 * 区间定义
 *
 * @author yanggy
 */
public class Range <K, V> {
    /**
     * 区间的唯一ID
     */
    private String id;

    private K start;

    private K end;

    private V data;

    public Range() {
    }

    public Range(String id) {
        this.id = id;
    }

    public Range(String id, K start, K end, V data) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public K getStart() {
        return start;
    }

    public void setStart(K start) {
        this.start = start;
    }

    public K getEnd() {
        return end;
    }

    public void setEnd(K end) {
        this.end = end;
    }

    public V getData() {
        return data;
    }

    public void setData(V data) {
        this.data = data;
    }
}
