package com.freeing.common.log.model;

import com.freeing.common.log.enums.BizType;

import java.util.Date;
import java.util.Map;

/**
 * 操作日志
 *
 * @author yanggy
 */
public class AuditLog {
    /**
     * 日志主键
     */
    private String id;

    /**
     * 业务类型 {@link BizType}
     */
    private String businessType;

    /**
     * 操作描述
     */
    private String description;

    /**
     * 模块
     */
    private String module;

    /**
     * 操作人 ID
     */
    private String operatorId;

    /**
     * 操作人
     */
    private String operatorName;

    /**
     * 请求 IP
     */
    private String requestIp;

    /**
     * 类路径
     */
    private String classPath;

    /**
     * 类方法
     */
    private String actionMethod;

    /**
     * 请求地址
     */
    private String requestUri;

    /**
     * 请求类型 HttpMethod {GET; POST; PUT; DELETE; PATCH; TRACE; HEAD; OPTIONS}
     */
    private String httpMethod;

    /**
     * 请求参数
     */
    private String params;

    /**
     * 返回值
     */
    private String result;

    /**
     * 异常详情信息
     */
    private String exDesc;

    /**
     * 异常描述
     */
    private String exDetail;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 完成时间
     */
    private Date endTime;

    /**
     * 消耗时间，单位纳秒
     */
    private Long elapsedNanos;

    /**
     * 消耗时间，如 15 ms
     */
    private String elapsedTime;

    /**
     * 操作状态，成功，失败
     */
    private String status;

    /**
     * 其他额外信息
     */
    private Map<String, Object> extra;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public String getActionMethod() {
        return actionMethod;
    }

    public void setActionMethod(String actionMethod) {
        this.actionMethod = actionMethod;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getExDesc() {
        return exDesc;
    }

    public void setExDesc(String exDesc) {
        this.exDesc = exDesc;
    }

    public String getExDetail() {
        return exDetail;
    }

    public void setExDetail(String exDetail) {
        this.exDetail = exDetail;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getElapsedNanos() {
        return elapsedNanos;
    }

    public void setElapsedNanos(Long elapsedNanos) {
        this.elapsedNanos = elapsedNanos;
    }

    public String getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(String elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, Object> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, Object> extra) {
        this.extra = extra;
    }

    @Override
    public String toString() {
        return "AuditLog{" +
            "id=" + id +
            ", businessType='" + businessType + '\'' +
            ", description='" + description + '\'' +
            ", module='" + module + '\'' +
            ", operatorId='" + operatorId + '\'' +
            ", operatorName='" + operatorName + '\'' +
            ", requestIp='" + requestIp + '\'' +
            ", classPath='" + classPath + '\'' +
            ", actionMethod='" + actionMethod + '\'' +
            ", requestUri='" + requestUri + '\'' +
            ", httpMethod='" + httpMethod + '\'' +
            ", params='" + params + '\'' +
            ", result='" + result + '\'' +
            ", exDesc='" + exDesc + '\'' +
            ", exDetail='" + exDetail + '\'' +
            ", startTime=" + startTime +
            ", endTime=" + endTime +
            ", elapsedNanos=" + elapsedNanos +
            ", elapsedTime='" + elapsedTime + '\'' +
            ", status='" + status + '\'' +
            ", extra=" + extra +
            '}';
    }
}
