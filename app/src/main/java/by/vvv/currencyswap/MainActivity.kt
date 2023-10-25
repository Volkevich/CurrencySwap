package by.vvv.currencyswap

import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import by.vvv.currencyswap.presentation.navGraph.botton.button_navigations.BottomItems
import by.vvv.currencyswap.presentation.screen.ExchangeRates
import by.vvv.currencyswap.presentation.screen.NewsScreen
import by.vvv.currencyswap.presentation.screen.main_screen.MainScreenViewModel
import by.vvv.currencyswap.presentation.screen.main_screen.MyScreen
import by.vvv.currencyswap.presentation.theme.CurrencySwapTheme
import dagger.hilt.android.AndroidEntryPoint

@RequiresApi(34)
@AndroidEntryPoint
class MainActivity() : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencySwapTheme {
                val viewModel: MainScreenViewModel = hiltViewModel()
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "MyScreen"
                ) {
                    //TODO MAIN SCREEN
                    composable(BottomItems.MyScreen.route) {
                        MyScreen(resources = resources, packageName = packageName)
                    }
                    //TODO MAIN SCREEN
                    composable(BottomItems.NewsScreen.route) {

                        NewsScreen()
                    }
                    composable(BottomItems.ExchangeScreen.route) {
                        ExchangeRates()
                    }

                }

            }
        }
    }
    fun getVideoUri(resources: Resources, packageName: String): Uri {
        val rawId = resources.getIdentifier("swap", "raw", packageName)
        val videoUri = "android.resource://$packageName/$rawId"
        return Uri.parse(videoUri)
    }



}
