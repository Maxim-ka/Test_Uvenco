package reschikov.test.test_uvenco.data.database

import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import reschikov.test.test_uvenco.data.models.Coffee
import reschikov.test.test_uvenco.data.models.TABLE_COFFEE

@Dao
interface CoffeeDao {

    @Query("SELECT * FROM $TABLE_COFFEE")
    fun getCoffees(): Flow<List<Coffee>>

    @Query("SELECT * FROM $TABLE_COFFEE where id = :coffeeId")
    fun getCoffee(coffeeId: Long): Flow<Coffee>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun save(coffee: Coffee)
}