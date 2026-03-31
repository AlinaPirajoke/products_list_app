package com.kopim.productlist.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.kopim.productlist.R
import com.kopim.productlist.ui.theme.fabSize
import com.kopim.productlist.ui.theme.ProductsTheme

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

@Preview
@Composable
private fun DefaultFabPreview() {
    ProductsTheme {
        DefaultFab(onClick = {}) {
            Icon(painter = painterResource(R.drawable.add), contentDescription = null)
        }
    }
}