package com.freeing.log.controller;

import com.freeing.common.log.annotation.Log;
import com.freeing.common.log.enums.BusinessType;
import com.freeing.log.entity.DataVo;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * @author yanggy
 */
@RestController
@RequestMapping("/log")
public class DemoController {

    @PostMapping("/v1")
    @Log(bizType = BusinessType.QUERY, desc = "有参测试", module = "测试模块", saveParma = false, saveResult = false)
    public String log1(@RequestBody DataVo dataVo) {
        return "ok";
    }

    @PostMapping("/v2")
    @Log(bizType = BusinessType.OTHER, desc = "无参测试", module = "测试模块")
    public String log2() {
        return "ok";
    }

    @PostMapping("/v3")
    @Log(bizType = BusinessType.OTHER, desc = "无参测试", module = "测试模块")
    public String log3() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        return "ok";
    }
}
