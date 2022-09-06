package io.github.horaciocome1.lucia.setup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.horaciocome1.lucia.ui.component.RadioRow
import io.github.horaciocome1.lucia.ui.theme.Brown70
import io.github.horaciocome1.lucia.ui.theme.Brown80
import io.github.horaciocome1.lucia.ui.theme.Green70
import io.github.horaciocome1.lucia.ui.theme.Green80
import io.github.horaciocome1.lucia.ui.theme.LuciaTheme
import io.github.horaciocome1.lucia.ui.theme.Yellow70
import io.github.horaciocome1.lucia.ui.theme.Yellow80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LevelScreen(
    modifier: Modifier = Modifier,
    levels: Set<Level>
) {
    val selectedLevel = remember { mutableStateOf<Level?>(null) }
    SetupScreen(
        modifier = modifier,
        title = "Choose level",
        actionButtonText = "Continue",
        firstStep = false,
        stepComplete = selectedLevel.value != null
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            levels.forEach { level ->
                RadioRow(
                    modifier = Modifier
                        .width(300.dp)
                        .padding(vertical = 4.dp),
                    backgroundColor = level.color,
                    backgroundColorSelected = level.colorSelected,
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
            modifier = Modifier,
            levels = levels
        )
    }
}

private val levels = setOf(
    Level(
        name = "Júnior",
        color = Green70,
        colorSelected = Green80
    ),
    Level(
        name = "Soft",
        color = Yellow70,
        colorSelected = Yellow80
    ),
    Level(
        name = "Madoda",
        color = Brown70,
        colorSelected = Brown80
    )
)