package com.kopim.productlist.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kopim.productlist.ui.theme.ProductsTheme
import com.kopim.productlist.ui.theme.defaultElevation

@Composable
fun ElevatingTile(
    modifier: Modifier = Modifier,
    picked: Boolean,
    onPick: () -> Unit,
    onLongPress: () -> Unit,
    content: @Composable () -> Unit
) {

    // Анимированная высота тени
    val elevation by animateDpAsState(
        targetValue = if (picked) defaultElevation else 0.dp,
        label = "elevation"
    )

    Box(
        modifier = modifier
            .shadow(
                elevation = elevation,
                shape = MaterialTheme.shapes.medium,
                ambientColor = MaterialTheme.colorScheme.secondary
            )
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { onLongPress() },
                    onTap = { onPick() }
                )
            }
            .then(modifier),
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun ElevatingTilePreview() {
    ProductsTheme {
        ElevatingTile(
            picked = true,
            onPick = {},
            onLongPress = {},
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(MaterialTheme.colorScheme.surface)
            )
        }
    }
}