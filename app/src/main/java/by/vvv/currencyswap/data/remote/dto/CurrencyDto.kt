package by.vvv.currencyswap.data.remote.dto


import com.google.gson.annotations.SerializedName

data class CurrencyDto(
    @SerializedName("data")
    val currencyDataDto: CurrencyDataDto
)