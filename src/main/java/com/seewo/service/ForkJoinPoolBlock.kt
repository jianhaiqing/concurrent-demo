package com.seewo.service

/**
 * @Author LJTjintao
 * @Date 2021/4/6
 * @Description TODO
 */
fun main(args: Array<String>) {
    System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "2")
    val nums = 1 until 4
    repeat(nums.count()) {
        MyThread(1).start()
    }
    val thread2 = MyThread(2)
    Thread.sleep((1000).toLong())
    thread2.start()
}

class MyThread(index: Int) : Thread() {
    private val theIndex = index
    override fun run() {
        if(theIndex == 1) {
            listOf(10, 20, 30, 40, 50 ,60 ,70, 11, 21, 31, 41, 55 ,61 ,71).parallelStream().forEach { num ->
                try {
                    sleep((4 * 1000).toLong())
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                //println("$theIndex:" + currentThread().name + ">>" + num)
            }
        }else{
            val start = System.currentTimeMillis()
            println("$theIndex start time : $start" )
            listOf(10, 20, 30, 40, 50 ,60 ,70).parallelStream().forEach { num ->
                try {
                    sleep((1000).toLong())
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                println("$theIndex:" + currentThread().name + ">>" + num)
            }
            val end = System.currentTimeMillis()
            println("$theIndex end time : $end, cost is ${end - start}" )
        }
    }
}