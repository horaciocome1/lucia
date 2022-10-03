package io.github.horaciocome1.lucia.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.horaciocome1.lucia.ui.theme.LuciaTheme

@Composable
fun CircularProgressBar(
    modifier: Modifier = Modifier,
    strokeColor: Color = MaterialTheme.colorScheme.primary,
    strokeWidth: Dp = 8.dp,
    size: Dp = 64.dp,
    currentValue: Long,
    maxValue: Long,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(Modifier.size(size)) {
            drawArc(
                startAngle = -90f,
                sweepAngle = -360f * currentValue / maxValue,
                useCenter = false,
                style = Stroke(
                    width = strokeWidth.toPx(),
                    cap = StrokeCap.Round
                ),
                color = strokeColor
            )
        }
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCircularProgressBar() {
    LuciaTheme {
        CircularProgressBar(
            modifier = Modifier,
            strokeColor = MaterialTheme.colorScheme.primary,
            strokeWidth = 8.dp,
            size = 180.dp,
            currentValue = 90,
            maxValue = 240
        ) {
            Text(
                text = "120",
                style = MaterialTheme.typography.displayMedium.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
    }
}