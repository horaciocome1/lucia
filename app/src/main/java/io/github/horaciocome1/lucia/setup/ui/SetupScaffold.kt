package io.github.horaciocome1.lucia.setup.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.horaciocome1.lucia.ui.theme.LuciaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetupScaffold(
    modifier: Modifier = Modifier,
    title: String,
    actionButtonText: String,
    firstStep: Boolean,
    stepComplete: Boolean,
    onNavigateUpClick: () -> Unit,
    onContinueButtonClick: () -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            SmallTopAppBar(
                title = {},
                navigationIcon = {
                    if (!firstStep) {
                        IconButton(
                            onClick = onNavigateUpClick
                        ) {
                            Image(
                                imageVector = Icons.Outlined.ArrowBackIosNew,
                                contentDescription = "Back"
                            )
                        }
                    }
                },
                actions = {
                    if (firstStep) {
                        IconButton(
                            onClick = onNavigateUpClick
                        ) {
                            Image(
                                imageVector = Icons.Outlined.Close,
                                contentDescription = "Close"
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            Button(
                onClick = onContinueButtonClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(80.dp),
                shape = RectangleShape,
                enabled = stepComplete
            ) {
                Text(
                    text = actionButtonText,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    ) { paddingValues ->
        val contentWidth = 300.dp
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text(
                modifier = Modifier
                    .widthIn(contentWidth)
                    .padding(top = 32.dp, start = 32.dp, end = 32.dp),
                text = title,
                style = MaterialTheme.typography.headlineLarge
            )
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
                content = content
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewSetupScreen() {
    LuciaTheme {
        SetupScaffold(
            modifier = Modifier.fillMaxSize(),
            firstStep = true,
            title = "Setup Screen Preview",
            actionButtonText = "Continue",
            stepComplete = true,
            onNavigateUpClick = {},
            onContinueButtonClick = {}
        ) {
            Text("test")
        }
    }
}