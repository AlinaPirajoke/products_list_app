package com.kopim.productlist.ui.components

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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kopim.productlist.R
import com.kopim.productlist.data.utils.ProductUiData
import com.kopim.productlist.ui.theme.ProductsTheme
import com.kopim.productlist.ui.theme.defaultPadding
import com.kopim.productlist.ui.theme.iconButtonSize
import com.kopim.productlist.ui.theme.lightBlue
import com.kopim.productlist.ui.theme.listCardPadding
import com.kopim.productlist.ui.theme.okGreen
import com.kopim.productlist.ui.theme.paleBlue
import com.kopim.productlist.ui.theme.productTileFieldPadding
import com.kopim.productlist.ui.theme.thinPadding
import kotlinx.coroutines.delay

private const val BUTTONS_QUANTITY = 2
private val buttonsWith = (iconButtonSize + thinPadding) * BUTTONS_QUANTITY + defaultPadding

@Composable
fun ProductTile(
    data: ProductUiData,
    onPick: () -> Unit,
    onDelete: () -> Unit,
    onEdit: () -> Unit,
    onTextChange: (TextFieldValue) -> Unit,
    onChangeConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    var tileHeight by remember { mutableStateOf(0.dp) }
    var tileWidth by remember { mutableStateOf(0.dp) }
    var textWidth by remember { mutableStateOf(0.dp) }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    var textOverflowed by remember(
        tileWidth,
        textWidth
    ) { mutableStateOf(tileWidth - textWidth < buttonsWith) }

    val showButtons = remember(!data.isEditing, data.picked) { data.picked && !data.isEditing }
    val buttonsAtSide = remember(textOverflowed, showButtons) { !textOverflowed && showButtons }
    val buttonsAtBot = remember(textOverflowed, showButtons) { textOverflowed && showButtons }

    val buttons = @Composable {
        Row(
            Modifier
                .height(iconButtonSize)
        ) {
            Spacer(Modifier.width(thinPadding))
            SquareIconButton(onEdit) {
                Icon(
                    painter = painterResource(R.drawable.edit),
                    contentDescription = null,
                    Modifier.fillMaxSize(),
                    tint = lightBlue
                )
            }
            Spacer(Modifier.width(thinPadding))
            SquareIconButton({ onDelete() }) {
                Icon(
                    painter = painterResource(R.drawable.done2),
                    contentDescription = null,
                    Modifier.fillMaxSize(),
                    tint = okGreen
                )
            }
            Spacer(Modifier.width(thinPadding))
        }
    }

    ElevatingTile(
        Modifier
            .fillMaxWidth(),
        picked = data.picked,
        onPick = onPick,
        onLongPress = { onDelete() }
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
                VerticalDivider(
                    Modifier.height(tileHeight + defaultPadding * 2),
                    thickness = 3.dp,
                    color = data.creatorColor
                )
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
                        if (data.isEditing) {
                            ProductTileTextField(
                                text = data.fieldText,
                                onEdit = onTextChange,
                                onConfirm = onChangeConfirm,
                                modifier = Modifier
                                    .padding(
                                        horizontal = productTileFieldPadding
                                    )
                                    .weight(1f),
                                focusRequester = focusRequester
                            )
                            SquareIconButton(
                                onClick = onChangeConfirm,
                                modifier = Modifier.padding(horizontal = thinPadding)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.edit),
                                    contentDescription = null,
                                    Modifier.fillMaxSize(),
                                    tint = lightBlue
                                )
                            }
                        } else {
                            Text(
                                text = data.text,
                                modifier = Modifier
                                    .padding(horizontal = thinPadding)
                                    .onGloballyPositioned { cords ->
                                        with(density) {
                                            textWidth = cords.size.width.toDp()
                                        }
                                    },
                                textDecoration = if (data.lineTrough) TextDecoration.LineThrough else null,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        if (buttonsAtSide) {
                            buttons()
                        }
                    }
                    if (buttonsAtBot) {
                        Spacer(Modifier.height(thinPadding))
                        Row(
                            Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            buttons()
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(data.isEditing) {
        if (data.isEditing) {
            delay(25)
            focusRequester.requestFocus()
            keyboardController?.show()
        } else {
            focusManager.clearFocus()
            keyboardController?.hide()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductTilePreview() {
    ProductsTheme {
        var productPicked by remember { mutableStateOf(false) }
        Surface(color = paleBlue) {
            Column(modifier = Modifier.padding(defaultPadding)) {
                ProductTile(
                    ProductUiData(1, Color(0xFF81D4FA), "Продукт 1", false, productPicked),
                    { productPicked = !productPicked },
                    {},
                    {},
                    {},
                    {}
                )
                Spacer(Modifier.height(listCardPadding))
                ProductTile(
                    ProductUiData(2, Color(0xFFC194FA), "Продукт 2", true, false),
                    {},
                    {},
                    {},
                    {},
                    {}
                )
                Spacer(Modifier.height(listCardPadding))
                ProductTile(
                    ProductUiData(
                        3,
                        Color(0xFF81D4FA),
                        "Супер пупер мега крутой продукт 3",
                        false,
                        true,
                        true,
                        TextFieldValue("Hello World!!!")
                    ), {}, {}, {}, {}, {}
                )
                Spacer(Modifier.height(listCardPadding))
                ProductTile(
                    ProductUiData(4, Color(0xFF81D4FA), "Продукт 4", false, true),
                    {},
                    {},
                    {},
                    {},
                    {}
                )
            }
        }
    }
}
