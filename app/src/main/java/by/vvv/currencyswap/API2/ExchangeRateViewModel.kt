/*
package by.vvv.currencyswap.API2


import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class ExchangeRateViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {
    private val _exchangeRateState = MutableLiveData<ExchangeRateViewState>()

    val exchangeRateState: LiveData<ExchangeRateViewState> get() = _exchangeRateState

    @SuppressLint("CheckResult")
    fun fetchExchangeRate(id: Int) {
        apiService.getExchangeRate(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                _exchangeRateState.value = ExchangeRateViewState(
                    response.curAbbreviation,
                    response.curScale,
                    response.curName,
                    response.curOfficialRate
                )
            }, { error ->
                // Обработка ошибок
            })
    }
}*/
