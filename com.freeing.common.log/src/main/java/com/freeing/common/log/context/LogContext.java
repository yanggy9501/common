package com.freeing.common.log.context;


public class LogContext {
    private static final ThreadLocal<String> REQUEST_THREAD_LOCAL = new ThreadLocal<>();

    private static final ThreadLocal<String> RESPONSE_THREAD_LOCAL = new ThreadLocal<>();

    public static void setLogRequestArgs(String requestArgs) {
        if (requestArgs != null && !requestArgs.isEmpty()) {
            REQUEST_THREAD_LOCAL.set(requestArgs);
        }
    }

    public static void setLogResponseArgs(String responseArgs) {
        if (responseArgs != null && !responseArgs.isEmpty()) {
            RESPONSE_THREAD_LOCAL.set(responseArgs);
        }
    }

    public static ThreadLocal<String> getLogRequestThreadLocal() {
        return REQUEST_THREAD_LOCAL;
    }

    public static ThreadLocal<String> getLogResponseThreadLocal() {
        return RESPONSE_THREAD_LOCAL;
    }
}
