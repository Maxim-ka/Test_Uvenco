package reschikov.test.test_uvenco.data.database

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import reschikov.test.test_uvenco.data.models.COLUMN_NAME
import reschikov.test.test_uvenco.data.models.COLUMN_PRICE
import reschikov.test.test_uvenco.data.models.COLUMN_URL
import reschikov.test.test_uvenco.data.models.TABLE_COFFEE


class FirstLoad : RoomDatabase.Callback() {

    private val sizeDataBase = 20

    override fun onCreate(db: SupportSQLiteDatabase) {
        if (db.isOpen) {
            repeat(sizeDataBase){
                db.insert(TABLE_COFFEE,
                    SQLiteDatabase.CONFLICT_REPLACE,
                    ContentValues().apply {
                        put(COLUMN_NAME, "Капучино эконом")
                        put(COLUMN_PRICE, "199")
                        put(COLUMN_URL, "https://s3-alpha-sig.figma.com/img/a065/5b3f/94860c1bebf809ee8dba77436257bb09?Expires=1717372800&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=plZ-y6Zrq0FdvLpDA6zkk4ul3D04XENPDuW6j8afXbNCMv0kvLSOdlkcnCiIBSKKdDDLcptANBRfHTamZ4QMwdR~dNZIY-6DcfPCG5x6c5vqpWNl3MQBsmMeVMSWGva8Yc7E4hbs0TsU5eKD1kQFMMJyu2tytMMISCxTIZ1kmLfrwvWRlM4vxX1rfEKEdLRT5KRTL-mpH1QIdNfOqr46uV41XXrJ1a5nELJG~TQUsUth77HmX9GdVOUP9CKs7h-e0bW2O9kDC4wkSOWGmVk0mGrtOQiDKPsE4iXuj1PWfjxhQHg754UPRHT7w7umr0PNacC9O84j2sfPhUNlOk6UkA__")
                    }
                )
            }
        }
    }
}