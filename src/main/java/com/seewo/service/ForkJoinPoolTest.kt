package com.seewo.service

import java.util.*
import java.util.concurrent.*


/**
 * @Author LJTjintao
 * @Date 2021/4/5
 * @Description TODO
 */
class ForkJoinPoolTest{
    fun singleThreadSum(arr: LongArray) {
        val start = System.currentTimeMillis()
        var sum: Long = 0
        for (i in arr.indices) {
            // 模拟耗时，本文由公从号“彤哥读源码”原创
            sum += arr[i] / 3 * 3 / 3 * 3 / 3 * 3 / 3 * 3 / 3 * 3
        }
        println("sum: $sum")
        println("single thread elapse: " + (System.currentTimeMillis() - start))
    }

    fun multiThreadSum(arr: LongArray) {
        val start = System.currentTimeMillis()
        val count = 8
        val threadPool = Executors.newFixedThreadPool(count)
        val list: MutableList<Future<Long>> = ArrayList<Future<Long>>()
        for (i in 0 until count) {
            // 分段提交任务
            val future: Future<Long> = threadPool.submit<Long> {
                var sum: Long = 0
                for (j in arr.size / count * i until arr.size / count * (i + 1)) {
                    try {
                        // 模拟耗时
                        sum += arr[j] / 3 * 3 / 3 * 3 / 3 * 3 / 3 * 3 / 3 * 3
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                sum
            }
            list.add(future)
        }

        // 每个段结果相加
        var sum: Long = 0
        for (future in list) {
            sum += future.get()
        }
        println("sum: $sum")
        println("multi thread elapse: " + (System.currentTimeMillis() - start))
    }

    fun forkJoinSum(arr: LongArray) {
        val start = System.currentTimeMillis()
        val forkJoinPool: ForkJoinPool = ForkJoinPool.commonPool()
        // 提交任务
        val forkJoinTask: ForkJoinTask<Long> = forkJoinPool.submit(SumTask(arr, 0, arr.size))
        // 获取结果
        val sum: Long = forkJoinTask.get()
        forkJoinPool.shutdown()
        println("sum: $sum")
        println("fork join elapse: " + (System.currentTimeMillis() - start))
    }
}

fun main() {
    val forkJoinTest = ForkJoinPoolTest()
    // 构造数据
    // 构造数据
    val length = 100000000
    val arr = LongArray(length)
    for (i in 0 until length) {
        arr[i] = ThreadLocalRandom.current().nextInt(Int.MAX_VALUE).toLong()
    }

    // 单线程
    // 单线程
    forkJoinTest.singleThreadSum(arr)
    // ThreadPoolExecutor线程池
    // ThreadPoolExecutor线程池
    forkJoinTest.multiThreadSum(arr)
    // ForkJoinPool线程池
    // ForkJoinPool线程池
    forkJoinTest.forkJoinSum(arr)

}

private class SumTask(private val arr: LongArray, private val from: Int, private val to: Int) : RecursiveTask<Long>() {
    override fun compute(): Long {
        // 小于1000的时候直接相加，可灵活调整
        if (to - from <= 1000) {
            var sum: Long = 0
            for (i in from until to) {
                // 模拟耗时
                sum += arr[i] / 3 * 3 / 3 * 3 / 3 * 3 / 3 * 3 / 3 * 3
            }
            return sum
        }

        // 分成两段任务
        val middle = (from + to) / 2
        val left = SumTask(arr, from, middle)
        val right = SumTask(arr, middle, to)

        // 提交左边的任务
        left.fork()
        // 右边的任务直接利用当前线程计算，节约开销
        val rightResult = right.compute()
        // 等待左边计算完毕
        val leftResult = left.join()
        // 返回结果
        return leftResult!! + rightResult
    }
}
