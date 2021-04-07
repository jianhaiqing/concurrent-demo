package com.seewo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.Executor
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

/**
 * @Author LJTjintao
 * @Date 2021/3/11
 * @Description TODO
 */
@Configuration
open class ThreadPoolExecutorConfig {
    /**
     * 无界队列异步线程池
     * @return
     */
    @Bean("demoExecutor")
    open fun taskExecutor(): Executor {
        val tag = AtomicInteger(0)
        return ThreadPoolExecutor(5,
            10,
            60L,
            TimeUnit.SECONDS,
            LinkedBlockingQueue()
        ) { runnable ->
            val thread = Thread(runnable)
            thread.name = "demoExecutor-thread-${tag.getAndIncrement()}"
            thread
        }
    }

    /**
     * 有界队列异步线程池
     * @return
     */
    @Bean("boundExecutor")
    open fun boundedTaskExecutor(): Executor {
        val tag = AtomicInteger(0)

        Thread().start()
        return ThreadPoolExecutor(5,
            10,
            60L,
            TimeUnit.SECONDS,
            LinkedBlockingQueue(200)
        ) { runnable ->
            val thread = Thread(runnable)
            thread.name = "boundExecutor-thread-${tag.getAndIncrement()}"
            thread
        }
    }

    /**
     * 有界队列异步线程池, callerRun 拒绝策略
     * @return
     */
    @Bean("callerRunExecutor")
    open fun callerRunExecutor(): Executor {
        val tag = AtomicInteger(0)
        return ThreadPoolExecutor(5,
            10,
            60L,
            TimeUnit.SECONDS,
            LinkedBlockingQueue(200), { runnable ->
            val thread = Thread(runnable)
            thread.name = "test-thread-${tag.getAndIncrement()}"
            thread
        },ThreadPoolExecutor.CallerRunsPolicy())
    }
}