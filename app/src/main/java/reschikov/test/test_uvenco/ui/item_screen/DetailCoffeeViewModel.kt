package reschikov.test.test_uvenco.ui.item_screen

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import reschikov.test.test_uvenco.data.constance.EMPTY
import reschikov.test.test_uvenco.data.constance.ZERO
import reschikov.test.test_uvenco.data.models.Coffee
import reschikov.test.test_uvenco.di.IFactoryDetailCoffeeViewModel
import reschikov.test.test_uvenco.ui.IRepo
import java.math.BigDecimal

@Immutable
class DetailCoffeeViewModel @AssistedInject constructor(
    private val iRepo: IRepo,
    @Assisted val id: Long
) : ViewModel(){

    companion object {


        fun provideFactory(
            factory: IFactoryDetailCoffeeViewModel,
            id: Long
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return factory.create(id) as T
            }
        }
    }

    private val regexPrice = Regex("\\d+\\.?\\d{0,2}")
    private val regexEmpty = Regex("0+\\.?0*")
    private val stateCoffee = mutableStateOf<Coffee?>(null)
    private val stateName = mutableStateOf(EMPTY)
    private val statePrice = mutableStateOf(EMPTY)
    private val stateIcon = mutableStateOf(EMPTY)

    init {
        iRepo.startTime()
        iRepo.startTemperature()
        viewModelScope.launch {
            iRepo.getCoffee(id).collect {
                stateCoffee.value = it
                stateName.value = it.name
                statePrice.value = it.price
                stateIcon.value = it.imageUrl
            }
        }
    }

    fun getTime(lifecycle: Lifecycle) = iRepo.getTimeValue().flowWithLifecycle(lifecycle)

    fun getTemperature(lifecycle: Lifecycle) = iRepo.getTemperatureValue().flowWithLifecycle(lifecycle)

    fun getStateCoffee() = stateCoffee

    fun getStateName() = stateName

    fun setName(string: String) {
        stateName.value = string
    }

    fun getStatePrice() = statePrice

    fun setPrice(string: String) {
        if (string.isEmpty()) statePrice.value = EMPTY
        else if (string.matches(regexPrice)) {
            statePrice.value = string
        }
    }

    fun checkPrice(boolean: Boolean) {
        if (boolean) statePrice.value = EMPTY
        else stateCoffee.value?.price?.let {
            if (it == EMPTY) statePrice.value = ZERO
            else statePrice.value = it
        }
    }

    fun getStateIcon() = stateIcon

    fun setImage(string: String) {
        stateIcon.value = string
    }

    fun save() {
        viewModelScope.launch {
            stateCoffee.value?.let {
                iRepo.save(coffee = it.copy(name = stateName.value,
                    price = if (statePrice.value.isEmpty() || statePrice.value.matches(regexEmpty)) EMPTY
                        else BigDecimal(statePrice.value).toString(),
                    imageUrl = stateIcon.value))
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        iRepo.stopTime()
        iRepo.stopTemperature()
    }
}