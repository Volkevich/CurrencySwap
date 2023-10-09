package by.vvv.currencyswap.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.vvv.currencyswap.R
import kotlinx.coroutines.delay
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

@Composable
fun ExchangeRates() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            text = "Курсы Валют",
            fontFamily = FontFamily.Serif,
            fontSize = 45.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.outline
        )
        val currencyRates: Map<String, Double> = mapOf(
            "USD" to 1.23,
            "EUR" to 0.89,
            "BYN" to 1.12,

        )
        CurrencyTicker(currencyRates)
    }
}

@OptIn(ExperimentalTime::class)
@Composable
fun CurrencyTicker(currencyRates: Map<String, Double>, interval: Duration = 5.seconds) {
    var currentIndex by remember { mutableStateOf(0) }
    var currentText by remember { mutableStateOf(formatCurrencyText(currencyRates)) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(interval.inWholeMilliseconds)
            currentIndex = (currentIndex + 1) % currentText.length
            currentText = formatCurrencyText(currencyRates)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        currentText.split(", ").forEach { currencyInfo ->
            val (currencyCode, currencyValue) = currencyInfo.split(": ")
            CurrencyRow(currencyCode = currencyCode, currencyValue = currencyValue.toDouble())
        }
    }
}

@Composable
fun CurrencyRow(currencyCode: String, currencyValue: Double) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val flagResource = getFlagResource(currencyCode)

        Image(
            painter = painterResource(id = flagResource),
            contentDescription = "$currencyCode Flag",
            modifier = Modifier.size(50.dp)
        )
        Text(
            text = "$currencyCode: ${currencyValue.format(2)}",
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}


private fun formatCurrencyText(currencyRates: Map<String, Double>): String {
    return currencyRates.entries.joinToString { "${it.key}: ${it.value}" }
}

private fun Double.format(digits: Int) = "%.${digits}f".format(this)
@Composable
fun getFlagResource(currencyCode: String): Int {
    return when (currencyCode) {
        "USD" -> R.drawable.usa
        "EUR" -> R.drawable.eur
        "BYN" -> R.drawable.byn

        else -> R.drawable.myscreen
    }
}
