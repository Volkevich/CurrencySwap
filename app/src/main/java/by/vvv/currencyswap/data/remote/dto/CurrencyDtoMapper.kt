package by.vvv.currencyswap.data.remote.dto

import by.vvv.currencyswap.domain.model.CurrencyRate

fun CurrencyDto.toCurrencyRates():List<CurrencyRate>{

    val currencyData = this.currencyDataDto

    return listOf(
        CurrencyRate("INR", "Индийская рупия", currencyData.iNR),
        CurrencyRate("EUR", "Евро", currencyData.eUR),
        CurrencyRate("USD", "Доллар США", currencyData.uSD),
        CurrencyRate("JPY", "Японская иена", currencyData.jPY),
        CurrencyRate("BGN", "Болгарский лев", currencyData.bGN),
        CurrencyRate("CZK", "Чешская крона", currencyData.cZK),
        CurrencyRate("DKK", "Датская крона", currencyData.dKK),
        CurrencyRate("GBP", "Британский фунт стерлингов", currencyData.gBP),
        CurrencyRate("HUF", "Венгерский форинт", currencyData.hUF),
        CurrencyRate("PLN", "Польский злотый", currencyData.pLN),
        CurrencyRate("RON", "Румынский лей", currencyData.rON),
        CurrencyRate("SEK", "Шведская крона", currencyData.sEK),
        CurrencyRate("CHF", "Швейцарский франк", currencyData.cHF),
        CurrencyRate("ISK", "Исландская крона", currencyData.iSK),
        CurrencyRate("NOK", "Норвежская крона", currencyData.nOK),
        CurrencyRate("HRK", "Хорватская куна", currencyData.hRK),
        CurrencyRate("RUB", "Российский рубль", currencyData.rUB),
        CurrencyRate("TRY", "Турецкая лира", currencyData.tRY),
        CurrencyRate("AUD", "Австралийский доллар", currencyData.aUD),
        CurrencyRate("BRL", "Бразильский реал", currencyData.bRL),
        CurrencyRate("CAD", "Канадский доллар", currencyData.cAD),
        CurrencyRate("CNY", "Китайский юань", currencyData.cNY),
        CurrencyRate("HKD", "Гонконгский доллар", currencyData.hKD),
        CurrencyRate("IDR", "Индонезийская рупия", currencyData.iDR),
        CurrencyRate("ILS", "Израильский новый шекель", currencyData.iLS),
        CurrencyRate("KRW", "Южнокорейская вона", currencyData.kRW),
        CurrencyRate("MXN", "Мексиканское песо", currencyData.mXN),
        CurrencyRate("MYR", "Малайзийский ринггит", currencyData.mYR),
        CurrencyRate("NZD", "Новозеландский доллар", currencyData.nZD),
        CurrencyRate("PHP", "Филиппинское песо", currencyData.pHP),
        CurrencyRate("SGD", "Сингапурский доллар", currencyData.sGD),
        CurrencyRate("THB", "Тайский бат", currencyData.tHB),
        CurrencyRate("ZAR", "Южноафриканский рэнд", currencyData.zAR)
    )
}