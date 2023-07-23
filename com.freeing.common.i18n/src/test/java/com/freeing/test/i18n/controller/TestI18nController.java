package com.freeing.test.i18n.controller;

import com.freeing.common.i18n.util.I18nUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanggy
 */
@RestController
public class TestI18nController {

    /**
     * http://localhost:8084/?code=language.name&lang=en_US
     * lang=en_US 语言切换
     *
     * @param code
     * @return
     */
    @GetMapping
    public String getCode(String code) {
        return I18nUtils.getMessage(code);
    }

    @GetMapping("/v2")
    public String getCode2(String code) {
        String[] strings = {"param"};
        return I18nUtils.getMessage(code, strings);
    }
}
