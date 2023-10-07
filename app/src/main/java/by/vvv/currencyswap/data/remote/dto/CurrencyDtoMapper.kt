package by.vvv.currencyswap.data.remote.dto

import by.vvv.currencyswap.domain.model.CurrencyRate

fun CurrencyDto.toCurrencyRates():List<CurrencyRate>{

    val currencyData = this.currencyDataDto

    return listOf(
        CurrencyRate("INR", "Indian Rupee", currencyData.iNR),
        CurrencyRate("EUR", "Euro", currencyData.eUR),
        CurrencyRate("USD", "US Dollar", currencyData.uSD),
        CurrencyRate("JPY", "Japanese Yen", currencyData.jPY),
        CurrencyRate("BGN", "Bulgarian Lev", currencyData.bGN),
        CurrencyRate("CZK", "Czech Republic Koruna", currencyData.cZK),
        CurrencyRate("DKK", "Danish Krone", currencyData.dKK),
        CurrencyRate("GBP", "British Pound Sterling", currencyData.gBP),
        CurrencyRate("HUF", "Hungarian Forint", currencyData.hUF),
        CurrencyRate("PLN", "Polish Zloty", currencyData.pLN),
        CurrencyRate("RON", "Romanian Leu", currencyData.rON),
        CurrencyRate("SEK", "Swedish Krona", currencyData.sEK),
        CurrencyRate("CHF", "Swiss Franc", currencyData.cHF),
        CurrencyRate("ISK", "Icelandic Króna", currencyData.iSK),
        CurrencyRate("NOK", "Norwegian Krone", currencyData.nOK),
        CurrencyRate("HRK", "Croatian Kuna", currencyData.hRK),
        CurrencyRate("RUB", "Russian Ruble", currencyData.rUB),
        CurrencyRate("TRY", "Turkish Lira", currencyData.tRY),
        CurrencyRate("AUD", "Australian Dollar", currencyData.aUD),
        CurrencyRate("BRL", "Brazilian Real", currencyData.bRL),
        CurrencyRate("CAD", "Canadian Dollar", currencyData.cAD),
        CurrencyRate("CNY", "Chinese Yuan", currencyData.cNY),
        CurrencyRate("HKD", "Hong Kong Dollar", currencyData.hKD),
        CurrencyRate("IDR", "Indonesian Rupiah", currencyData.iDR),
        CurrencyRate("ILS", "Israeli New Sheqel", currencyData.iLS),
        CurrencyRate("KRW", "South Korean Won", currencyData.kRW),
        CurrencyRate("MXN", "Mexican Peso", currencyData.mXN),
        CurrencyRate("MYR", "Malaysian Ringgit", currencyData.mYR),
        CurrencyRate("NZD", "New Zealand Dollar", currencyData.nZD),
        CurrencyRate("PHP", "Philippine Peso", currencyData.pHP),
        CurrencyRate("SGD", "Singapore Dollar", currencyData.sGD),
        CurrencyRate("THB", "Thai Baht", currencyData.tHB),
        CurrencyRate("ZAR", "South African Rand", currencyData.zAR)
    )
}