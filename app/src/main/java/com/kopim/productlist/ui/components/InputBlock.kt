package com.kopim.productlist.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.kopim.productlist.R
import com.kopim.productlist.ui.theme.ProductsTheme
import com.kopim.productlist.ui.theme.inputBlockButtonHeight
import com.kopim.productlist.ui.theme.inputBlockButtonWidth

@Composable
fun InputBlock(
    text: TextFieldValue,
    onEdit: (TextFieldValue) -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester
) {
    Row(
        Modifier
            .fillMaxWidth()
            .then(modifier),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        InputField(
            text = text,
            onEdit = onEdit,
            onConfirm = onConfirm,
            boxModifier = Modifier.weight(9f),
            fieldModifier = Modifier.focusRequester(focusRequester)
        )
        SquareIconButton(
            onClick = onConfirm,
            modifier = Modifier.size(
                inputBlockButtonWidth,
                inputBlockButtonHeight
            ),
            background = MaterialTheme.colorScheme.primary
        ) {
            Icon(
                painter = painterResource(R.drawable.add),
                contentDescription = null,
                Modifier.fillMaxSize(),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Preview
@Composable
fun InputBlockPreview() {
    ProductsTheme {
        InputBlock(
            text = TextFieldValue("Hello world!"),
            onEdit = {},
            onConfirm = {},
            focusRequester = FocusRequester()
        )
    }
}