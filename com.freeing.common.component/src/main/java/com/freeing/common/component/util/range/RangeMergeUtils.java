package com.freeing.common.component.util.range;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * @author yanggy
 */
public class RangeMergeUtils {

    public static <K, V> MergeRange<K, V> mergeRange(List<Range<K, V>> rangeList , Comparator<Range<K, V>> comparator) {
        Objects.requireNonNull(comparator);
        rangeList.sort(comparator);
        ArrayList<MergeRange<K, V>> mergeRangeList = new ArrayList<>();
        for (int i = 0; i < rangeList.size(); i++) {
            Range<K, V> aRange = rangeList.get(i);
            if (mergeRangeList.isEmpty()) {
                MergeRange<K, V> mergeRange = new MergeRange<>();
                mergeRange.getOriginRanges().add(aRange);
                mergeRange.getOverlapRanges().add(aRange);
                mergeRange.setStart(aRange.getStart());
                mergeRange.setEnd(aRange.getEnd());
                mergeRangeList.add(mergeRange);
                continue;
            }
        }
        return null;
    }
}
