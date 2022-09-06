package io.github.horaciocome1.lucia.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.horaciocome1.lucia.ui.theme.BrightGreen
import io.github.horaciocome1.lucia.ui.theme.Grey
import io.github.horaciocome1.lucia.ui.theme.LuciaTheme

@Composable
fun Seekbar(
    modifier: Modifier = Modifier,
    position: State<Long>,
    duration: State<Long>,
    onNewProgress: (progress: Long) -> Unit,
    onDragStart: (progress: Long) -> Unit = {},
    onDragEnd: (progress: Long) -> Unit = {},
    progressColor: Color = MaterialTheme.colorScheme.primary,
    backgroundColor: Color = Color.Gray,
    sliderColor: Color = MaterialTheme.colorScheme.primary,
    sliderWidth: Dp = 20.dp,
    sliderHeight: Dp = 12.dp,
    radius: Dp = 12.dp
) {
    val isDragging = remember { mutableStateOf(false) }
    val dragProgress = remember { mutableStateOf(0f) }

    val sliderWidthPx = with(LocalDensity.current) { sliderWidth.toPx() }
    val sliderHeightPx = with(LocalDensity.current) { sliderHeight.toPx() }
    val sliderSize = Size(sliderWidthPx, sliderHeightPx)
    val sliderCornerRadiusPx = with(LocalDensity.current) { radius.toPx() }
    val sliderSizeOnDragging = Size(sliderWidthPx * 1.5f, sliderHeightPx * 1.5f)

    Box(
        modifier = modifier
            .height(64.dp)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = {
                            val p = it.x / (size.width - sliderWidthPx)
                            dragProgress.value = p
                            val newPosition = (dragProgress.value * duration.value).toLong()
                            onDragStart(newPosition)
                            isDragging.value = true
                        },
                        onDragEnd = {
                            val newPosition = (dragProgress.value * duration.value).toLong()
                            onDragEnd(newPosition)
                            isDragging.value = false
                        },
                        onDrag = { change: PointerInputChange, dragAmount ->
                            change.consume()
                            // calculate progress from 0.0f to 1.0f
                            val newProgress = dragAmount.x / (size.width - sliderWidthPx)
                            dragProgress.value = (dragProgress.value + newProgress).roundTo1()
                            val newPosition = (dragProgress.value * duration.value).toLong()
                            onNewProgress(newPosition)
                        }
                    )
                }
                .pointerInput(Unit) {
                    detectTapGestures {
                        val newProgress = it.x / (size.width - sliderWidthPx)
                        dragProgress.value = newProgress.roundTo1()
                        val newPosition = (dragProgress.value * duration.value).toLong()
                        onNewProgress(newPosition)
                        onDragEnd(newPosition)
                    }
                }
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            val progressFloat = (position.value.toDouble() / duration.value.toDouble()).toFloat()
            val offsetX = (canvasWidth - sliderSize.width) * progressFloat

            val p = if (isDragging.value) {
                dragProgress.value
            } else {
                progressFloat
            }

            val sliderOffsetX = (canvasWidth - sliderSize.width) * p

            // increase slider size on touch
            val adaptiveSliderSize = if (isDragging.value) sliderSizeOnDragging else sliderSize
            val adaptiveSliderColor = if (isDragging.value) progressColor else sliderColor

            val cornerRadius = CornerRadius(sliderCornerRadiusPx, sliderCornerRadiusPx)

            drawRoundRect(
                color = backgroundColor,
                topLeft = Offset(x = sliderOffsetX, y = canvasHeight / 2 - sliderSize.height / 2),
                size = Size(width = canvasWidth - offsetX, height = sliderHeightPx),
                cornerRadius = cornerRadius
            )

            drawRoundRect(
                color = progressColor,
                topLeft = Offset(x = 0f, y = canvasHeight / 2 - sliderSize.height / 2),
                size = Size(width = offsetX + sliderWidthPx, height = sliderHeightPx),
                cornerRadius = cornerRadius
            )

            drawRoundRect(
                color = adaptiveSliderColor,
                topLeft = Offset(x = sliderOffsetX, y = canvasHeight / 2 - adaptiveSliderSize.height / 2),
                size = adaptiveSliderSize,
                cornerRadius = cornerRadius
            )
        }
    }
}

fun Float.roundTo1(): Float {
    return when (this) {
        in (0f..1f) -> this
        in (Float.NEGATIVE_INFINITY..0f) -> 0f
        else -> 1f
    }
}

@Preview(showBackground = true)
@Composable
fun SeekBarPreview() {
    LuciaTheme {
        Seekbar(
            modifier = Modifier.fillMaxWidth(),
            duration = remember { mutableStateOf(1000) },
            position = remember { mutableStateOf(300) },
            onNewProgress = { },
            sliderColor = Color.White,
            backgroundColor = Grey,
            progressColor = BrightGreen,
            sliderHeight = 56.dp,
            sliderWidth = 56.dp,
            radius = 56.dp
        )
    }
}