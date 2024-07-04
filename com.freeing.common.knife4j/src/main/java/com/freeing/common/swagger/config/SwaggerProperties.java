package com.freeing.common.swagger.config;

import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * swagger2 属性配置
 *
 * @author yanggy
 */
@Reference
@ConfigurationProperties(prefix = "app.swagger")
public class SwaggerProperties {
    /**
     * 是否开启swagger
     **/
    private Boolean enabled = true;

    /**
     * 标题
     **/
    private String title = "在线文档";

    /**
     * 接口分组-组名
     */
    private String group = "";

    /**
     * 描述
     **/
    private String description = "在线文档";

    /**
     * 版本
     **/
    private String version = "1.0";

    /**
     * 许可证 URL
     **/
    private String licenseUrl = "";

    /**
     * 服务条款URL
     **/
    private String termsOfServiceUrl = "";

    /**
     * 联系人
     */
    private Contact contact = new Contact();

    /**
     * swagger 解析的包路径
     **/
    private String basePackage = "";

    /**
     * swagger 解析的url规则, 该路径为ant路径
     * 指定controller中哪些url会被解析成文档
     **/
    private List<String> basePath = new ArrayList<>();

    /**
     * 在basePath基础上需要排除的url规则
     **/
    private List<String> excludePath = new ArrayList<>();

    /**
     * 分组文档
     **/
    private Map<String, DocketInfo> docket = new LinkedHashMap<>();

    /**
     * host信息
     **/
    private String host = "";

    /**
     * 排序
     */
    private Integer order = 1;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLicenseUrl() {
        return licenseUrl;
    }

    public void setLicenseUrl(String licenseUrl) {
        this.licenseUrl = licenseUrl;
    }

    public String getTermsOfServiceUrl() {
        return termsOfServiceUrl;
    }

    public void setTermsOfServiceUrl(String termsOfServiceUrl) {
        this.termsOfServiceUrl = termsOfServiceUrl;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public List<String> getBasePath() {
        return basePath;
    }

    public void setBasePath(List<String> basePath) {
        this.basePath = basePath;
    }

    public List<String> getExcludePath() {
        return excludePath;
    }

    public void setExcludePath(List<String> excludePath) {
        this.excludePath = excludePath;
    }

    public Map<String, DocketInfo> getDocket() {
        return docket;
    }

    public void setDocket(Map<String, DocketInfo> docket) {
        this.docket = docket;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    /**
     * 接口文档
     */
    public static class DocketInfo {
        /**
         * 标题
         **/
        private String title = "在线文档";

        /**
         * 自定义组名
         */
        private String group = "";

        /**
         * 描述
         **/
        private String description = "在线文档";

        /**
         * 版本
         **/
        private String version = "1.0";

        /**
         * 许可证
         **/
        private String license = "";

        /**
         * 许可证URL
         **/
        private String licenseUrl = "";

        /**
         * 服务条款URL
         **/
        private String termsOfServiceUrl = "";

        /**
         * 联系人
         */
        private Contact contact = new Contact();

        /**
         * swagger会解析的包路径
         **/
        private String basePackage = "";

        /**
         * swagger 解析的url规则, 该路径为ant路径
         * 指定controller中哪些url会被解析成文档
         **/
        private List<String> basePath = new ArrayList<>();

        /**
         * 在basePath基础上需要排除的url规则
         **/
        private List<String> excludePath = new ArrayList<>();

        /**
         * 排序
         */
        private Integer order = 1;


        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getLicense() {
            return license;
        }

        public void setLicense(String license) {
            this.license = license;
        }

        public String getLicenseUrl() {
            return licenseUrl;
        }

        public void setLicenseUrl(String licenseUrl) {
            this.licenseUrl = licenseUrl;
        }

        public String getTermsOfServiceUrl() {
            return termsOfServiceUrl;
        }

        public void setTermsOfServiceUrl(String termsOfServiceUrl) {
            this.termsOfServiceUrl = termsOfServiceUrl;
        }

        public Contact getContact() {
            return contact;
        }

        public void setContact(Contact contact) {
            this.contact = contact;
        }

        public String getBasePackage() {
            return basePackage;
        }

        public void setBasePackage(String basePackage) {
            this.basePackage = basePackage;
        }

        public List<String> getBasePath() {
            return basePath;
        }

        public void setBasePath(List<String> basePath) {
            this.basePath = basePath;
        }

        public List<String> getExcludePath() {
            return excludePath;
        }

        public void setExcludePath(List<String> excludePath) {
            this.excludePath = excludePath;
        }

        public Integer getOrder() {
            return order;
        }

        public void setOrder(Integer order) {
            this.order = order;
        }

        public String getGroup() {
            if (group == null || "".equals(group)) {
                return title;
            }
            return group;
        }
    }

    public String getGroup() {
        if (group == null || "".equals(group)) {
            return title;
        }
        return group;
    }

    /**
     * 联系人
     */
    public static class Contact {
        /**
         * 联系人
         **/
        private String name = "";

        /**
         * 联系人url
         **/
        private String url = "";

        /**
         * 联系人email
         **/
        private String email = "";

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
