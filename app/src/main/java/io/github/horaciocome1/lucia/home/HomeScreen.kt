package io.github.horaciocome1.lucia.home

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.horaciocome1.lucia.ui.theme.LuciaTheme

@Composable
fun HomeScreen(modifier: Modifier = Modifier, userName: String = "Lucia") {
    val title = buildAnnotatedString {
        withStyle(MaterialTheme.typography.headlineSmall.toSpanStyle()) {
            append("Welcome,")
        }
        append("\n")
        append(userName)
    }
    Surface(modifier) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(Modifier.height(60.dp))
            Button(
                onClick = {},
                modifier = Modifier
                    .widthIn(200.dp)
                    .heightIn(100.dp),
                shape = CircleShape
            ) {
                Text("CHALLENGE")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    LuciaTheme {
        HomeScreen(
            modifier = Modifier
                .fillMaxSize()
        )
    }
}