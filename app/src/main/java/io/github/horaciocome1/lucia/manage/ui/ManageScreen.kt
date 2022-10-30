package io.github.horaciocome1.lucia.manage.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.QuestionAnswer
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.horaciocome1.lucia.destinations.ManageTopicsScreenDestination
import io.github.horaciocome1.lucia.ui.component.ListItem
import io.github.horaciocome1.lucia.ui.theme.LuciaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun ManageScreen(
    navigator: DestinationsNavigator?
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = { navigator?.navigateUp() }
                    ) {
                        Image(
                            imageVector = Icons.Outlined.ArrowBackIosNew,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text(
                modifier = Modifier
                    .widthIn(300.dp)
                    .padding(top = 32.dp, start = 32.dp, end = 32.dp),
                text = "Configure",
                style = MaterialTheme.typography.headlineLarge
            )
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LazyColumn(
                    modifier = Modifier.width(300.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(options) { (title, icon) ->
                        ListItem(
                            modifier = Modifier.fillMaxWidth(),
                            text = title,
                            icon = icon,
                            onClick = {
                                when (title) {
                                    "Topics" -> navigator?.navigate(ManageTopicsScreenDestination())
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewManageScreen() {
    LuciaTheme {
        ManageScreen(
            navigator = null
        )
    }
}

private val options = listOf(
    "Topics" to Icons.Outlined.List,
    "Questions" to Icons.Outlined.QuestionAnswer
)