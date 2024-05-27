package reschikov.test.test_uvenco.data.database

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import reschikov.test.test_uvenco.data.IDatabase
import reschikov.test.test_uvenco.data.models.Coffee
import javax.inject.Inject

class DataBaseProvider @Inject constructor(private val dataBase: DataBase) : IDatabase {

    override fun getCoffees(): Flow<List<Coffee>> = dataBase.takeCoffeeDao().getCoffees().flowOn(Dispatchers.IO)

    override suspend fun getCoffee(id: Long): Flow<Coffee> = dataBase.takeCoffeeDao().getCoffee(id).flowOn(Dispatchers.IO)

    override suspend fun save(coffee: Coffee) {
        withContext(Dispatchers.IO) {
            dataBase.takeCoffeeDao().save(coffee)
        }
    }
}