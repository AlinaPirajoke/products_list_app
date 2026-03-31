package com.kopim.productlist.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.kopim.productlist.R
import com.kopim.productlist.data.utils.ShortCartData
import com.kopim.productlist.ui.theme.ProductsTheme
import com.kopim.productlist.ui.theme.listPreviewCardInnerPadding
import com.kopim.productlist.ui.theme.listPreviewCardInterElementSpace
import com.kopim.productlist.ui.theme.listPreviewCardMaxHeight
import kotlin.math.min

private const val MAX_PREVIEW_ITEMS = 5

@Composable
fun ListPreviewCard(
    model: ShortCartData,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    val viewableListLength = remember(model.items.size) { min(model.items.size, MAX_PREVIEW_ITEMS) }
    val unseenElementsQuantity = model.items.size - viewableListLength
    val listWasTrimmed = unseenElementsQuantity > 0

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = listPreviewCardMaxHeight)
            .then(modifier)
            .clickable(onClick = onClick),
        color = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(listPreviewCardInnerPadding)
        ) {
            Text(
                text = model.name,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            if (model.items.isNotEmpty()) {
                Spacer(Modifier.height(listPreviewCardInterElementSpace))
            }
            for (i in 0 until viewableListLength) {
                Text(
                    text = model.items[i].name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                if (i != viewableListLength - 1) {
                    Spacer(Modifier.height(listPreviewCardInterElementSpace))
                }
            }
            if (listWasTrimmed) {
                Spacer(Modifier.height(listPreviewCardInterElementSpace))
                Text(
                    text = pluralStringResource(
                        R.plurals.trimmed_list_caption,
                        unseenElementsQuantity,
                        unseenElementsQuantity
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ListPreviewCardPreview() {
    ProductsTheme {
        ListPreviewCard(
            model = ShortCartData(
                name = "Список на неделю",
                id = 1L,
                items = listOf(
                    ShortCartData.ShortListItemData("Хлеб"),
                    ShortCartData.ShortListItemData("Молоко"),
                    ShortCartData.ShortListItemData("Яйца"),
                    ShortCartData.ShortListItemData("Сыр"),
                    ShortCartData.ShortListItemData("Фрукты"),
                    ShortCartData.ShortListItemData("Овощи"),
                )
            )
        )
    }
}
