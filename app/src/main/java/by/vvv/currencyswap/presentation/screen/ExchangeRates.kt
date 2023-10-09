package by.vvv.currencyswap.presentation.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import by.vvv.currencyswap.R
import by.vvv.currencyswap.constant.Constant.EUR
import by.vvv.currencyswap.constant.Constant.RUS
import by.vvv.currencyswap.constant.Constant.USD
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ExchangeRates() {
    val todayDate by rememberUpdatedState(getTodayDate())

    val rangeUSD = getUSDRate()
    val rangeEUR = getEURRate()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = todayDate,
            style = MaterialTheme.typography.h6,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            color = MaterialTheme.colors.primary
        )

        rangeUSD?.let {
            CurrencyItem(icon = getFlagResource("USD"), currency = "USD", rate = it)
        }
        rangeEUR?.let {
            CurrencyItem(icon = getFlagResource("EUR"), currency = "EUR", rate = it)
        }


    }
}

@Composable
fun CurrencyItem(icon: Int, currency: String, rate: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Text(text = currency, style = MaterialTheme.typography.body1)
        }
        Text(
            text = "Rate: $rate",
            style = MaterialTheme.typography.body2,
            color = Color.Gray
        )
    }
}

@Composable
fun getTodayDate(): String {
    LocalContext.current
    val timeZone = TimeZone.getDefault()
    val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    sdf.timeZone = timeZone
    return sdf.format(Calendar.getInstance(timeZone).time)
}



@OptIn(DelicateCoroutinesApi::class)
@Composable
fun getUSDRate(): String? {
    var doc: Document? by remember { mutableStateOf(null as Document?) }

    DisposableEffect(Unit) {
        val job = GlobalScope.launch(Dispatchers.IO) {
            try {
                doc = Jsoup.connect("https://myfin.by/currency/minsk").get()
                val elements = doc?.getElementsByClass("course-brief-info course-brief-info--best-courses course-brief-info--desk")
                val table = elements?.get(0)
                val tableUSD = table?.children()?.get(1)
                val tableUSD2 = tableUSD?.children()?.get(1)
                val rangeUSD = tableUSD2?.children()?.get(0)?.text()

                Log.d("MyTag", "Info: $rangeUSD")
            } catch (e: IOException) {
                Log.e("MyTag", "Error fetching document: $e")
            }
        }

        onDispose {
            job.cancel()
        }
    }

    return doc?.let {
        val elements = it.getElementsByClass("course-brief-info course-brief-info--best-courses course-brief-info--desk")
        val table = elements.firstOrNull()
        val tableUSD = table?.children()?.getOrNull(1)
        val tableUSD2 = tableUSD?.children()?.getOrNull(1)
        tableUSD2?.children()?.getOrNull(0)?.text()
    }
}
@OptIn(DelicateCoroutinesApi::class)
@Composable
fun getEURRate(): String? {
    var doc: Document? by remember { mutableStateOf(null as Document?) }

    DisposableEffect(Unit) {
        val job = GlobalScope.launch(Dispatchers.IO) {
            try {
                doc = Jsoup.connect("https://myfin.by/currency/minsk").get()
                val elements = doc?.getElementsByClass("course-brief-info course-brief-info--best-courses course-brief-info--desk")
                val table = elements?.get(0)
                val tableUSD = table?.children()?.get(1)
                val tableUSD2 = tableUSD?.children()?.get(3)
                val rangeUSD = tableUSD2?.children()?.get(0)?.text()

                Log.d("MyTag", "Info: $rangeUSD")
            } catch (e: IOException) {
                Log.e("MyTag", "Error fetching document: $e")
            }
        }

        onDispose {
            job.cancel()
        }
    }

    return doc?.let {
        val elements = it.getElementsByClass("course-brief-info course-brief-info--best-courses course-brief-info--desk")
        val table = elements.firstOrNull()
        val tableUSD = table?.children()?.getOrNull(1)
        val tableUSD2 = tableUSD?.children()?.getOrNull(3)
        tableUSD2?.children()?.getOrNull(0)?.text()
    }
}


@Composable
fun getFlagResource(currencyCode: String): Int {
    return when (currencyCode) {
        USD -> R.drawable.usa
        EUR -> R.drawable.eur
        RUS -> R.drawable.russia_flag_icon
        else -> R.drawable.myscreen
    }
}




