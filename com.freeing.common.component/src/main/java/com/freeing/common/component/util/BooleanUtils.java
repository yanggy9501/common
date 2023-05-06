package com.freeing.common.component.util;

import java.util.function.Supplier;

/**
 * bool 工具类
 *
 * @author yanggy
 */
public class BooleanUtils extends org.apache.commons.lang3.BooleanUtils {

    public static <T> T handleConditionWithReturn(Supplier<T> ifSupplier, boolean ...conditions) {
        if (and(conditions) && ifSupplier != null) {
            return ifSupplier.get();
        }
        return null;
    }

    public static void handleCondition(Runnable ifRunnable, boolean ...conditions) {
        if (and(conditions) && ifRunnable != null) {
            ifRunnable.run();
        }
    }
}
