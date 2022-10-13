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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Shuffle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.horaciocome1.lucia.destinations.HomeScreenDestination
import io.github.horaciocome1.lucia.destinations.InviteScreenDestination
import io.github.horaciocome1.lucia.setup.TopicsViewModel
import io.github.horaciocome1.lucia.setup.model.Level
import io.github.horaciocome1.lucia.setup.model.Topic
import io.github.horaciocome1.lucia.setup.model.TopicsWrapper
import io.github.horaciocome1.lucia.ui.component.RadioBox
import io.github.horaciocome1.lucia.ui.theme.Brown70
import io.github.horaciocome1.lucia.ui.theme.Brown80
import io.github.horaciocome1.lucia.ui.theme.LuciaTheme

@Destination
@Composable
fun TopicsScreen(
    navigator: DestinationsNavigator?,
    duration: Long,
    level: Level,
    viewModel: TopicsViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    SetupScaffold(
        modifier = Modifier.fillMaxSize(),
        title = "Select topics",
        firstStep = false,
        actionButtonText = "Continue",
        stepComplete = state.value.selectedTopics.isNotEmpty() && !state.value.disableUserActions,
        onNavigateUpClick = { navigator?.navigateUp() },
        onContinueButtonClick = {
            navigator?.navigate(
                direction = InviteScreenDestination(
                    level = level,
                    duration = duration,
                    topics = TopicsWrapper(
                        topics = state.value.selectedTopics
                    )
                ),
                builder = {
                    popUpTo(route = HomeScreenDestination.route)
                }
            )
        }
    ) {
        when {
            state.value.loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.size(32.dp),
                    color = Color(level.colorSelected)
                )
            }
            state.value.retrieveError -> {
                Text(
                    modifier = Modifier.padding(32.dp),
                    text = state.value.errorMessage.ifBlank {
                        "We were not able to reach our servers"
                    },
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Light
                    )
                )
            }
            state.value.retrieveSuccess && state.value.topics.isEmpty() -> {
                Text(
                    modifier = Modifier.padding(32.dp),
                    text = "We were not able to retrieve your topics",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Light
                    )
                )
            }
            else -> {
                TopicsContent(
                    topics = state.value.topics,
                    level = level,
                    onTopicSelected = viewModel::onTopicSelected,
                    isTopicSelected = viewModel::isTopicSelected,
                    onRandomize = viewModel::randomize,
                    disableUserActions = state.value.disableUserActions
                )
            }
        }
    }
}

@Composable
fun TopicsContent(
    topics: List<Topic>,
    level: Level,
    onTopicSelected: (topic: Topic) -> Unit,
    isTopicSelected: (topic: Topic) -> Boolean,
    disableUserActions: Boolean,
    onRandomize: () -> Unit
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
                items(topics) { topic ->
                    RadioBox(
                        modifier = Modifier
                            .width(102.dp)
                            .height(76.dp),
                        backgroundColor = Color(level.color),
                        backgroundColorSelected = Color(level.colorSelected),
                        onClick = { onTopicSelected(topic) },
                        text = topic.name,
                        selected = isTopicSelected(topic)
                    )
                }
            }
        }
        IconButton(
            modifier = Modifier.padding(32.dp),
            enabled = !disableUserActions,
            onClick = onRandomize
        ) {
            Image(
                imageVector = Icons.Outlined.Shuffle,
                contentDescription = "Randomize topics"
            )
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