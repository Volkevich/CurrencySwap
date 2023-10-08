package by.vvv.currencyswap.presentation.screen.main_screen

import android.annotation.SuppressLint
import android.content.res.Resources
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph
import androidx.navigation.compose.rememberNavController
import by.vvv.currencyswap.presentation.navGraph.NavGraph
import by.vvv.currencyswap.presentation.navGraph.botton.button_navigations.BottomNavigations

@RequiresApi(34)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyScreen(
    resources: Resources,
    packageName: String
) {
    val navController = rememberNavController()
    Scaffold(bottomBar = { BottomNavigations(navController = navController) }) {
        NavGraph(
            navHostController = navController,
            resources = resources,
            packageName = packageName
        )
    }
}