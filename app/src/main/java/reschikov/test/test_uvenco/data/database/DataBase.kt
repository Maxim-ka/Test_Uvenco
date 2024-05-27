package reschikov.test.test_uvenco.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import reschikov.test.test_uvenco.data.models.Coffee
import javax.inject.Singleton

@Singleton
@Database(entities = [Coffee::class], version = 1, exportSchema = false)
abstract class DataBase : RoomDatabase()  {

    abstract fun takeCoffeeDao() : CoffeeDao
}