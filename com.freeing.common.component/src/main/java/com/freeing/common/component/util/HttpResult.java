package com.freeing.common.component.util;

import lombok.Builder;

@Builder
public class HttpResult {
    private int status;		// http返回状态码
    private String body;	// http返回体
    private String error;	// 异常信息
    private byte[] bytes;   // 返回原始字节数据

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
