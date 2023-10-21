package by.vvv.currencyswap.presentation.navGraph

import android.content.res.Resources
import android.net.Uri
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import by.vvv.currencyswap.presentation.screen.ExchangeRates
import by.vvv.currencyswap.presentation.screen.NewsScreen
import by.vvv.currencyswap.presentation.screen.main_screen.MainScreen
import by.vvv.currencyswap.presentation.screen.main_screen.MainScreenViewModel

@RequiresApi(34)
@Composable
fun NavGraph(
    navHostController: NavHostController,
    resources: Resources,
    packageName: String
) {
    val viewModel: MainScreenViewModel = hiltViewModel()
    NavHost(navController = navHostController, startDestination = "MyScreen") {
        composable("MyScreen") {
            MainScreen(
                state = viewModel.state,
                onEvent = viewModel::onEvent,
                videoUri = getVideoUri(resources, packageName),
                packageName = packageName,
                resources = resources,
                navController = navHostController
            )
        }

        composable("NEWS") {

            NewsScreen()
        }
        composable("ExchangeRates") {
            ExchangeRates()
        }
    }
}

fun getVideoUri(resources: Resources, packageName: String): Uri {
    val rawId = resources.getIdentifier("swap", "raw", packageName)
    val videoUri = "android.resource://$packageName/$rawId"
    return Uri.parse(videoUri)
}
