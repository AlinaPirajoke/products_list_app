package com.kopim.productlist.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.kopim.productlist.ui.theme.iconButtonIconPadding
import com.kopim.productlist.ui.theme.iconButtonSize

@Composable
fun SquareIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    background: Color = MaterialTheme.colorScheme.secondary,
    icon: @Composable () -> Unit,
) {
    FilledIconButton(
        onClick = onClick,
        Modifier
            .then(modifier)
            .size(iconButtonSize),
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = MaterialTheme.colorScheme.onSecondary,
            containerColor = background
        ),
        shape = MaterialTheme.shapes.small
    ) {
        Box(Modifier.padding(iconButtonIconPadding)) {
            icon()
        }
    }
}