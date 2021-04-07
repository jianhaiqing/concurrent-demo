package com.seewo.service.impl;

import com.seewo.service.DemoService;
import com.seewo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.RejectedExecutionException;

/** 
 * @Author: Nuwa 
 * @Description: 由IDEA插件Nuwa生成的类
 */
@Service
public class DemoServiceImpl implements DemoService {

    private final TaskService taskService;

    public DemoServiceImpl(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * 测试创建线程OOM
     *    默认线程池 SimpleAsyncTaskExecutor
     */
    @Override
    public String testThreadOOM() {
        String uuid = UUID.randomUUID().toString();
        try{
            taskService.queryUserInfoDefaultPool(uuid);
        }catch (Throwable ex){
            return "hello " + uuid;
        }
        return "hello " + uuid;
    }

    /**
     * 测试堆OOM
     *    自定义无界队列线程池
     */
    @Override
    public String testHeapOOM() {
        String uuid = UUID.randomUUID().toString();
        String param = getParam();

        taskService.queryUserInfoUnboundedQueue(uuid, param);
        return "hello " + uuid;
    }

    /**
     * 测试内存正常，任务拒绝
     *     自定义有界队列线程池，默认拒绝策略
     */
    @Override
    public String testHeapNotOOM() {
        String uuid = UUID.randomUUID().toString();
        String param = getParam();
        taskService.queryUserInfoboundedQueue(uuid, param);
/*        try {
            taskService.queryUserInfoboundedQueue(uuid, param);
        }catch (RejectedExecutionException exception){
            System.out.println(exception);
        }*/
        return "hello " + uuid;
    }

    /**
     * 测试内存正常，任务提交任务的线程支持
     *     自定义有界队列线程池，默认CallerRun策略
     */
    @Override
    public String testCallerRun() {
        String uuid = UUID.randomUUID().toString();
        String param = getParam();
        try {
            taskService.testCallerRun(uuid, param);
        }catch (RejectedExecutionException exception){
            System.out.println(exception.getMessage());
        }
        return "hello " + uuid;
    }






    private String getParam() {
        return "111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111" +
                "11111111111111111111111111111111111111111111111111111111111111111111111111" +
                "11111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "11111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "11111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "11111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "11111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "11111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "11111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "11111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "11111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "11111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "11111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "11111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "1111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "1111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "1111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "1111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "1111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "1111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "1111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "11111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "1111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "1111111111111111111111111111111111111111111111111111111111111111111111111111111";
    }
}

