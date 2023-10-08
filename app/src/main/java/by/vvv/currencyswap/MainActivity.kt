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
import by.vvv.currencyswap.presentation.screen.HistoryScreen
import by.vvv.currencyswap.presentation.screen.StatisticScreen
import by.vvv.currencyswap.presentation.screen.main_screen.MainScreen
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
                    composable("MyScreen") {
                        MyScreen(resources = resources, packageName = packageName)
                    }
                    composable("Statistic") {
                        StatisticScreen()
                    }
                    composable("History") {
                        HistoryScreen()
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
