package com.freeing.common.mail;

import com.freeing.common.mail.entity.News;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class MailSender {
    public static void main(String[] args) {
        MailSender sender = new MailSender();
        List<News> newsList = Arrays.asList(
            new News("标题1", "内容1", "作者1", "2023-01-01", "来源1"),
            new News("标题2", "内容2", "作者2", "2023-01-02", "来源2"),
            new News("标题3", "内容3", "作者3", "2023-01-03", "来源3")
        );
        sender.sendNewsMail(newsList);
    }

    public void sendNewsMail(List<News> list) {
        TemplateEngine templateEngine = mailTemplateEngine();
        Context context = new Context();
        context.setVariable("newsList", list);

        String html = templateEngine.process("news", context);
        System.out.println(html);
    }

    public TemplateEngine mailTemplateEngine() {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();

        resolver.setPrefix("/mail/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resolver.setCacheable(false); // 邮件模板一般不缓存

        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);
        return engine;
    }
}
