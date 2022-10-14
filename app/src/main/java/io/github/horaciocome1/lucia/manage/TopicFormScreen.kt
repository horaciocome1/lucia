package io.github.horaciocome1.lucia.manage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.horaciocome1.lucia.setup.model.Topic
import io.github.horaciocome1.lucia.ui.theme.LuciaTheme
import io.github.horaciocome1.lucia.ui.theme.RedLight

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun TopicFormScreen(
    navigator: DestinationsNavigator?,
    topic: Topic?,
    viewModel: TopicFormViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    val id = rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(topic?.id ?: ""))
    }
    val title = rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(topic?.name ?: ""))
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Topic",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
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
                onClick = {
                    if (topic != null) {
                        viewModel.updateTopic(topic.copy(name = title.value.text))
                    } else {
                        viewModel.createTopic(Topic("", title.value.text))
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(80.dp),
                shape = RectangleShape,
                enabled = title.value.text.isNotBlank() && !state.value.loading
            ) {
                Text(
                    text = if (topic != null) "Save changes" else "Add",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    ) { paddingValues ->

        when (true) {
            state.value.loading -> {
                Box(
                    modifier = Modifier.padding(paddingValues)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(32.dp))
                }
            }
            state.value.createTopicError -> {
                Box(
                    modifier = Modifier.padding(paddingValues)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier.padding(32.dp),
                        text = state.value.errorMessage.ifBlank {
                            "Some error occurred while adding your topic" + " Please try again later"
                        },
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Light)
                    )
                }
            }
            state.value.updateTopicError -> {
                Box(
                    modifier = Modifier.padding(paddingValues)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier.padding(32.dp),
                        text = state.value.errorMessage.ifBlank {
                            "Some error occurred while saving your changes" + " Please try again later"
                        },
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Light)
                    )
                }
            }
            state.value.deleteTopicError -> {
                Box(
                    modifier = Modifier.padding(paddingValues)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier.padding(32.dp),
                        text = state.value.errorMessage.ifBlank {
                            "Some error occurred while deleting your topic" + " Please try again later"
                        },
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Light)
                    )
                }
            }
            state.value.createTopicSuccess,
            state.value.updateTopicSuccess,
            state.value.deleteTopicSuccess -> {
                navigator?.navigateUp()
            }
            else -> {
                Column(
                    modifier = Modifier.padding(paddingValues)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 16.dp),
                        value = id.value,
                        onValueChange = { id.value = it },
                        label = { Text("Id") },
                        enabled = topic != null,
                        readOnly = true
                    )
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        value = title.value,
                        onValueChange = { title.value = it },
                        label = { Text("Title") }
                    )
                    if (topic != null) {
                        Button(
                            modifier = Modifier.padding(32.dp),
                            onClick = { viewModel.deleteTopic(topic.id) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = RedLight
                            ),
                            enabled = !state.value.loading
                        ) {
                            Text(
                                text = "Delete",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = Color.Red,
                                    fontWeight = FontWeight.Bold
                                )
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
fun PreviewTopicFormScreen() {
    LuciaTheme {
        TopicFormScreen(
            navigator = null,
            topic = null
        )
    }
}