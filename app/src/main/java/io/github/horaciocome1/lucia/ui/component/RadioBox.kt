package io.github.horaciocome1.lucia.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.horaciocome1.lucia.ui.theme.Green70
import io.github.horaciocome1.lucia.ui.theme.Green80
import io.github.horaciocome1.lucia.ui.theme.Grey
import io.github.horaciocome1.lucia.ui.theme.GreyDark
import io.github.horaciocome1.lucia.ui.theme.LuciaTheme
import java.util.Locale

@Composable
fun RadioBox(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    backgroundColor: Color,
    backgroundColorSelected: Color = backgroundColor,
    text: String
) {
    val animatedPadding = animateDpAsState(if (selected) 0.dp else 4.dp)
    val animatedColor = animateColorAsState(if (selected) backgroundColorSelected else backgroundColor)
    val animatedFontWeight = animateIntAsState(if (selected) FontWeight.SemiBold.weight else FontWeight.Light.weight)
    val animatedStrokeColor = animateColorAsState(if (selected) Grey else backgroundColor)

    Card(
        modifier = modifier
            .padding(animatedPadding.value),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(width = 1.dp, color = animatedStrokeColor.value),
        colors = CardDefaults.cardColors(containerColor = animatedColor.value)
    ) {
        Box(
            modifier = Modifier
                .selectable(selected = selected, onClick = onClick)
                .fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            Text(
                modifier = Modifier
                    .padding(8.dp),
                text = text.uppercase(Locale.getDefault()),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.End,
                color = GreyDark,
                fontWeight = FontWeight(animatedFontWeight.value)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRadioBox() {
    LuciaTheme {
        RadioBox(
            selected = true,
            onClick = {},
            backgroundColor = Green70,
            backgroundColorSelected = Green80,
            text = "junior"
        )
    }
}