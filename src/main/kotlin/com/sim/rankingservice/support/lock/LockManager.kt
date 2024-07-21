package com.sim.rankingservice.support.lock

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.locks.ReentrantLock

object LockManager {

    private sealed interface LockHandler {
        fun <R> lockByKey(key: String, time: Long = 10, timeUnit: TimeUnit = TimeUnit.SECONDS, body: () -> R): R
    }

    object NON_DISTRIBUTED : LockHandler {
        private val lockMap = ConcurrentHashMap<String, ReentrantLock>()
        private val lockCountMap = ConcurrentHashMap<String, AtomicInteger>()

        override fun <R> lockByKey(key: String, time: Long, timeUnit: TimeUnit, body: () -> R): R {
            val lock = lockMap.getOrPut(key) { ReentrantLock() }
            val lockCount = lockCountMap.getOrPut(key) { AtomicInteger(0) }
            lockCount.incrementAndGet()
            if (lock.tryLock(time, timeUnit)) {
                try {
                    return body()
                } finally {
                    lock.unlock()
                    if (lockCount.decrementAndGet() == 0) {
                        lockMap.remove(key)
                        lockCountMap.remove(key)
                    }
                }
            } else {
                throw RuntimeException("Lock timeout")
            }
        }

    }
}