package io.github.horaciocome1.lucia.setup.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.IosShare
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.horaciocome1.lucia.destinations.HomeScreenDestination
import io.github.horaciocome1.lucia.setup.model.Player
import io.github.horaciocome1.lucia.ui.theme.LuciaTheme
import java.text.DateFormat
import java.util.Calendar

@Destination
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InviteScreen(
    navigator: DestinationsNavigator?,
    maxPlayers: Int = 5
) {
    val players = remember { mutableStateOf(players) }
    val dateFormatter = DateFormat.getDateTimeInstance()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navigator?.popBackStack(
                                route = HomeScreenDestination.route,
                                inclusive = false
                            )
                        }
                    ) {
                        Image(
                            imageVector = if (players.value.isEmpty()) {
                                Icons.Outlined.ArrowBackIosNew
                            } else {
                                Icons.Outlined.Close
                            },
                            contentDescription = if (players.value.isEmpty()) {
                                "Back"
                            } else {
                                "Close"
                            }
                        )
                    }
                },
                actions = {
                    if (players.value.isNotEmpty() && players.value.size < maxPlayers) {
                        IconButton(
                            onClick = {}
                        ) {
                            Image(
                                imageVector = Icons.Outlined.IosShare,
                                contentDescription = "Share"
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            if (players.value.isEmpty()) {
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
                        text = "Send invitation",
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
            } else {
                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(80.dp),
                    shape = RectangleShape
                ) {
                    Text(
                        text = "Start",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
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
                text = "Invite players",
                style = MaterialTheme.typography.headlineLarge
            )
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (players.value.isEmpty()) {
                    Text(
                        text = "No body joined yet",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.ExtraBold
                    )
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        items(players.value) { player ->
                            val dateText = Calendar.getInstance().apply {
                                timeInMillis = player.joinedAt
                            }.time.let { date ->
                                dateFormatter.format(date)
                            }
                            Text(
                                text = buildAnnotatedString {
                                    append(player.name)
                                    append(" joined ")
                                    withStyle(
                                        MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = FontWeight.Light
                                        ).toSpanStyle()
                                    ) {
                                        append(" at ")
                                        append(dateText)
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
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewInviteScreen() {
    LuciaTheme {
        InviteScreen(navigator = null)
    }
}

private val players = listOf(
    Player(
        id = "",
        name = "Mike",
        joinedAt = Calendar.getInstance().timeInMillis - 100000999999
    ),
    Player(
        id = "",
        name = "Juliet",
        joinedAt = Calendar.getInstance().timeInMillis
    ),
    Player(
        id = "",
        name = "Jordan",
        joinedAt = Calendar.getInstance().timeInMillis - 999999999999
    ),
    Player(
        id = "",
        name = "Muchanga",
        joinedAt = Calendar.getInstance().timeInMillis - 11111999999
    )
//    Player(
//        id = "",
//        name = "Juliet",
//        joinedAt = Calendar.getInstance().timeInMillis
//    )
)