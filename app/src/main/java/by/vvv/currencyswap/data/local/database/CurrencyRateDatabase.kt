package by.vvv.currencyswap.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import by.vvv.currencyswap.data.local.dao.CurrencyRateDao
import by.vvv.currencyswap.data.local.entity.CurrencyRateEntity

@Database(
    entities = [CurrencyRateEntity::class],
    version = 1
)
abstract class CurrencyRateDatabase :RoomDatabase() {
    abstract val currencyRateDao: CurrencyRateDao

}