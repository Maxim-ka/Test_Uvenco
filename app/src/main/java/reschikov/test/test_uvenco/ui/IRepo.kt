package reschikov.test.test_uvenco.ui

import kotlinx.coroutines.flow.Flow
import reschikov.test.test_uvenco.data.models.Coffee

interface IRepo {

    fun getCoffees() : Flow<List<Coffee>>
    suspend fun getCoffee(id: Long) : Flow<Coffee>
    suspend fun save(coffee: Coffee)
    fun startTime()
    fun getTimeValue(): Flow<String>
    fun stopTime()
    fun startTemperature()
    fun getTemperatureValue(): Flow<String>
    fun stopTemperature()
}