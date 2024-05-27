package reschikov.test.test_uvenco.data

import kotlinx.coroutines.flow.Flow
import reschikov.test.test_uvenco.data.models.Coffee

interface IDatabase {

    fun getCoffees() : Flow<List<Coffee>>
    suspend fun getCoffee(id: Long) : Flow<Coffee>
    suspend fun save(coffee: Coffee)
}