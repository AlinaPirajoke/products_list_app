package com.kopim.productlist.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kopim.productlist.ui.theme.defaultPadding
import com.kopim.productlist.ui.theme.textBlack

@Composable
fun ScreenHeader(
    text: String,
    iconPainter: VectorPainter,
    iconTint: Color,
    onIconClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(defaultPadding),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = textBlack,
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium)
        )

        SquareButton(
            onClick = onIconClick,
            background = Color.Transparent
        ) {
            Icon(painter = iconPainter, tint = iconTint, contentDescription = null)
        }
    }
}