# 使用说明

1.   引入该模块

     ```xml
     <dependency>
         <groupId>com.freeing</groupId>
         <artifactId>com.freeing.common.i18n</artifactId>
         <version>1.0</version>
     </dependency>
     ```

     2.   I18n yaml配置

          配置多个

          ```yaml
          spring:
            messages:
            # 配置多个
              basename:
                - i18n/message
                - i18n/other
          ```

          配置一个

          ```yaml
          spring:
            messages:
            # 配置多个
              basename: i18n/message
          ```

          basename 为路径加 i18n 文件前缀，如：basename: i18n/message，其路径为 resources 下：

          ```txt
          resources
            -- i18n
          	-- message_en_US.properties
              -- message_en_US.properties
          ```

          ps：如果引入 i18n 模块而没有，配置 spring.messages.basename 会报错.

2.   使用 I18nUtils

     ```java
     I18nUtils.getMessage(code);
     ```

3.   语言切换

     1.   方式1：自定义拦截器，在拦截器中设置在 request 域中

          ```java
          httpServletRequest.setAttribute("lang", "en_US")
          ```

     2.   方式2：请求 url 设置或者表单设置

          ```http
          http://localhost:8084/?lang=en_US
          ```

     3.   方式3：请求头切换，在请求头中添加 lang 请求头

          ```header
          lang=en_US
          ```
     4.   方式4：I18nUtils 指定语言

4.   占位符使用

     i18n 文件中使用 {n} 进行占位符占位，n 从 0 开始，在使用 I18nUtils 是选择带 Object[] args 传参的 api，如：

     ```i18n
     value.holder=中文：占位符参数={0}
     ```

     