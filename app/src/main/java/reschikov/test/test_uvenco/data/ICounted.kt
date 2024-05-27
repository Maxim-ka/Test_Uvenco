package reschikov.test.test_uvenco.data

import kotlinx.coroutines.flow.Flow

interface ICounted<T> {
    fun start()
    fun getValue(): Flow<T>
    fun stop()
}