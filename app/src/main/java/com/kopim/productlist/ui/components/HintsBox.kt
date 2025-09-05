package com.kopim.productlist.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kopim.productlist.data.utils.Hint

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HintsBox(
    hints: List<Hint>,
    onPick: (Hint) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        FlowRow(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.Center
        ) {
            for (hint in hints) {
                HintBadge(hint, Modifier.clickable { onPick(hint) })
            }
        }
    }
}