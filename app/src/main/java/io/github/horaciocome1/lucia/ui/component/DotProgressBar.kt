package io.github.horaciocome1.lucia.ui.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.horaciocome1.lucia.ui.theme.LuciaTheme

@Composable
fun DotProgressBar(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    currentValue: Int,
    maxValue: Int
) {
    LazyRow(
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight()
    ) {
        items(maxValue) { index ->
            val animatedPadding = animateDpAsState(if (index != currentValue) 1.dp else 0.dp)
            val animatedSize = animateDpAsState(if (index == currentValue) 8.dp else 6.dp)
            Box(
                modifier = Modifier
                    .padding(animatedPadding.value)
                    .size(animatedSize.value)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDotProgressBar() {
    LuciaTheme {
        DotProgressBar(
            currentValue = 4,
            maxValue = 10
        )
    }
}