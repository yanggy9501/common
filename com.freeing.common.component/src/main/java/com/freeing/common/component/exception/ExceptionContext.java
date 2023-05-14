package com.freeing.common.component.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * Exception 上下文
 *
 * @author yanggy
 */
public class ExceptionContext {

    private static final String LINE_SEPARATOR = System.getProperty("line.separator","\n");
    private static final ThreadLocal<ExceptionContext> LOCAL = new ThreadLocal<>();

    /**
     * 下一个异常存储（跨线程时使用）
     */
    private ExceptionContext stored;

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

    private ExceptionContext() {
    }

    public static ExceptionContext instance() {
        ExceptionContext context = LOCAL.get();
        if (context == null) {
            context = new ExceptionContext();
            LOCAL.set(context);
        }
        return context;
    }

    /**
     * 存储当前的 ExceptionContext，返回新的 ExceptionContext，并将当前的 ExceptionContext 存储到新的 ExceptionContext
     * 同时将新 ExceptionContext 绑定到当前线程
     *
     * @return new ExceptionContext
     */
    public ExceptionContext store() {
        ExceptionContext newContext = new ExceptionContext();
        newContext.stored = this;
        LOCAL.set(newContext);
        return LOCAL.get();
    }

    /**
     * 当前 ExceptionContext 召回其存储的 ExceptionContext，同时将自己的 stored 置空
     *
     * @return
     */
    public ExceptionContext recall() {
        if (stored != null) {
            LOCAL.set(stored);
            stored = null;
        }
        return LOCAL.get();
    }

    public ExceptionContext code(String code) {
        this.code = code;
        return this;
    }

    public ExceptionContext resource(String resource) {
        this.resource = resource;
        return this;
    }

    public ExceptionContext object(String object) {
        this.object = object;
        return this;
    }

    public ExceptionContext message(String message) {
        this.message = message;
        return this;
    }

    public ExceptionContext extra(String key, Object value) {
        if (extra == null) {
            extra = new HashMap<>();
        }
        extra.put(key, value);
        return this;
    }

    public ExceptionContext cause(Throwable cause) {
        this.cause = cause;
        return this;
    }

    public ExceptionContext reset() {
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
        // code
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

        if (stored != null) {
            description.append(LINE_SEPARATOR);
            description.append("### More exception context info: ");
            description.append(recall());
        }
        return description.toString();
    }

}
