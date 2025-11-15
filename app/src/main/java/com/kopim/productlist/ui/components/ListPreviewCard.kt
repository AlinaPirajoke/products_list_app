package com.kopim.productlist.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.pluralStringResource
import com.kopim.productlist.R
import com.kopim.productlist.data.utils.ShortCartData
import com.kopim.productlist.ui.theme.listPreviewCardInnerPadding
import com.kopim.productlist.ui.theme.listPreviewCardInterElementSpace
import com.kopim.productlist.ui.theme.listPreviewCardMaxHeight
import com.kopim.productlist.ui.theme.listPreviewCardWidth
import kotlin.math.min

private const val MAX_LIST_LENGTH = 7

@Composable
fun ListPreviewCard(
    model: ShortCartData,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    val viewableListLength = remember(model) { min(model.items.size, MAX_LIST_LENGTH) }
    val unseenElementsQuantity = remember(model) { model.items.size - viewableListLength }
    val listWasTrimmed = remember(viewableListLength) { unseenElementsQuantity > 0 }

    Surface(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .clip(MaterialTheme.shapes.medium)
            .heightIn(max = listPreviewCardMaxHeight)
            .width(listPreviewCardWidth)
            .then(modifier)
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier
                .fillMaxSize()
                .padding(listPreviewCardInnerPadding)
        ) {
            for (i in 0..viewableListLength) {
                Text(text = model.items[i].name, style = MaterialTheme.typography.labelSmall)
                if (i != viewableListLength)
                    Spacer(
                        Modifier.height(listPreviewCardInterElementSpace)
                    )
            }
            if (listWasTrimmed) {
                Spacer(Modifier.height(listPreviewCardInterElementSpace))
                Text(
                    text = pluralStringResource(
                        R.plurals.trimmed_list_caption,
                        unseenElementsQuantity,
                        unseenElementsQuantity
                    ), style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}