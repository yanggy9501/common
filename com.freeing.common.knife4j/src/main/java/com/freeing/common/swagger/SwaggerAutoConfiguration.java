package com.freeing.common.swagger;

import com.freeing.common.swagger.config.SwaggerProperties;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * swagger 自动配置类
 * 服务URL: http://localhost:port/doc.html
 * 配置案例：
 * 案例1 - 分组文档方式，要求必须至少有两个分组
 * app:
 *   swagger:
 *     enabled: true #是否启用swagger
 *     docket:
 *       user:
 *         title: 用户模块
 *         base-package: com.app.knife4j.controller.user
 *       menu:
 *         title: 菜单模块
 *         base-package: com.demo.knife4j.controller.menu
 *
 * 案例2 - 单组文档方式
 *  app:
 *   swagger:
 *     enabled: true
 *     title: 在线接口文档
 *     base-package: com.app.knife4j.controller
 *
 * @author yanggy
 */
@Configuration
@EnableSwagger2
@EnableConfigurationProperties(SwaggerProperties.class)
@ConditionalOnProperty(name = "app.swagger.enabled", havingValue = "true", matchIfMissing = true)
public class SwaggerAutoConfiguration implements BeanFactoryAware {

    private BeanFactory beanFactory;


    @Autowired
    SwaggerProperties swaggerProperties;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "app.swagger.enabled", havingValue = "true", matchIfMissing = true)
    public List<Docket> createRestApi() {
        ConfigurableBeanFactory configurableBeanFactory = (ConfigurableBeanFactory) beanFactory;
        List<Docket> docketList = new LinkedList<>();
        // 没有分组
        if (swaggerProperties.getDocket().isEmpty()) {
            Docket docket = createDocket(swaggerProperties);
            configurableBeanFactory.registerSingleton(swaggerProperties.getTitle(), docket);
            docketList.add(docket);
        } else {
        // 分组
            for (String groupName : swaggerProperties.getDocket().keySet()){
                SwaggerProperties.DocketInfo docketInfo = swaggerProperties.getDocket().get(groupName);
                ApiInfo apiInfo = new ApiInfoBuilder()
                    .title(docketInfo.getTitle().isEmpty() ? swaggerProperties.getTitle() : docketInfo.getTitle())
                    .description(docketInfo.getDescription().isEmpty() ?
                        swaggerProperties.getDescription() : docketInfo.getDescription())
                    .version(docketInfo.getVersion().isEmpty() ?
                        swaggerProperties.getVersion() : docketInfo.getVersion())
                    .licenseUrl(docketInfo.getLicenseUrl().isEmpty() ?
                        swaggerProperties.getLicenseUrl() : docketInfo.getLicenseUrl())
                    .contact(new Contact(
                            docketInfo.getContact().getName().isEmpty() ?
                                swaggerProperties.getContact().getName() : docketInfo.getContact().getName(),
                            docketInfo.getContact().getUrl().isEmpty() ?
                                swaggerProperties.getContact().getUrl() : docketInfo.getContact().getUrl(),
                            docketInfo.getContact().getEmail().isEmpty() ?
                                swaggerProperties.getContact().getEmail() : docketInfo.getContact().getEmail()
                        )
                    )
                    .termsOfServiceUrl(docketInfo.getTermsOfServiceUrl().isEmpty() ?
                        swaggerProperties.getTermsOfServiceUrl() : docketInfo.getTermsOfServiceUrl())
                    .build();

                // base-path处理
                // 当没有配置任何path的时候，解析/**
                if (docketInfo.getBasePath().isEmpty()) {
                    docketInfo.getBasePath().add("/**");
                }
                List<Predicate<String>> basePath = new ArrayList<>(docketInfo.getBasePath().size());
                for (String path : docketInfo.getBasePath()) {
                    basePath.add(PathSelectors.ant(path));
                }

                // exclude-path处理
                List<Predicate<String>> excludePath = new ArrayList<>(docketInfo.getExcludePath().size());
                for (String path : docketInfo.getExcludePath()) {
                    excludePath.add(PathSelectors.ant(path));
                }
                Docket docket = new Docket(DocumentationType.SWAGGER_2)
                    .host(swaggerProperties.getHost())
                    .apiInfo(apiInfo)
                    .groupName(docketInfo.getGroup())
                    .select()
                    .apis(RequestHandlerSelectors.basePackage(docketInfo.getBasePackage()))
                    .paths(Predicates.and(Predicates.not(Predicates.or(excludePath)), Predicates.or(basePath)))
                    .build();
                configurableBeanFactory.registerSingleton(groupName, docket);
                docketList.add(docket);
            }
        }
        return docketList;
    }

    /**
     *  创建接口文档对象
     *
     * @param swaggerProperties swaggerProperties配置属性
     * @return swagger文档对象
     */
    private Docket createDocket(SwaggerProperties swaggerProperties) {
        // API 基础信息
        ApiInfo apiInfo = new ApiInfoBuilder()
            .title(swaggerProperties.getTitle())
            .description(swaggerProperties.getDescription())
            .version(swaggerProperties.getVersion())
            .licenseUrl(swaggerProperties.getLicenseUrl())
            .contact(new Contact(
                swaggerProperties.getContact().getName(),
                swaggerProperties.getContact().getUrl(),
                swaggerProperties.getContact().getEmail()))
            .termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl())
            .build();

        // base-path处理
        // 当没有配置任何path的时候，解析/**
        if (swaggerProperties.getBasePath().isEmpty()) {
            swaggerProperties.getBasePath().add("/**");
        }
        List<Predicate<String>> basePath = new ArrayList<>();
        for (String path : swaggerProperties.getBasePath()) {
            basePath.add(PathSelectors.ant(path));
        }

        // exclude-path处理
        List<Predicate<String>> excludePath = new ArrayList<>();
        for (String path : swaggerProperties.getExcludePath()) {
            excludePath.add(PathSelectors.ant(path));
        }

        return new Docket(DocumentationType.SWAGGER_2)
            .host(swaggerProperties.getHost())
            .apiInfo(apiInfo)
            .groupName(swaggerProperties.getGroup())
            .select()
            .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
            .paths(Predicates.and(Predicates.not(Predicates.or(excludePath)), Predicates.or(basePath)))
            .build();
    }
}
