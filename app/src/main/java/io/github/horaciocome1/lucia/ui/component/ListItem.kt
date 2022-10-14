package io.github.horaciocome1.lucia.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.horaciocome1.lucia.ui.theme.Grey
import io.github.horaciocome1.lucia.ui.theme.GreyDark
import io.github.horaciocome1.lucia.ui.theme.LuciaTheme
import io.github.horaciocome1.lucia.ui.theme.PinkLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListItem(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = PinkLight),
        shape = RoundedCornerShape(16.dp),
        onClick = onClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .padding(start = 32.dp, top = 16.dp, bottom = 16.dp),
                painter = rememberVectorPainter(icon),
                contentDescription = "$text icon",
                tint = Grey
            )
            Text(
                modifier = Modifier
                    .padding(start = 24.dp, end = 32.dp, top = 16.dp, bottom = 16.dp),
                text = text,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = GreyDark
                )
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewManageItem() {
    LuciaTheme {
        ListItem(
            text = "Topics",
            icon = Icons.Outlined.List,
            onClick = {}
        )
    }
}