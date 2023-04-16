package com.freeing.common.component.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Log 上下文
 *
 * @author yanggy
 */
public class LogContext {

    private static final String LINE_SEPARATOR = System.getProperty("line.separator","\n");
    private static final ThreadLocal<LogContext> LOCAL = new ThreadLocal<>();

    /**
     * 下一个异常存储（跨线程时使用）
     */
    private LogContext stored;

    /**
     * 错误放生在什么资源上
     */
    private String resource;

    private String code;
    private String message;
    private String object;

    /**
     * 额外信息
     */
    private Map<String, Object> extra;

    private Throwable cause;

    private LogContext() {
    }

    public static LogContext instance() {
        LogContext context = LOCAL.get();
        if (context == null) {
            context = new LogContext();
            LOCAL.set(context);
        }
        return context;
    }

    public LogContext store() {
        LogContext newContext = new LogContext();
        newContext.stored = this;
        LOCAL.set(newContext);
        return LOCAL.get();
    }

    public LogContext recall() {
        if (stored != null) {
            LOCAL.set(stored);
            stored = null;
        }
        return LOCAL.get();
    }

    public LogContext code(String code) {
        this.code = code;
        return this;
    }

    public LogContext resource(String resource) {
        this.resource = resource;
        return this;
    }

    public LogContext object(String object) {
        this.object = object;
        return this;
    }

    public LogContext message(String message) {
        this.message = message;
        return this;
    }

    public LogContext extra(String key, Object value) {
        if (extra == null) {
            extra = new HashMap<>();
        }
        extra.put(key, value);
        return this;
    }

    public LogContext cause(Throwable cause) {
        this.cause = cause;
        return this;
    }

    public LogContext reset() {
        resource = null;
        object = null;
        message = null;
        cause = null;
        LOCAL.remove();
        return this;
    }

    @Override
    public String toString() {
        StringBuilder description = new StringBuilder();
        // message
        if (this.code != null) {
            description.append(LINE_SEPARATOR);
            description.append("### error code ");
            description.append(this.code);
        }

        // message
        if (this.message != null) {
            description.append(LINE_SEPARATOR);
            description.append("### error msg");
            description.append(this.message);
        }

        // resource
        if (resource != null) {
            description.append(LINE_SEPARATOR);
            description.append("### The error may exist in ");
            description.append(resource);
        }

        // object
        if (object != null) {
            description.append(LINE_SEPARATOR);
            description.append("### The error may involve ");
            description.append(object);
        }

        // extra
        if (extra != null) {
            description.append(LINE_SEPARATOR);
            description.append("### The extra info ");
            description.append(extra);
        }

        // cause
        if (cause != null) {
            description.append(LINE_SEPARATOR);
            description.append("### Cause: ");
            description.append(cause.toString());
        }

        return description.toString();
    }

}
