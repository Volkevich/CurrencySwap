package by.vvv.currencyswap.data.local.entity

import by.vvv.currencyswap.domain.model.CurrencyRate

fun CurrencyRateEntity.toCurrencyRate(): CurrencyRate {

    return CurrencyRate(
        code = code,
        name = name,
        rate = rate
    )
}
fun CurrencyRate.toCurrencyRateEntity(): CurrencyRateEntity {

    return CurrencyRateEntity(
        code = code,
        name = name,
        rate = rate
    )
}