package reschikov.test.test_uvenco.di

import dagger.assisted.AssistedFactory
import reschikov.test.test_uvenco.ui.item_screen.DetailCoffeeViewModel

@AssistedFactory
interface IFactoryDetailCoffeeViewModel {

    fun create(id: Long): DetailCoffeeViewModel
}