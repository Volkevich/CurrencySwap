package by.vvv.currencyswap

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.Surface
import androidx.hilt.navigation.compose.hiltViewModel
import by.vvv.currencyswap.presentation.main_screen.MainScreen
import by.vvv.currencyswap.presentation.main_screen.MainScreenViewModel
import by.vvv.currencyswap.presentation.theme.CurrencySwapTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencySwapTheme {
                val viewModel:MainScreenViewModel = hiltViewModel()
                Surface {
                    MainScreen(
                        state = viewModel.state ,
                        onEvent = viewModel::onEvent
                    )
                }

            }
        }
    }
}