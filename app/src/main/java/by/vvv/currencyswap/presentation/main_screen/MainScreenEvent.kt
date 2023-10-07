package by.vvv.currencyswap.presentation.main_screen

sealed class MainScreenEvent{
    object FromCurrencySelect:MainScreenEvent()
    object ToCurrencySelect:MainScreenEvent()
    object SwapIconClicked:MainScreenEvent()
    data class ButtonSheetItemClicked(val value:String):MainScreenEvent()
    data class NumberButtonClicked(val value:String):MainScreenEvent()
}
