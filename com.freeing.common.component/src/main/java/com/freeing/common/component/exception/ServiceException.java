package com.freeing.common.component.exception;

/**
 * 业务异常
 */
public class ServiceException extends BaseException {
    /**
     * 国际化标准
     */
    private String msgId;

    public ServiceException(String code, String msg, String msgId, Object... args) {
        super(code, msg, args);
        this.msgId = msgId;
    }

    public ServiceException(String code, String msg, String msgId) {
        super(code, msg);
        this.msgId = msgId;
    }

    public ServiceException(String code, String msg) {
        super(code, msg);
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
}
