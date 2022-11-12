package io.github.horaciocome1.lucia.manage.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import io.github.horaciocome1.lucia.manage.TopicFormViewModel
import io.github.horaciocome1.lucia.setup.model.Topic
import io.github.horaciocome1.lucia.ui.theme.GreenDark
import io.github.horaciocome1.lucia.ui.theme.Indigo500
import io.github.horaciocome1.lucia.ui.theme.LuciaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun TopicFormScreen(
    navigator: ResultBackNavigator<Boolean>?,
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
                title = {},
                navigationIcon = {
                    FilledIconButton(
                        onClick = { navigator?.navigateBack(result = false) },
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
                }
            )
        }
    ) { paddingValues ->
        when (true) {
            state.value.createTopicSuccess,
            state.value.updateTopicSuccess,
            state.value.deleteTopicSuccess -> {
                navigator?.navigateBack(result = true)
            }
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
            else -> {
                Column(
                    modifier = Modifier.padding(paddingValues)
                        .fillMaxWidth()
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = if (topic != null) {
                            Arrangement.SpaceBetween
                        } else {
                            Arrangement.End
                        }
                    ) {
                        if (topic != null) {
                            FilledTonalIconButton(
                                modifier = Modifier.padding(32.dp),
                                onClick = { viewModel.deleteTopic(topic.id) },
                                enabled = !state.value.loading,
                                colors = IconButtonDefaults.filledTonalIconButtonColors(
                                    containerColor = Color.Red.copy(alpha = 0.1f),
                                    contentColor = Color.Red
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.DeleteOutline,
                                    contentDescription = "Delete"
                                )
                            }
                        }
                        Button(
                            modifier = Modifier.padding(32.dp),
                            onClick = {
                                if (topic != null) {
                                    viewModel.updateTopic(topic.copy(name = title.value.text))
                                } else {
                                    viewModel.createTopic(Topic("", title.value.text))
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = GreenDark.copy(alpha = 0.1f),
                                contentColor = GreenDark.copy(alpha = 0.75f)
                            ),
                            enabled = when (false) {
                                !state.value.loading,
                                title.value.text.isNotBlank(),
                                (title.value.text != topic?.name) -> false
                                else -> true
                            }
                        ) {
                            Text(
                                text = if (topic != null) "Save changes" else "Add",
                                style = MaterialTheme.typography.bodyMedium.copy(
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