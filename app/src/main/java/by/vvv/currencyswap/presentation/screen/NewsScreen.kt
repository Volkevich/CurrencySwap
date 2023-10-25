package by.vvv.currencyswap.presentation.screen


import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import java.io.IOException


@Composable
fun NewsScreen() {
    val todayDate by rememberUpdatedState(getTodayDate())
    val newsList = remember { mutableStateListOf<NewsItem>() }
    val lazyListState = rememberLazyListState()
    val openUrlLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            Log.d("MyTag", "Ссылка успешно открыта")
        } else {

            Log.e("MyTag", "Не удалось открыть ссылку")
        }
    }

    DisposableEffect(Unit) {
        val job = GlobalScope.launch(Dispatchers.IO) {
            try {
                val doc = Jsoup.connect("https://myfin.by/stati").get()
                val newsElements = doc.getElementsByClass("news-item__link")

                val newsItems = newsElements.map { element ->
                    val link = element.attr("href")
                    val fullUrl = "https://myfin.by$link"

                    val titleElement = element.parent().getElementsByClass("news-item__title").first()
                    val title = titleElement.text()

                    val imageElement = element.parent().getElementsByClass("news-item__image").first()
                    val imageUrl = imageElement.attr("src")

                    NewsItem(title, fullUrl, imageUrl)
                }
                newsList.addAll(newsItems)
            } catch (e: IOException) {
                Log.e("MyTag", "Ошибка в документе: $e")
            }
        }

        onDispose {
            job.cancel()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            text = todayDate,
            fontFamily = FontFamily.Serif,
            fontSize = 35.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            text = "Новости",
            fontFamily = FontFamily.Serif,
            fontSize = 35.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        LazyColumn(
            state = lazyListState,
            modifier = Modifier.fillMaxSize()
        ) {
            items(newsList) { newsItem ->
                NewsListItem(newsItem, openUrlLauncher)
            }
        }
    }
}

@Composable
fun NewsListItem(newsItem: NewsItem, openUrlLauncher: ActivityResultLauncher<Intent>) {
    val context = LocalContext.current

    val openNewsLink: () -> Unit = {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(newsItem.link))
        try {
            openUrlLauncher.launch(intent)
        } catch (e: ActivityNotFoundException) {
            Log.e("MyTag", "Ссылка на статью: ${newsItem.link}")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { openNewsLink() }
            .padding(8.dp)
    ) {
        Text(
            text = newsItem.title,
            fontFamily = FontFamily.Serif,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        NewsListItemIcon(newsItem)
    }
}

@Composable
fun NewsListItemIcon(newsItem: NewsItem) {
    Icon(
        imageVector = Icons.Default.Info,
        tint = MaterialTheme.colorScheme.onPrimary,
        contentDescription = "Иконка информации"
    )
}

data class NewsItem(val title: String, val link: String, val imageUrl: String)








