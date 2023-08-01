package com.freeing.common.component.exception;

/**
 * 操作失败，终止运行异常
 *
 * @author yanggy
 */
public class FailOperationException extends BaseException {
    /**
     * 国际化标准
     */
    private boolean sourceMsg;

    public FailOperationException(String code, String msg, boolean isSourceMsg, Object... args) {
        super(code, msg, args);
        this.sourceMsg = isSourceMsg;
    }

    public FailOperationException(String code, String msg, boolean isSourceMsg) {
        super(code, msg);
        this.sourceMsg = isSourceMsg;
    }

    public FailOperationException(String code, String msg) {
        super(code, msg);
    }

    public boolean isSourceMsg() {
        return sourceMsg;
    }

    public void setSourceMsg(boolean sourceMsg) {
        this.sourceMsg = sourceMsg;
    }
}
