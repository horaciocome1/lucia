package io.github.horaciocome1.lucia.setup.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Shuffle
import androidx.compose.material3.IconButton
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
import io.github.horaciocome1.lucia.destinations.HomeScreenDestination
import io.github.horaciocome1.lucia.destinations.InviteScreenDestination
import io.github.horaciocome1.lucia.setup.model.Level
import io.github.horaciocome1.lucia.setup.model.Topic
import io.github.horaciocome1.lucia.setup.model.TopicsWrapper
import io.github.horaciocome1.lucia.ui.component.RadioBox
import io.github.horaciocome1.lucia.ui.theme.Brown70
import io.github.horaciocome1.lucia.ui.theme.Brown80
import io.github.horaciocome1.lucia.ui.theme.LuciaTheme
import kotlinx.coroutines.delay
import kotlin.random.Random

@Destination
@Composable
fun TopicsScreen(
    navigator: DestinationsNavigator?,
    duration: Long,
    level: Level
) {
    val topics = remember { mutableStateOf(topics.map { it to false }) }
    val runningInitialAnimation = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        runningInitialAnimation.value = true
        topics.value.toMutableList().let { mutableTopics ->
            delay(300)
            for (index in mutableTopics.indices) {
                val (topic, _) = mutableTopics[index]
                mutableTopics[index] = topic to true
            }
            topics.value = mutableTopics
        }
        topics.value.toMutableList().let { mutableTopics ->
            delay(700)
            for (index in mutableTopics.indices) {
                val (topic, _) = mutableTopics[index]
                mutableTopics[index] = topic to false
            }
            topics.value = mutableTopics
        }
        runningInitialAnimation.value = false
    }
    SetupScaffold(
        modifier = Modifier.fillMaxSize(),
        title = "Select topics",
        firstStep = false,
        actionButtonText = "Continue",
        stepComplete = topics.value.any { (_, selected) -> selected } && !runningInitialAnimation.value,
        onNavigateUpClick = { navigator?.navigateUp() },
        onContinueButtonClick = {
            navigator?.navigate(
                direction = InviteScreenDestination(
                    level = level,
                    duration = duration,
                    topics = TopicsWrapper(
                        topics = topics.value.filter { it.second }.map { it.first }
                    )
                ),
                builder = {
                    popUpTo(route = HomeScreenDestination.route)
                }
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Box(
                modifier = Modifier.fillMaxHeight(.8f),
                contentAlignment = Alignment.Center
            ) {
                LazyHorizontalGrid(
                    modifier = Modifier.height(260.dp),
                    rows = GridCells.Adaptive(76.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    itemsIndexed(topics.value) { index, (topic, selected) ->
                        RadioBox(
                            modifier = Modifier
                                .width(102.dp)
                                .height(76.dp),
                            backgroundColor = Color(level.color),
                            backgroundColorSelected = Color(level.colorSelected),
                            onClick = {
                                val updatedTopics = topics.value.toMutableList().apply {
                                    set(index, topic to selected.not())
                                }
                                topics.value = updatedTopics
                            },
                            text = topic.name,
                            selected = selected
                        )
                    }
                }
            }
            IconButton(
                modifier = Modifier.padding(32.dp),
                onClick = {
                    val updatedTopics = topics.value.toMutableList().apply {
                        topics.value.forEachIndexed { index, (topic, _) ->
                            set(index, topic to Random.nextBoolean())
                        }
                    }
                    topics.value = updatedTopics
                }
            ) {
                Image(
                    imageVector = Icons.Outlined.Shuffle,
                    contentDescription = "Randomize topics"
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewTopicsScreen() {
    LuciaTheme {
        TopicsScreen(
            navigator = null,
            level = Level(
                name = "Madoda",
                color = Brown70.value,
                colorSelected = Brown80.value
            ),
            duration = 90
        )
    }
}

private val topics = setOf(
    Topic(
        id = "",
        name = "Culture"
    ),
    Topic(
        id = "",
        name = "Sports"
    ),
    Topic(
        id = "",
        name = "History"
    ),
    Topic(
        id = "",
        name = "Food"
    ),
    Topic(
        id = "",
        name = "Space"
    ),
    Topic(
        id = "",
        name = "Business"
    ),
    Topic(
        id = "",
        name = "Finance"
    ),
    Topic(
        id = "",
        name = "Places"
    ),
    Topic(
        id = "",
        name = "Environment"
    ),
    Topic(
        id = "",
        name = "Environment1"
    ),
    Topic(
        id = "",
        name = "Environment2"
    ),
    Topic(
        id = "",
        name = "Environment3"
    ),
    Topic(
        id = "",
        name = "Environment4"
    ),
    Topic(
        id = "",
        name = "Environment5"
    ),
    Topic(
        id = "",
        name = "Environment6"
    )
)