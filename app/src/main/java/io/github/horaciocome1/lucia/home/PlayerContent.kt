package io.github.horaciocome1.lucia.home

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.horaciocome1.lucia.LuciaConfig
import io.github.horaciocome1.lucia.destinations.DurationScreenDestination
import io.github.horaciocome1.lucia.ui.theme.LuciaTheme

@RootNavGraph(start = LuciaConfig.VERSION_PLAY)
@Destination(start = LuciaConfig.VERSION_PLAY)
@Composable
fun PlayerContent(
    navigator: DestinationsNavigator?,
    userName: String = "Lucia"
) {
    val title = buildAnnotatedString {
        withStyle(MaterialTheme.typography.headlineSmall.toSpanStyle()) {
            append("Welcome,")
        }
        append("\n")
        append(userName)
    }
    val infiniteTransition = rememberInfiniteTransition()
    val buttonWidth by infiniteTransition.animateFloat(
        initialValue = 180f,
        targetValue = 200f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 500
                195f at 400
            },
            repeatMode = RepeatMode.Reverse
        )
    )
    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(60.dp))
            Button(
                onClick = { navigator?.navigate(DurationScreenDestination()) },
                modifier = Modifier
                    .widthIn(buttonWidth.dp)
                    .heightIn((buttonWidth / 2).dp),
                shape = CircleShape
            ) {
                Text(
                    text = "CHALLENGE",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PlayerContentPreview() {
    LuciaTheme {
        PlayerContent(navigator = null)
    }
}