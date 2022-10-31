package com.freeing.common.component.utils;

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

    /**
     * 条件处理
     *
     * @param conditions 条件
     * @return if-else 处理对象
     */
    public static IfTrueHandler of(boolean ...conditions) {
        return new IfTrueHandler(and(conditions));
    }

    /**
     * IfTrueHandler
     */
    static class IfTrueHandler {
        private final boolean ifTrue;

        public IfTrueHandler(boolean condition) {
            this.ifTrue = condition;
        }

        public OrElseHandler<Void> ifTrue(Runnable runnable) {
            if (ifTrue) {
                runnable.run();
                return new OrElseHandler<>(false);
            } else {
                return new OrElseHandler<>(true);
            }
        }

        public <T> OrElseHandlerWithReturn<T> ifTrue(Supplier<T> ifSupplier) {
            if (ifTrue) {
                T result = ifSupplier.get();
                return new OrElseHandlerWithReturn<>(false, result);
            } else {
                return new OrElseHandlerWithReturn<>(true, null);
            }
        }
    }

    /**
     * OrElse 处理类
     */
    static class OrElseHandler<T> {
        private final boolean orElse;



        public OrElseHandler(boolean orElse) {
            this.orElse = orElse;
        }

        public OrElseHandler(boolean orElse, T ifTrueResult) {
            this.orElse = orElse;
        }

        public void orElse(Runnable runnable) {
            if (orElse) {
                runnable.run();
            }
        }
    }

    /**
     *  OrElse 处理类
     */
    static class OrElseHandlerWithReturn<T> {
        private final boolean orElse;

        private final T ifTrueResult;

        public OrElseHandlerWithReturn(boolean orElse, T ifTrueResult) {
            this.orElse = orElse;
            this.ifTrueResult = ifTrueResult;
        }

        public T orElse(Supplier<T> orElseSupplier) {
            if (orElse) {
                return orElseSupplier.get();
            }
            return ifTrueResult;
        }
    }
}
