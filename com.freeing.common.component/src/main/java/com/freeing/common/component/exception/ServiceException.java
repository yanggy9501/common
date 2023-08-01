package com.freeing.common.component.exception;

/**
 * 业务异常
 */
public class ServiceException extends BaseException {
    /**
     * 国际化标准
     */
    private boolean sourceMsg;

    public ServiceException(String code, String msg, boolean isSourceMsg, Object... args) {
        super(code, msg, args);
        this.sourceMsg = isSourceMsg;
    }

    public ServiceException(String code, String msg, boolean isSourceMsg) {
        super(code, msg);
        this.sourceMsg = isSourceMsg;
    }

    public ServiceException(String code, String msg) {
        super(code, msg);
    }

    public boolean isSourceMsg() {
        return sourceMsg;
    }

    public void setSourceMsg(boolean sourceMsg) {
        this.sourceMsg = sourceMsg;
    }
}
