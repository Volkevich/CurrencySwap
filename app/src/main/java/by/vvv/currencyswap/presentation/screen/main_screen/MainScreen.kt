package by.vvv.currencyswap.presentation.screen.main_screen

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.net.Uri
import android.os.Build
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import by.vvv.currencyswap.BottomSheetContent
import by.vvv.currencyswap.R
import by.vvv.currencyswap.presentation.navGraph.NavGraph
import by.vvv.currencyswap.presentation.navGraph.botton.button_navigations.BottomNavigations
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.StyledPlayerView
import kotlinx.coroutines.launch

@RequiresApi(34)
@SuppressLint("OpaqueUnitKey", "UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    state: MainScreenState,
    onEvent: (MainScreenEvent) -> Unit, videoUri: Uri, resources: Resources,
    packageName: String, navController: NavController
) {

    val keys = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", ".", "0", "C")
    var selectedScreen by remember { mutableStateOf(0) }

    val context = LocalContext.current
    val exoPlayer = remember { context.buildExoPlayer(videoUri) }
    val navController = rememberNavController()


    DisposableEffect(
        AndroidView(
            factory = { it.buildPlayerView(exoPlayer) },
            modifier = Modifier.fillMaxSize()
        )
    ) {
        onDispose {
            exoPlayer.release()
        }
    }

    LaunchedEffect(key1 = state.error) {
        if (state.error != null) {
            Toast.makeText(context, state.error, Toast.LENGTH_LONG).show()
        }
    }

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    var shouldBottomSheetShow by remember { mutableStateOf(false) }

    if (shouldBottomSheetShow) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = { shouldBottomSheetShow = false },
            dragHandle = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BottomSheetDefaults.DragHandle()
                    Text(
                        text = "Select Currency",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Divider()
                }
            },
            content = {
                BottomSheetContent(
                    onItemClicked = { currencyCode ->
                        onEvent(MainScreenEvent.ButtonSheetItemClicked(currencyCode))
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) shouldBottomSheetShow = false
                        }
                    },
                    currenciesList = state.currencyRates.values.toList()
                )
            }
        )
    }

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
            text = "Currency Swap",
            fontFamily = FontFamily.Serif,
            fontSize = 45.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.outline
        )

        Box(
            contentAlignment = Alignment.CenterStart
        ) {

            Column {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 4.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        CurrencyRow(
                            modifier = Modifier.fillMaxWidth(),
                            currencyCode = state.fromCurrencyCode,
                            currencyName = state.currencyRates[state.fromCurrencyCode]?.name ?: "",
                            onDropDownIconClicked = {
                                shouldBottomSheetShow = true
                                onEvent(MainScreenEvent.FromCurrencySelect)
                            }
                        )
                        Text(
                            text = state.fromCurrencyValue,
                            fontSize = 40.sp,
                            modifier = Modifier.clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null,
                                onClick = { onEvent(MainScreenEvent.FromCurrencySelect) }
                            ),
                            color = if (state.selection == SelectionState.FROM) {
                                MaterialTheme.colorScheme.primary
                            } else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 4.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = state.toCurrencyValue,
                            fontSize = 40.sp,
                            modifier = Modifier.clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null,
                                onClick = { onEvent(MainScreenEvent.ToCurrencySelect) }
                            ),
                            color = if (state.selection == SelectionState.TO) {
                                MaterialTheme.colorScheme.primary
                            } else MaterialTheme.colorScheme.onSurface
                        )
                        CurrencyRow(
                            modifier = Modifier.fillMaxWidth(),
                            currencyCode = state.toCurrencyCode,
                            currencyName = state.currencyRates[state.toCurrencyCode]?.name ?: "",
                            onDropDownIconClicked = {
                                shouldBottomSheetShow = true
                                onEvent(MainScreenEvent.ToCurrencySelect)
                            }
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .padding(start = 40.dp)
                    .clip(CircleShape)
                    .clickable { onEvent(MainScreenEvent.SwapIconClicked) }
                    .background(color = MaterialTheme.colorScheme.background)
            ) {
                Icon(
                    painter = painterResource(R.drawable.sync),
                    contentDescription = "Swap Currency",
                    modifier = Modifier
                        .padding(8.dp)
                        .size(25.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        LazyVerticalGrid(
            modifier = Modifier.padding(horizontal = 25.dp),
            columns = GridCells.Adaptive(100.dp)
        ) {
            items(keys) { key ->
                KeyboardButton(
                    modifier = Modifier.aspectRatio(1f),
                    key = key,
                    backgroundColor = if (key == "C") MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.surfaceVariant,
                    onClick = {
                        onEvent(MainScreenEvent.NumberButtonClicked(key))
                    }
                )
            }
        }


    }
}

@Composable
fun CurrencyRow(
    modifier: Modifier = Modifier,
    currencyCode: String,
    currencyName: String,
    onDropDownIconClicked: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = currencyCode, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        IconButton(onClick = onDropDownIconClicked) {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Open Button down"
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(text = currencyName, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }

}

@Composable
fun KeyboardButton(
    modifier: Modifier = Modifier,
    key: String,
    backgroundColor: Color,
    onClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .clip(CircleShape)
            .background(color = backgroundColor)
            .clickable { onClick(key) },
        contentAlignment = Alignment.Center
    ) {
        Text(text = key, fontSize = 32.sp)
    }

}

private fun Context.buildExoPlayer(uri: Uri) =
    ExoPlayer.Builder(this).build().apply {
        setMediaItem(MediaItem.fromUri(uri))
        repeatMode = Player.REPEAT_MODE_ALL
        playWhenReady = true
        prepare()
    }

private fun Context.buildPlayerView(exoPlayer: ExoPlayer) =
    StyledPlayerView(this).apply {
        player = exoPlayer
        layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        useController = false
        resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
    }


@RequiresApi(Build.VERSION_CODES.Q)
@Preview
@Composable
fun getScreen() {
    //MainScreen()
}
