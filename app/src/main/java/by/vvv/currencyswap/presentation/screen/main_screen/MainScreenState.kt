package by.vvv.currencyswap.presentation.screen.main_screen

import by.vvv.currencyswap.domain.model.CurrencyRate

data class MainScreenState(val fromCurrencyCode:String = "RUB",
                           val toCurrencyCode:String = "USD",
                           val fromCurrencyValue:String = "0.00",
                           val toCurrencyValue:String = "0.00",
                           val selection: SelectionState = SelectionState.FROM,
                           val currencyRates: Map<String,CurrencyRate> = emptyMap(),
                           val error:String? = null) {
}

enum class SelectionState{
    FROM,
    TO
}