package io.github.horaciocome1.lucia.setup.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.horaciocome1.lucia.destinations.TopicsScreenDestination
import io.github.horaciocome1.lucia.setup.model.Level
import io.github.horaciocome1.lucia.ui.component.RadioRow
import io.github.horaciocome1.lucia.ui.theme.Brown70
import io.github.horaciocome1.lucia.ui.theme.Brown80
import io.github.horaciocome1.lucia.ui.theme.Green70
import io.github.horaciocome1.lucia.ui.theme.Green80
import io.github.horaciocome1.lucia.ui.theme.LuciaTheme
import io.github.horaciocome1.lucia.ui.theme.Yellow70
import io.github.horaciocome1.lucia.ui.theme.Yellow80
import kotlinx.coroutines.delay

@Destination
@Composable
fun LevelScreen(
    navigator: DestinationsNavigator?,
    duration: Long
) {
    val selectedLevel = remember { mutableStateOf<Level?>(null) }
    val runningInitialAnimation = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        runningInitialAnimation.value = true
        val interval = 300L
        levels.forEach { level ->
            delay(interval)
            selectedLevel.value = level
        }
        delay(interval)
        selectedLevel.value = null
        levels.reversed().forEach { level ->
            delay(interval)
            selectedLevel.value = level
        }
        delay(interval)
        selectedLevel.value = null
        runningInitialAnimation.value = false
    }
    SetupScaffold(
        title = "Choose level",
        actionButtonText = "Continue",
        firstStep = false,
        stepComplete = selectedLevel.value != null && !runningInitialAnimation.value,
        onNavigateUpClick = { navigator?.navigateUp() },
        onContinueButtonClick = {
            val selected = selectedLevel.value
            if (selected != null) {
                navigator?.navigate(
                    direction = TopicsScreenDestination(
                        level = selected,
                        duration = duration
                    )
                )
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            levels.forEach { level ->
                RadioRow(
                    modifier = Modifier
                        .width(300.dp)
                        .padding(vertical = 4.dp),
                    backgroundColor = Color(level.color),
                    backgroundColorSelected = Color(level.colorSelected),
                    onClick = { selectedLevel.value = level },
                    selected = level.name == selectedLevel.value?.name,
                    text = level.name
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewLevelScreen() {
    LuciaTheme {
        LevelScreen(
            navigator = null,
            duration = 90L
        )
    }
}

private val levels = setOf(
    Level(
        name = "Júnior",
        color = Green70.value,
        colorSelected = Green80.value
    ),
    Level(
        name = "Soft",
        color = Yellow70.value,
        colorSelected = Yellow80.value
    ),
    Level(
        name = "Madoda",
        color = Brown70.value,
        colorSelected = Brown80.value
    )
)