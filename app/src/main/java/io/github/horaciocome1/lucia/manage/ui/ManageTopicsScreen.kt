package io.github.horaciocome1.lucia.manage.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import io.github.horaciocome1.lucia.destinations.TopicFormScreenDestination
import io.github.horaciocome1.lucia.setup.TopicsViewModel
import io.github.horaciocome1.lucia.ui.component.ListItem
import io.github.horaciocome1.lucia.ui.theme.LuciaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun ManageTopicsScreen(
    navigator: DestinationsNavigator?,
    resultRecipient: ResultRecipient<TopicFormScreenDestination, Boolean>?,
    viewModel: TopicsViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    resultRecipient?.onNavResult { navResult ->
        when (navResult) {
            NavResult.Canceled -> Unit
            is NavResult.Value -> viewModel.retrieveTopics()
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Configure topics",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
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
        Box(
            modifier = Modifier.fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when {
                state.value.loading -> {
                    CircularProgressIndicator(modifier = Modifier.size(32.dp))
                }
                state.value.retrieveError -> {
                    Text(
                        modifier = Modifier.padding(32.dp),
                        text = state.value.errorMessage.ifBlank {
                            "We were not able to reach our servers"
                        },
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Light)
                    )
                }
                state.value.retrieveSuccess && state.value.topics.isEmpty() -> {
                    Text(
                        modifier = Modifier.padding(32.dp),
                        text = "We were not able to retrieve your topics",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Light)
                    )
                }
                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(horizontal = 32.dp)
                    ) {
                        item {
                            ListItem(
                                modifier = Modifier.fillMaxWidth(),
                                text = "New",
                                icon = Icons.Outlined.Add,
                                onClick = {
                                    navigator?.navigate(TopicFormScreenDestination(topic = null))
                                }
                            )
                        }
                        items(state.value.topics) { topic ->
                            ListItem(
                                modifier = Modifier.fillMaxWidth(),
                                text = topic.name,
                                icon = Icons.Outlined.Edit,
                                onClick = {
                                    navigator?.navigate(TopicFormScreenDestination(topic = topic))
                                }
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
fun PreviewManageTopicsScreen() {
    LuciaTheme {
        ManageTopicsScreen(
            navigator = null,
            resultRecipient = null
        )
    }
}