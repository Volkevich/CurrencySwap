package by.vvv.currencyswap.data.repository

import by.vvv.currencyswap.R
import by.vvv.currencyswap.data.local.dao.CurrencyRateDao
import by.vvv.currencyswap.data.local.entity.toCurrencyRate
import by.vvv.currencyswap.data.local.entity.toCurrencyRateEntity
import by.vvv.currencyswap.data.remote.CurrencyApi
import by.vvv.currencyswap.data.remote.dto.toCurrencyRates
import by.vvv.currencyswap.domain.model.CurrencyRate
import by.vvv.currencyswap.domain.model.Resource
import by.vvv.currencyswap.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class CurrencyRepositoryImpl(
    private val api: CurrencyApi,
    private val dao: CurrencyRateDao
) : CurrencyRepository {


    override fun getCurrencyRatesList(): Flow<Resource<List<CurrencyRate>>> = flow {
        val localCurrencyRate = getLocalCurrencyRates()
        emit(Resource.Success(localCurrencyRate))

        try {
            val newrates = getRemoteCurrencyRate()
            updateLocalCurrencyRates(newrates)
            emit(Resource.Success(newrates))
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = R.string.errorInternet.toString(),
                    data = localCurrencyRate
                )
            )
        }catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Oops....${e.message} ",
                    data = localCurrencyRate
                )
            )
        }

    }

    private suspend fun getLocalCurrencyRates(): List<CurrencyRate> {
        return dao.getAllCurrencyRates().map { it.toCurrencyRate() }
    }

    private suspend fun getRemoteCurrencyRate(): List<CurrencyRate> {
        val response = api.getLatestRates()
        return response.toCurrencyRates()
    }

    private suspend fun updateLocalCurrencyRates(currencyRates: List<CurrencyRate>) {
        dao.upsertAll(currencyRates.map { it.toCurrencyRateEntity() })
    }

}
