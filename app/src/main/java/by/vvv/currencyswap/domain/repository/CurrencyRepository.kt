package by.vvv.currencyswap.domain.repository

import by.vvv.currencyswap.domain.model.CurrencyRate
import by.vvv.currencyswap.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    fun getCurrencyRatesList(): Flow<Resource<List<CurrencyRate>>>
}