package com.seewo.service

import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

/**
 * @Author LJTjintao
 * @Date 2021/3/10
 * @Description TODO
 */
@Service
open class TaskService {

    @Async("demoExecutor")
    open fun queryUserInfoUnboundedQueue(userId: String, param: String): String {
        Thread.sleep(2000 * 100)
        println(Thread.currentThread().name + "sleep finish userId is $userId")
        return userId
    }

    @Async("boundExecutor")
    open fun queryUserInfoboundedQueue(userId: String, param: String): String {
        Thread.sleep(2000)
        println(Thread.currentThread().name + "sleep finish userId is $userId")
        return userId
    }

    @Async
    open fun queryUserInfoDefaultPool(userId: String): String {
        println(Thread.currentThread().name + "sleep finish userId is $userId")
        Thread.sleep(2000*100)
        return userId
    }

    @Async("callerRunExecutor")
    open fun testCallerRun(userId: String, param: String): String {
        Thread.sleep(2000)
        println(Thread.currentThread().name + "sleep finish userId is $userId")
        return userId
    }

}