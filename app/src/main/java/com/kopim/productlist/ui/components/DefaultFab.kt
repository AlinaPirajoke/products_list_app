package com.kopim.productlist.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.kopim.productlist.ui.theme.deepBlue
import com.kopim.productlist.ui.theme.fabSize
import com.kopim.productlist.ui.theme.lightBlue
import com.kopim.productlist.ui.theme.surfaceWhite

@Composable
fun DefaultFab(
    onClick: () -> Unit,
    background: Color = MaterialTheme.colorScheme.primary,
    icon: @Composable () -> Unit
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = Modifier.size(fabSize),
        containerColor = background,
        shape = MaterialTheme.shapes.medium
    ) {
        icon()
    }
}