package com.seewo.service

import java.util.concurrent.CountDownLatch




/**
 * @Author LJTjintao
 * @Date 2021/3/30
 * @Description TODO
 */
object TestNativeOutOfMemoryError {
    @JvmStatic
    fun main(args: Array<String>) {
        var i = 0
        while (true) {
            println("i = $i")
            Thread(HoldThread()).start()
            i++
        }
    }
}

internal class HoldThread : Thread() {
    var cdl = CountDownLatch(1)
    override fun run() {
        try {
            cdl.await()
        } catch (e: InterruptedException) {
        }
    }

    init {
        this.isDaemon = true
    }
}