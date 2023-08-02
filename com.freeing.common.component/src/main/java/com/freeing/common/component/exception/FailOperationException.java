package com.freeing.common.component.exception;

/**
 * 操作失败，终止运行异常
 *
 * @author yanggy
 */
public class FailOperationException extends BaseException {
    /**
     * 国际化 msg id
     */
    private String msgId;

    public FailOperationException(String code, String msg, String msgId, Object... args) {
        super(code, msg, args);
        this.msgId = msgId;
    }

    public FailOperationException(String code, String msg, String msgId) {
        super(code, msg);
        this.msgId = msgId;
    }

    public FailOperationException(String code, String msg) {
        super(code, msg);
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
}
