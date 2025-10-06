package com.kopim.productlist.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kopim.productlist.R
import com.kopim.productlist.data.utils.LocalChange
import com.kopim.productlist.data.utils.ProductUiData
import com.kopim.productlist.ui.theme.ProductsTheme
import com.kopim.productlist.ui.theme.defaultPadding
import com.kopim.productlist.ui.theme.iconButtonSize
import com.kopim.productlist.ui.theme.lightBlue
import com.kopim.productlist.ui.theme.listCardPadding
import com.kopim.productlist.ui.theme.okGreen
import com.kopim.productlist.ui.theme.paleBlue
import com.kopim.productlist.ui.theme.textBlack
import com.kopim.productlist.ui.theme.thinPadding

private const val BUTTONS_QUANTITY = 2
private val buttonsWith = (iconButtonSize + thinPadding) * BUTTONS_QUANTITY + defaultPadding

@Composable
fun LocalProductTile(
    data: LocalChange.AdditionChange,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    var tileHeight by remember { mutableStateOf(0.dp) }
    var tileWidth by remember { mutableStateOf(0.dp) }
    var textWidth by remember { mutableStateOf(0.dp) }

    ElevatingTile(
        Modifier
            .fillMaxWidth(),
        picked = false,
        onPick = {},
        onLongPress = {}
    ) {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            shape = MaterialTheme.shapes.medium,
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .then(modifier),
            ) {
                Column(
                    modifier
                        .padding(vertical = defaultPadding)
                        .onGloballyPositioned { coords ->
                            with(density) {
                                tileHeight = coords.size.height.toDp()
                                tileWidth = coords.size.width.toDp()
                            }
                        }
                ) {
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = data.name,
                            Modifier
                                .padding(horizontal = thinPadding)
                                .onGloballyPositioned { coords ->
                                    with(density) {
                                        textWidth = coords.size.width.toDp()
                                    }
                                },
                            color = textBlack
                        )
                    }
                }
            }
        }
    }
}
