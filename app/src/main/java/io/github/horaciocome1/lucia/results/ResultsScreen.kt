package io.github.horaciocome1.lucia.results

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.horaciocome1.lucia.results.model.Result
import io.github.horaciocome1.lucia.results.model.ResultsWrapper
import io.github.horaciocome1.lucia.setup.model.Player
import io.github.horaciocome1.lucia.setup.model.PlayersWrapper
import io.github.horaciocome1.lucia.ui.theme.LuciaTheme
import java.util.Calendar

@Destination
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultsScreen(
    navigator: DestinationsNavigator?,
    players: PlayersWrapper,
    results: ResultsWrapper
) {
    val playersPoints = remember { mutableStateOf(emptyList<Pair<Player, Result?>>()) }
    playersPoints.value = buildList {
        players.players.forEach { player ->
            add(player to results.results.find { player.id == it.playerId })
        }
        sortByDescending { it.second?.points }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = { navigator?.navigateUp() }
                    ) {
                        Image(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = "Close"
                        )
                    }
                }
            )
        },
        bottomBar = {
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(104.dp)
                    .padding(start = 32.dp, bottom = 24.dp, end = 32.dp),
                shape = CircleShape
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(.9f)
                        .padding(start = 24.dp),
                    text = "Share results",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center
                )
                Image(
                    imageVector = Icons.Outlined.Send,
                    contentDescription = "Share",
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 32.dp, start = 32.dp, end = 32.dp),
                text = "Game results",
                style = MaterialTheme.typography.headlineLarge
            )
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    itemsIndexed(playersPoints.value) { index, (player, result) ->
                        Text(
                            text = buildAnnotatedString {
                                append(player.name)
                                if (index == 0 && result?.points !in setOf(null, 0)) {
                                    append(" won")
                                }
                                withStyle(
                                    MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Light
                                    ).toSpanStyle()
                                ) {
                                    append(" with")
                                    append(" ${result?.points ?: 0}")
                                    append(" points")
                                }
                            },
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewInviteScreen() {
    LuciaTheme {
        ResultsScreen(
            navigator = null,
            players = PlayersWrapper(players),
            results = ResultsWrapper(results)
        )
    }
}

private val players = listOf(
    Player(
        id = "a",
        name = "Mike",
        joinedAt = Calendar.getInstance().timeInMillis - 100000999999
    ),
    Player(
        id = "b",
        name = "Juliet",
        joinedAt = Calendar.getInstance().timeInMillis
    ),
    Player(
        id = "c",
        name = "Jordan",
        joinedAt = Calendar.getInstance().timeInMillis - 999999999999
    ),
    Player(
        id = "d",
        name = "Muchanga",
        joinedAt = Calendar.getInstance().timeInMillis - 11111999999
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