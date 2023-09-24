package com.freeing.common.log.enums;

import java.util.Objects;

/**
 * 业务操作类型
 *
 * @author yanggy
 */
public enum BizType {
    /**
     * 新增
     */
    INSERT(1, "insert"),

    /**
     * 删除
     */
    DELETE(2, "delete"),

    /**
     * 修改
     */
    UPDATE(3, "update"),

    /**
     * 查询
     */
    QUERY(4, "query"),

    /**
     * 授权
     */
    GRANT(5, "grant"),

    /**
     * 导出
     */
    EXPORT(6, "export"),

    /**
     * 导入
     */
    IMPORT(7, "import"),

    /**
     * 下载
     */
    DOWNLOAD(8, "download"),

    /**
     * 上传
     */
    UPLOAD(9, "download"),

    /**
     * 登录
     */
    LOGIN(10,  "login"),

    /**
     * 退出
     */
    LOGOUT(10,  "logout"),

    /**
     * 其它
     */
    OTHER(0, "other"),

    /**
     * 不存在的错误操作
     */
    NULL(-1, "null");

    private final String type;

    /**
     * 操作代码
     */
    private final Integer code;

    BizType(Integer code, String type) {
        this.code = code;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public Integer getCode() {
        return code;
    }

    public static BizType match(String type) {
        for (BizType value : BizType.values()) {
            if (Objects.equals(value.getType(), type)) {
                return value;
            }
        }
        return NULL;
    }

    public static BizType match(Integer code) {
        for (BizType value : BizType.values()) {
            if (Objects.equals(value.getCode(), code)) {
                return value;
            }
        }
        return NULL;
    }
}