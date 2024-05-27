package reschikov.test.test_uvenco.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val TABLE_COFFEE = "coffee"
const val COLUMN_NAME = "name"
const val COLUMN_PRICE = "price"
const val COLUMN_URL = "imageUrl"

@Entity(tableName = TABLE_COFFEE)
data class Coffee(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = COLUMN_NAME)
    val name: String,
    @ColumnInfo(name = COLUMN_PRICE)
    val price: String,
    @ColumnInfo(name = COLUMN_URL)
    val imageUrl: String
)
