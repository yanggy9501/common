package com.freeing.log.controller;

import com.freeing.common.log.annotation.Log;
import com.freeing.common.log.enums.BizType;
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
    @Log(bizType = BizType.QUERY, description = "有参测试")
    public String log1(@RequestBody DataVo dataVo) {
        return "ok";
    }

    @PostMapping("/v2")
    @Log(bizType = BizType.OTHER, description = "无参测试")
    public String log2() {
        return "ok";
    }

    @PostMapping("/v3")
    @Log(bizType = BizType.OTHER, description = "无参测试")
    public String log3() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        return "ok";
    }
}
