package by.vvv.currencyswap.presentation.navGraph.botton.button_navigations


import by.vvv.currencyswap.R

sealed class BottomItems(val title:String, val iconId:Int, val route: String ){
    object MyScreen: BottomItems("Конвертер", R.drawable.currency_exchange_svgrepo_com, "MyScreen")
    object NewsScreen: BottomItems("Новости", R.drawable.news, "NEWS")
    object ExchangeScreen: BottomItems("Курсы", R.drawable.execoin, "ExchangeRates")
}
