package reschikov.test.test_uvenco.ui

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ActivityComponent
import reschikov.test.test_uvenco.di.IFactoryDetailCoffeeViewModel
import reschikov.test.test_uvenco.ui.item_screen.DetailCoffeeScreen
import reschikov.test.test_uvenco.ui.item_screen.DetailCoffeeViewModel
import reschikov.test.test_uvenco.ui.list_screen.CoffeesScreen
import reschikov.test.test_uvenco.ui.theme.Test_UvencoTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @EntryPoint
    @InstallIn(ActivityComponent::class)
    interface ViewModelFactoryProvider {
        fun takeFactoryDetailCoffeeViewModel() : IFactoryDetailCoffeeViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        setContent {
            val navController = rememberNavController()
            Test_UvencoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { pv ->
                    NavHost(navController = navController,
                        startDestination = Routes.ListCoffee.route,
                        modifier = Modifier.padding(pv)
                    ) {
                        composable(Routes.ListCoffee.route) {
                            CoffeesScreen { navController.navigate(Routes.ItemCoffee(it.toString()).route) }
                        }
                        composable(Routes.ItemCoffee("{id}").route, setLongArg("id")) {
                            it.getLongArg(name = "id")?.let { long ->
                                val viewModel = it.createViewModel(id = long, cls = DetailCoffeeViewModel::class.java)
                                DetailCoffeeScreen(viewModel = viewModel) { navController.popBackStack() }
                            }
                        }
                    }
                }
            }
        }
    }
}

sealed class Routes(val route: String) {

    data object ListCoffee : Routes("list_coffee")
    class ItemCoffee(id: String): Routes("item_coffee/$id")
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.createViewModel(id: Long, cls: Class<T>) : T {
    val factory = when(cls){
        DetailCoffeeViewModel::class.java -> {
            val iFactory = takeEntryPoint().takeFactoryDetailCoffeeViewModel()
            DetailCoffeeViewModel.provideFactory(iFactory, id)
        }
        else -> throw Exception("Неизвестный класс viewModel $cls")
    }
    return viewModel(
        viewModelStoreOwner = this,
        factory = factory
    )
}

@Composable
fun takeEntryPoint() = EntryPointAccessors.fromActivity(
    LocalContext.current as Activity,
    MainActivity.ViewModelFactoryProvider::class.java
)

private fun setLongArg(name: String): List<NamedNavArgument> {
    return mutableListOf<NamedNavArgument>().apply {
        add(navArgument(name) {
            type = NavType.LongType
        })
    }
}

fun NavBackStackEntry.getLongArg(name: String): Long? {
    return arguments?.getLong(name)
}
