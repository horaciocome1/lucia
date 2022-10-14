package io.github.horaciocome1.lucia.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.horaciocome1.lucia.LuciaConfig
import io.github.horaciocome1.lucia.ui.theme.LuciaTheme

@RootNavGraph(start = true)
@Destination(start = true)
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator?,
    userName: String = "Lucia",
    playerVersion: Boolean = LuciaConfig.VERSION_PLAY,
    managementVersion: Boolean = LuciaConfig.VERSION_MANAGEMENT
) {
    when {
        playerVersion -> {
            PlayerContent(
                navigator = navigator,
                userName = userName
            )
        }
        managementVersion -> {
            ManagementContent(
                navigator = navigator,
                userName = userName
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    LuciaTheme {
        HomeScreen(
            navigator = null,
            userName = "Melucha",
            playerVersion = true,
            managementVersion = false
        )
    }
}