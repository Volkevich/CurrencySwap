package by.vvv.currencyswap.presentation.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import by.vvv.currencyswap.R
import by.vvv.currencyswap.constant.Constant.EUR
import by.vvv.currencyswap.constant.Constant.RUS
import by.vvv.currencyswap.constant.Constant.USD
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
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
    val backgroundPainter = painterResource(id = R.drawable.fon1)

    val passUsd = getUSDRatePass()
    val passEur = getEURRatePass()
    val passRus = getRusRatePass()
    val sellUsd = getUSDRateSell()
    val sellEur = getEurRateSell()
    val sellRus = getRusRateSell()
    GifBackground(
        modifier = Modifier.fillMaxSize(),
        painter = backgroundPainter
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            text = todayDate,
            fontFamily = FontFamily.Serif,
            fontSize = 45.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground

        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            text = "Лучшие курсы банков г. Минска",
            fontFamily = FontFamily.Serif,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground

        )

    }

        Spacer(modifier = Modifier
            .width(10.dp)
            .height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .padding(35.dp)
            ) {
                passUsd?.let {
                    CurrencyItem(
                        icon = getFlagResource("USD"),
                        currency = "USD",
                        sellingRate = it,
                        buyingRate = sellUsd.toString()
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(35.dp)
            ) {
                passEur?.let {
                    CurrencyItem(
                        icon = getFlagResource("EUR"),
                        currency = "EUR",
                        sellingRate = it,
                        buyingRate = sellEur.toString()
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(35.dp)
            ) {
                if (passRus != null) {
                    CurrencyItem(
                        icon = getFlagResource("RUS"),
                        currency = "RUS",
                        sellingRate = passRus,
                        buyingRate = sellRus.toString()
                    )
                }
            }

    }



}
@Composable
fun GifBackground(
    modifier: Modifier = Modifier,
    painter: Painter
) {
    Box(
        modifier = modifier
            .background(color = Color.Transparent) // Устанавливаем фон как прозрачный
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun CurrencyItem(icon: Int, currency: String, sellingRate: String, buyingRate: String) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier
            .width(20.dp)
            .height(14.dp))
        Column(
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = currency,
                style = MaterialTheme.typography.body1,
                fontSize = TextUnit.Unspecified
            )
        }
        Spacer(modifier = Modifier
            .width(20.dp)
            .height(14.dp))
        Column(
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Сдать\n \n $sellingRate",
                style = MaterialTheme.typography.body1,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier
            .width(20.dp)
            .height(14.dp))
        Column(
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Купить\n \n $buyingRate",
                style = MaterialTheme.typography.body1,
                color = Color.Black
            )
        }

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
fun getUSDRatePass(): String? {
    var doc: Document? by remember { mutableStateOf(null as Document?) }

    DisposableEffect(Unit) {
        val job = GlobalScope.launch(Dispatchers.IO) {
            try {
                doc = Jsoup.connect("https://myfin.by/currency/minsk").get()
            } catch (e: IOException) {
                Log.e("MyTag", "Error fetching document: $e")
            }
        }

        onDispose {
            job.cancel()
        }
    }

    return doc?.let {
        val elements =
            it.getElementsByClass("course-brief-info course-brief-info--best-courses course-brief-info--desk")
        val table = elements.firstOrNull()
        val tableUSD = table?.children()?.getOrNull(1)
        val tableUSD2 = tableUSD?.children()?.getOrNull(1)
        tableUSD2?.children()?.getOrNull(0)?.text()
    }
}

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun getEURRatePass(): String? {
    var doc: Document? by remember { mutableStateOf(null as Document?) }

    DisposableEffect(Unit) {
        val job = GlobalScope.launch(Dispatchers.IO) {
            try {
                doc = Jsoup.connect("https://myfin.by/currency/minsk").get()
            } catch (e: IOException) {
                Log.e("MyTag", "Error fetching document: $e")
            }
        }

        onDispose {
            job.cancel()
        }
    }

    return doc?.let {
        val elements =
            it.getElementsByClass("course-brief-info course-brief-info--best-courses course-brief-info--desk")
        val table = elements.firstOrNull()
        val tableUSD = table?.children()?.getOrNull(1)
        val tableUSD2 = tableUSD?.children()?.getOrNull(3)
        tableUSD2?.children()?.getOrNull(0)?.text()
    }
}

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun getRusRatePass(): String? {
    var doc: Document? by remember { mutableStateOf(null as Document?) }

    DisposableEffect(Unit) {
        val job = GlobalScope.launch(Dispatchers.IO) {
            try {
                doc = Jsoup.connect("https://myfin.by/currency/minsk").get()
            } catch (e: IOException) {
                Log.e("MyTag", "Error fetching document: $e")
            }
        }

        onDispose {
            job.cancel()
        }
    }

    return doc?.let {
        val elements =
            it.getElementsByClass("course-brief-info course-brief-info--best-courses course-brief-info--desk")
        val table = elements.firstOrNull()
        val tableUSD = table?.children()?.getOrNull(1)
        val tableUSD2 = tableUSD?.children()?.getOrNull(5)
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

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationScreen() {
    val locationViewModel: LocationViewModel = viewModel()
    val locationPermissionState =
        rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        if (locationPermissionState.hasPermission) {
            if (locationViewModel.city.isNotEmpty()) {
                Text("Город: ${locationViewModel.city}")
            } else {
                Text("Местоположение не доступно.")
            }
        } else {
            Text("Разрешение на местоположение не предоставлено.")
        }
    }
}

class LocationViewModel : ViewModel() {
    var city: String by mutableStateOf("Москва")
}

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun getUSDRateSell(): String? {
    var doc: Document? by remember { mutableStateOf(null as Document?) }

    DisposableEffect(Unit) {
        val job = GlobalScope.launch(Dispatchers.IO) {
            try {
                doc = Jsoup.connect("https://myfin.by/currency/minsk").get()
            } catch (e: IOException) {
                Log.e("MyTag", "Error fetching document: $e")
            }
        }
        onDispose {
            job.cancel()
        }
    }
    return doc?.let {
        val elements =
            it.getElementsByClass("course-brief-info__b")

        elements?.getOrNull(4)?.text()
    }
}

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun getEurRateSell(): String? {
    var doc: Document? by remember { mutableStateOf(null as Document?) }

    DisposableEffect(Unit) {
        val job = GlobalScope.launch(Dispatchers.IO) {
            try {
                doc = Jsoup.connect("https://myfin.by/currency/minsk").get()
            } catch (e: IOException) {
                Log.e("MyTag", "Error fetching document: $e")
            }
        }

        onDispose {
            job.cancel()
        }
    }

    return doc?.let {
        val elements =
            it.getElementsByClass("course-brief-info__b")

        elements?.getOrNull(7)?.text()
    }
}

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun getRusRateSell(): String? {
    var doc: Document? by remember { mutableStateOf(null as Document?) }

    DisposableEffect(Unit) {
        val job = GlobalScope.launch(Dispatchers.IO) {
            try {
                doc = Jsoup.connect("https://myfin.by/currency/minsk").get()
            } catch (_: IOException) {
            }
        }

        onDispose {
            job.cancel()
        }
    }

    return doc?.let {
        val elements =
            it.getElementsByClass("course-brief-info__b")

        elements?.getOrNull(10)?.text()
    }
}


@Preview
@Composable
fun Prev() {
    ExchangeRates()
}





