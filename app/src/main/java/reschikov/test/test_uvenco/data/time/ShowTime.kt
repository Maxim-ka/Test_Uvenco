package reschikov.test.test_uvenco.data.time

import reschikov.test.test_uvenco.data.base.Generator
import reschikov.test.test_uvenco.data.constance.EMPTY
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShowTime @Inject constructor() : Generator<String>(EMPTY, 0, 1, TimeUnit.MINUTES) {

    private val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())

    override val action: () -> String
        get() = { formatter.format(Date(System.currentTimeMillis())) }

}