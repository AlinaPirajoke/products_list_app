package com.kopim.productlist.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kopim.productlist.data.utils.Hint
import com.kopim.productlist.ui.theme.badgeHeight
import com.kopim.productlist.ui.theme.badgeOuterPadding
import com.kopim.productlist.ui.theme.badgeTextPadding

@Composable
fun HintBadge(
    hint: Hint,
    modifier: Modifier = Modifier
) {
    Box(
        Modifier
            .padding(badgeOuterPadding)
//            .height(badgeHeight)
            .background(MaterialTheme.colorScheme.primaryContainer, MaterialTheme.shapes.medium)
            .then(modifier)
    ) {
        Text(
            text = hint.name,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.padding(
                badgeTextPadding
            )
        )
    }
}