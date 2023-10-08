package by.vvv.currencyswap.presentation.navGraph.botton.button_navigations

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigations(navController: NavController) {

    val listItemsScreen = listOf(
        BottomItems.MyScreen,
        BottomItems.HistoryScreen,
        BottomItems.Statistic
    )

    BottomNavigation(backgroundColor = Color.Transparent, modifier = Modifier.padding(5.dp)) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route
        listItemsScreen.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.route,
                onClick = {
                          navController.navigate(item.route)
                          },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconId),
                        contentDescription = "Icon", modifier = Modifier.size(25.dp)
                    )
                },
                label = { Text(text = item.title, fontSize = 12.sp, color = Color.White) },
                selectedContentColor = Color.Blue,
                unselectedContentColor = Color.Gray,
                modifier = Modifier.padding(5.dp)
            )
        }


    }
}