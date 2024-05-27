package reschikov.test.test_uvenco.data.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import reschikov.test.test_uvenco.data.ICounted
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

abstract class Generator<T>(init: T,
                            private val delay: Long,
                            private val period: Long,
                            private val timeUnit: TimeUnit) : ICounted<T> {

    abstract val action: () -> T
    private val flow = MutableStateFlow(init)
    private val service : ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
    private lateinit var future: ScheduledFuture<*>

    override fun start() {
        if (this::future.isInitialized.not() || future.isCancelled || future.isDone) {
            future = service.scheduleWithFixedDelay({ flow.value = action()  }, delay, period, timeUnit)
        }
    }

    override fun getValue(): Flow<T> {
        if (this::future.isInitialized.not() || future.isCancelled || future.isDone) {
            start()
        }
        return flow
    }

    override fun stop() {
        if (future.isCancelled.not()) future.cancel(true)
    }
}