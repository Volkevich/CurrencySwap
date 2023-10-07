package by.vvv.currencyswap.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import by.vvv.currencyswap.data.local.entity.CurrencyRateEntity

@Dao
interface CurrencyRateDao {
    @Upsert
    suspend fun upsertAll(currencyRates:List<CurrencyRateEntity>)
    @Query("SELECT * FROM currencyrateentity")
    suspend fun getAllCurrencyRates():List<CurrencyRateEntity>
}