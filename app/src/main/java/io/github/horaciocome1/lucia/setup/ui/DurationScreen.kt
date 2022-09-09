package io.github.horaciocome1.lucia.setup.ui

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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
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

@Destination
@Composable
fun DurationScreen(
    navigator: DestinationsNavigator?,
    minimumDuration: Long = 60L,
    maximumDuration: Long = 240L,
    position: Long = 90L
) {
    val seekbarMaximum = remember { mutableStateOf(maximumDuration - minimumDuration) }
    val seekbarPosition = remember { mutableStateOf(position - minimumDuration) }
    val contentWidth = 300.dp
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
                        MaterialTheme.typography.displaySmall.toSpanStyle().copy(
                            fontWeight = FontWeight.ExtraLight
                        )
                    ) {
                        append("seconds")
                    }
                },
                style = MaterialTheme.typography.displayLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.widthIn(contentWidth))
            RegionSeekBar(
                max = seekbarMaximum,
                currentProgress = seekbarPosition,
                labelMinimum = minimumDuration.toString(),
                labelMaximum = maximumDuration.toString(),
                width = contentWidth
            )
        }
    }
}

@Composable
fun RegionSeekBar(
    max: State<Long>,
    currentProgress: MutableState<Long>,
    labelMinimum: String,
    labelMaximum: String,
    width: Dp = 300.dp
) {
    Column(modifier = Modifier.width(width)) {
        Seekbar(
            modifier = Modifier,
            duration = max,
            position = currentProgress,
            onNewProgress = { currentProgress.value = it },
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
            max = remember { mutableStateOf(150L) },
            currentProgress = remember { mutableStateOf(30L) },
            labelMinimum = "60",
            labelMaximum = "240"
        )
    }
}