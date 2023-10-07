package by.vvv.currencyswap.data.remote

import by.vvv.currencyswap.data.remote.dto.CurrencyDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {


    @GET("v1/latest")
    suspend fun getLatestRates(@Query("apikey") apiKey: String = API_KEY):CurrencyDto

    companion object {
        const val API_KEY = "fca_live_Ij9kbt2OqLu4bIJNWNsHOou9xhsohqDo0caqJVRX"
        const val BASE_URL = "https://api.freecurrencyapi.com/"
    }

}