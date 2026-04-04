package com.kopim.productlist.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.kopim.productlist.R
import com.kopim.productlist.ui.theme.ProductsTheme
import com.kopim.productlist.ui.theme.topIconSize

@Composable
fun BackNavigationButton(
    onClick: () -> Unit,
    contentDescription: String,
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colorScheme.primaryContainer,
) {
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(
            modifier = Modifier.size(topIconSize),
            painter = painterResource(R.drawable.arrow_back),
            contentDescription = contentDescription,
            tint = tint,
        )
    }
}

@Preview
@Composable
private fun BackNavigationButtonPreview() {
    ProductsTheme {
        BackNavigationButton(onClick = {}, contentDescription = "Назад")
    }
}
