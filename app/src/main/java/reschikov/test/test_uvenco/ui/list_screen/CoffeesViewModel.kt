package reschikov.test.test_uvenco.ui.list_screen

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.flowWithLifecycle
import dagger.hilt.android.lifecycle.HiltViewModel
import reschikov.test.test_uvenco.ui.IRepo
import javax.inject.Inject

@HiltViewModel
class CoffeesViewModel @Inject constructor(private val iRepo: IRepo) : ViewModel() {

    init {
        iRepo.startTime()
        iRepo.startTemperature()
    }

    fun getCoffees(lifecycle: Lifecycle) = iRepo.getCoffees().flowWithLifecycle(lifecycle)

    fun getTime(lifecycle: Lifecycle) = iRepo.getTimeValue().flowWithLifecycle(lifecycle)

    fun getTemperature(lifecycle: Lifecycle) = iRepo.getTemperatureValue().flowWithLifecycle(lifecycle)

    override fun onCleared() {
        super.onCleared()
        iRepo.stopTime()
        iRepo.stopTemperature()
    }
}