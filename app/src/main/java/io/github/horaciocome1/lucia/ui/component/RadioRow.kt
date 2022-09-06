package io.github.horaciocome1.lucia.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.horaciocome1.lucia.ui.theme.Green70
import io.github.horaciocome1.lucia.ui.theme.Green80
import io.github.horaciocome1.lucia.ui.theme.Grey
import io.github.horaciocome1.lucia.ui.theme.GreyDark
import io.github.horaciocome1.lucia.ui.theme.LuciaTheme

@Composable
fun RadioRow(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    backgroundColor: Color,
    backgroundColorSelected: Color = backgroundColor,
    text: String,
    height: Dp = 64.dp
) {
    val animatedHeight = animateDpAsState(if (selected) height + height / 2 else height)
    val animatedColor = animateColorAsState(if (selected) backgroundColorSelected else backgroundColor)
    val animatedFontWeight = animateIntAsState(if (selected) FontWeight.SemiBold.weight else FontWeight.Light.weight)
    val animatedStrokeColor = animateColorAsState(if (selected) Grey else backgroundColor)

    Card(
        modifier = modifier
            .selectable(selected = selected, onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(width = 1.dp, color = animatedStrokeColor.value),
        colors = CardDefaults.cardColors(containerColor = animatedColor.value)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(animatedHeight.value),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                color = GreyDark,
                fontWeight = FontWeight(animatedFontWeight.value)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRadioRow() {
    LuciaTheme {
        RadioRow(
            Modifier.fillMaxWidth(),
            selected = true,
            onClick = {},
            backgroundColor = Green70,
            backgroundColorSelected = Green80,
            text = "junior"
        )
    }
}