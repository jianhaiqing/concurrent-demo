package com.seewo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import com.seewo.service.DemoService;
import com.seewo.dto.DemoDto;
import com.seewo.honeycomb.web.support.ApiResult;

/** 
 * @Author: Nuwa 
 * @Description: 由IDEA插件Nuwa生成的类
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @Resource
    private DemoService demoService;

    /**
     * 测试线程OOM
     */
    @GetMapping("/threadOOM")
    public ApiResult threadOOM() {
        return ApiResult.success(demoService.testThreadOOM());
    }

    /**
     * 测试堆OOM
     */
    @GetMapping("/heapOOM")
    public ApiResult heapOOM() {
        return ApiResult.success(demoService.testHeapOOM());
    }

    /**
     * 测试堆正常
     */
    @GetMapping("/heapNotOOM")
    public ApiResult heapNotOOM() {
        return ApiResult.success(demoService.testHeapNotOOM());
    }

    /**
     * 自己执行
     */
    @GetMapping("/callerRun")
    public ApiResult callerRun() {
        return ApiResult.success(demoService.testCallerRun());
    }
}
