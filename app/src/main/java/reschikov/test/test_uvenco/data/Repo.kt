package reschikov.test.test_uvenco.data

import kotlinx.coroutines.flow.Flow
import reschikov.test.test_uvenco.data.models.Coffee
import reschikov.test.test_uvenco.di.Temperature
import reschikov.test.test_uvenco.di.Time
import reschikov.test.test_uvenco.ui.IRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repo @Inject constructor(
    private val iDatabase: IDatabase,
    @Time val iTimer: ICounted<String>,
    @Temperature val iTemperature: ICounted<String>
) : IRepo {

    override fun getCoffees(): Flow<List<Coffee>> {
        return iDatabase.getCoffees()
    }

    override suspend fun getCoffee(id: Long): Flow<Coffee> {
        return iDatabase.getCoffee(id)
    }

    override suspend fun save(coffee: Coffee) {
        iDatabase.save(coffee)
    }

    override fun startTime() {
        iTimer.start()
    }

    override fun getTimeValue(): Flow<String> {
        return iTimer.getValue()
    }

    override fun stopTime() {
        iTimer.stop()
    }

    override fun startTemperature() {
        iTemperature.start()
    }

    override fun getTemperatureValue(): Flow<String> {
        return iTemperature.getValue()
    }

    override fun stopTemperature() {
        iTemperature.stop()
    }
}