package reschikov.test.test_uvenco.data.temperature

import reschikov.test.test_uvenco.data.base.Generator
import reschikov.test.test_uvenco.data.constance.CELSIUS
import reschikov.test.test_uvenco.data.constance.EMPTY
import java.text.DecimalFormat
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class ShowTemperature @Inject constructor() : Generator<String>(EMPTY, 0, 1, TimeUnit.SECONDS) {

    private val min = 85
    private val max = 95
    private val format = DecimalFormat("00.0")

    override val action: () -> String
        get() = {
            "${format.format(Random.nextInt(min, max) + Random.nextFloat())}$CELSIUS"
        }

}