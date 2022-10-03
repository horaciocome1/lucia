package io.github.horaciocome1.lucia.game

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.horaciocome1.lucia.game.model.Option
import io.github.horaciocome1.lucia.game.model.Question
import io.github.horaciocome1.lucia.ui.component.RadioRow
import io.github.horaciocome1.lucia.ui.theme.Brown70
import io.github.horaciocome1.lucia.ui.theme.Brown80
import io.github.horaciocome1.lucia.ui.theme.LuciaTheme

@Composable
fun QuestionItem(
    modifier: Modifier = Modifier,
    color: Color,
    colorSelected: Color,
    question: Question,
    selectedOption: Option?,
    onOptionSelected: (option: Option) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .widthIn(300.dp)
                .padding(top = 32.dp, start = 32.dp, end = 32.dp)
                .animateContentSize(),
            text = question.title,
            style = MaterialTheme.typography.headlineLarge
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            question.options.forEach { option ->
                RadioRow(
                    modifier = Modifier
                        .width(300.dp)
                        .padding(vertical = 4.dp),
                    backgroundColor = color,
                    backgroundColorSelected = colorSelected,
                    onClick = { onOptionSelected(option) },
                    selected = option.title == selectedOption?.title,
                    text = option.title
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewQuestionItem() {
    LuciaTheme {
        QuestionItem(
            color = Brown70,
            colorSelected = Brown80,
            question = question,
            selectedOption = null,
            onOptionSelected = {}
        )
    }
}

private val question = Question(
    title = "Quantos dias a mais tem ano bisexto?",
    options = listOf(
        Option(
            title = "1 dias",
            points = 0
        ),
        Option(
            title = "6 dias",
            points = 0
        ),
        Option(
            title = "12 dias",
            points = 0
        ),
        Option(
            title = "366 dias",
            points = 0
        )
    )
)