package io.github.horaciocome1.lucia.game

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.horaciocome1.lucia.destinations.HomeScreenDestination
import io.github.horaciocome1.lucia.destinations.ResultsScreenDestination
import io.github.horaciocome1.lucia.game.model.Option
import io.github.horaciocome1.lucia.game.model.Question
import io.github.horaciocome1.lucia.results.model.Result
import io.github.horaciocome1.lucia.results.model.ResultsWrapper
import io.github.horaciocome1.lucia.setup.model.Level
import io.github.horaciocome1.lucia.setup.model.PlayersWrapper
import io.github.horaciocome1.lucia.setup.model.TopicsWrapper
import io.github.horaciocome1.lucia.ui.component.CircularProgressBar
import io.github.horaciocome1.lucia.ui.component.DotProgressBar
import io.github.horaciocome1.lucia.ui.theme.Brown70
import io.github.horaciocome1.lucia.ui.theme.Brown80
import io.github.horaciocome1.lucia.ui.theme.Indigo500
import io.github.horaciocome1.lucia.ui.theme.LuciaTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Destination
@Composable
fun QuestionsScreen(
    navigator: DestinationsNavigator?,
    duration: Long,
    level: Level,
    topics: TopicsWrapper,
    players: PlayersWrapper
) {
    val remainingSeconds = remember { mutableStateOf(duration) }
    val questions = remember {
        mutableStateOf<List<Pair<Question, Option?>>>(questions.map { it to null })
    }
    val pagerState = rememberPagerState(0)
    LaunchedEffect(Unit) {
        (duration downTo 0).asFlow().onEach {
            delay(1000)
        }.onCompletion {
            navigator?.navigate(
                direction = ResultsScreenDestination(
                    players = players,
                    results = ResultsWrapper(results)
                ),
                builder = {
                    popUpTo(route = HomeScreenDestination.route)
                }
            )
        }.collectLatest { remaining ->
            remainingSeconds.value = remaining
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    FilledIconButton(
                        onClick = { navigator?.navigateUp() },
                        shape = RoundedCornerShape(8.dp),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Indigo500,
                            contentColor = contentColorFor(backgroundColor = Indigo500)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = "Close"
                        )
                    }
                },
                actions = {
                    val animatedStrokeWidth = animateDpAsState(
                        if (remainingSeconds.value % 2L == 0L) 2.dp else 1.dp
                    )
                    CircularProgressBar(
                        modifier = Modifier.padding(end = 16.dp),
                        size = 38.dp,
                        strokeWidth = animatedStrokeWidth.value,
                        currentValue = remainingSeconds.value,
                        maxValue = duration
                    ) {
                        Text(
                            text = remainingSeconds.value.toString(),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                DotProgressBar(
                    currentValue = pagerState.currentPage,
                    maxValue = questions.value.size
                )
            }
        }
    ) { paddingValues ->
        HorizontalPager(
            count = questions.value.size,
            state = pagerState,
            contentPadding = paddingValues
        ) { index ->
            val (question, selectedOption) = questions.value[index]
            QuestionItem(
                color = Color(level.color),
                colorSelected = Color(level.colorSelected),
                question = question,
                selectedOption = selectedOption,
                onOptionSelected = { option ->
                    val updatedQuestions = questions.value.toMutableList().apply {
                        set(index, question to option)
                    }
                    questions.value = updatedQuestions
                }
            )
        }
        LaunchedEffect(pagerState.currentPage) {
            pagerState.scrollToPage(pagerState.currentPage)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewQuestionsScreen() {
    LuciaTheme {
        QuestionsScreen(
            navigator = null,
            level = Level(
                name = "Madoda",
                color = Brown70.value,
                colorSelected = Brown80.value
            ),
            duration = 90,
            topics = TopicsWrapper(emptyList()),
            players = PlayersWrapper(emptyList())
        )
    }
}

private val questions = listOf(
    Question(
        title = "Quantos dias a mais tem um ano bisexto?",
        options = listOf(
            Option(
                title = "1 dias",
                points = 0
            ),
            Option(
                title = "6 dias",
                points = 0
            ),
            Option(
                title = "12 dias",
                points = 0
            ),
            Option(
                title = "366 dias",
                points = 0
            )
        )
    ),
    Question(
        title = "Qual destes foi um monarca em Moçambique?",
        options = listOf(
            Option(
                title = "Ngungunhane",
                points = 0
            ),
            Option(
                title = "Samora Machel",
                points = 0
            ),
            Option(
                title = "Luísa Diogo",
                points = 0
            ),
            Option(
                title = "Ubakka",
                points = 0
            )
        )
    ),
    Question(
        title = "O quadrado da hipotenusa é igual a soma dos dois catetos equivale a:",
        options = listOf(
            Option(
                title = "h^2 = c^2 + c^2",
                points = 0
            ),
            Option(
                title = "co^2 + ca^2 = h^2",
                points = 0
            ),
            Option(
                title = "co^2 + ca^2 = c^2 + c^2",
                points = 0
            ),
            Option(
                title = "c^2 + c^2",
                points = 0
            )
        )
    ),
    Question(
        title = "O irmão mais novo do Maxh258 é também conhecido como:",
        options = listOf(
            Option(
                title = "Shainull",
                points = 0
            ),
            Option(
                title = "Chainul",
                points = 0
            ),
            Option(
                title = "Tonito",
                points = 0
            ),
            Option(
                title = "Shai L Ul",
                points = 0
            )
        )
    )
)

private val results = listOf(
    Result(
        playerId = "a",
        points = 295
    ),
    Result(
        playerId = "b",
        points = 415
    ),
    Result(
        playerId = "c",
        points = 320
    ),
    Result(
        playerId = "d",
        points = 376
    ),
    Result(
        playerId = "e",
        points = 320
    )
)