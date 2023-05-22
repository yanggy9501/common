package com.freeing.common.component.util.range;

import java.util.ArrayList;
import java.util.List;

/**
 * 区间定义
 *
 * @author yanggy
 */
public class MergeRange<K, V> {

    private K start;

    private K end;

    /**
     * start-end的分段
     */
    private List<Range<K, V>> overlapRanges;

    private List<Range<K, V>> originRanges;

    public MergeRange() {
        overlapRanges = new ArrayList<>();
        originRanges = new ArrayList<>();
    }

    public K getEnd() {
        return end;
    }

    public K getStart() {
        return start;
    }

    public void setStart(K start) {
        this.start = start;
    }

    public void setEnd(K end) {
        this.end = end;
    }

    public List<Range<K, V>> getOverlapRanges() {
        return overlapRanges;
    }

    public void setOverlapRanges(List<Range<K, V>> overlapRanges) {
        this.overlapRanges = overlapRanges;
    }

    public List<Range<K, V>> getOriginRanges() {
        return originRanges;
    }

    public void setOriginRanges(List<Range<K, V>> originRanges) {
        this.originRanges = originRanges;
    }
}
