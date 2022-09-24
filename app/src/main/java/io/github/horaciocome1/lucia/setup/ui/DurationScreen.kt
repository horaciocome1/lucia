package io.github.horaciocome1.lucia.setup.ui

import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.horaciocome1.lucia.destinations.LevelScreenDestination
import io.github.horaciocome1.lucia.ui.component.Seekbar
import io.github.horaciocome1.lucia.ui.theme.BrightGreen
import io.github.horaciocome1.lucia.ui.theme.Grey
import io.github.horaciocome1.lucia.ui.theme.LuciaTheme
import kotlin.math.roundToLong

@Destination
@Composable
fun DurationScreen(
    navigator: DestinationsNavigator?,
    minimumDuration: Long = 60L,
    maximumDuration: Long = 240L,
    position: Long = 90L
) {
    val seekbarMaximum = remember { mutableStateOf(maximumDuration - minimumDuration) }
    val seekbarPosition = remember { mutableStateOf(0L) }
    val contentWidth = 300.dp
    LaunchedEffect(Unit) {
        animate(
            initialValue = 0f,
            targetValue = (maximumDuration - minimumDuration).toFloat(),
            animationSpec = tween(durationMillis = 400)
        ) { value, _ ->
            seekbarPosition.value = value.roundToLong()
        }
        animate(
            initialValue = (maximumDuration - minimumDuration).toFloat(),
            targetValue = (position - minimumDuration).toFloat(),
            animationSpec = tween(durationMillis = 600)
        ) { value, _ ->
            seekbarPosition.value = value.roundToLong()
        }
    }
    val dragging = remember { mutableStateOf(false) }
    val animatedDurationFontWeight = animateIntAsState(
        targetValue = if (dragging.value) {
            FontWeight.Black.weight
        } else {
            FontWeight.Normal.weight
        }
    )
    SetupScaffold(
        title = "Pick duration",
        actionButtonText = "continue",
        firstStep = true,
        stepComplete = true,
        onNavigateUpClick = { navigator?.navigateUp() },
        onContinueButtonClick = { navigator?.navigate(LevelScreenDestination()) }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = buildAnnotatedString {
                    append((seekbarPosition.value + minimumDuration).toString())
                    append("\n")
                    withStyle(
                        MaterialTheme.typography.headlineSmall.toSpanStyle().copy(
                            fontWeight = FontWeight.ExtraLight
                        )
                    ) {
                        append("seconds")
                    }
                },
                style = MaterialTheme.typography.displayLarge,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight(animatedDurationFontWeight.value)
            )
            Spacer(modifier = Modifier.widthIn(contentWidth))
            RegionSeekBar(
                max = seekbarMaximum.value,
                currentProgress = seekbarPosition.value,
                labelMinimum = minimumDuration.toString(),
                labelMaximum = maximumDuration.toString(),
                width = contentWidth,
                onNewProgress = { seekbarPosition.value = it },
                onDragStart = { dragging.value = true },
                onDragEnd = { dragging.value = false }
            )
        }
    }
}

@Composable
fun RegionSeekBar(
    max: Long,
    currentProgress: Long,
    labelMinimum: String,
    labelMaximum: String,
    onNewProgress: (progress: Long) -> Unit,
    onDragStart: (progress: Long) -> Unit = {},
    onDragEnd: (progress: Long) -> Unit,
    width: Dp = 300.dp
) {
    Column(modifier = Modifier.width(width)) {
        Seekbar(
            modifier = Modifier,
            duration = max,
            position = currentProgress,
            onNewProgress = onNewProgress,
            onDragStart = onDragStart,
            onDragEnd = onDragEnd,
            sliderColor = Color.White,
            backgroundColor = Grey,
            progressColor = BrightGreen,
            sliderHeight = 56.dp,
            sliderWidth = 56.dp,
            radius = 56.dp
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = labelMinimum,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Black
            )
            Text(
                text = labelMaximum,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Black
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DurationScreenPreview() {
    LuciaTheme {
        DurationScreen(navigator = null)
    }
}

@Preview(showBackground = true)
@Composable
fun RegionSeekBarPreview() {
    LuciaTheme {
        RegionSeekBar(
            max = 150L,
            currentProgress = 30L,
            labelMinimum = "60",
            labelMaximum = "240",
            onNewProgress = { },
            onDragStart = {},
            onDragEnd = {}
        )
    }
}