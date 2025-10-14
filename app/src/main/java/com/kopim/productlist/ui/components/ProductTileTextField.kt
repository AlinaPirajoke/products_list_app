package com.kopim.productlist.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun ProductTileTextField(
    text: TextFieldValue,
    onEdit: (TextFieldValue) -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester = FocusRequester()
) {
    BasicTextField(
        value = text,
        onValueChange = onEdit,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text,
            capitalization = KeyboardCapitalization.Sentences
        ),
        keyboardActions = KeyboardActions(onDone = { onConfirm() }),
        modifier = Modifier.focusRequester(focusRequester),
        textStyle = MaterialTheme.typography.bodyMedium,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primaryContainer),
    ) { field ->
        Box(
            modifier
        ) {
            field()
        }
    }
}