package com.kopim.productlist.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.kopim.productlist.ui.theme.ProductsTheme
import com.kopim.productlist.ui.theme.inputFieldBottomPadding
import com.kopim.productlist.ui.theme.inputFieldEndPadding
import com.kopim.productlist.ui.theme.inputFieldHeight
import com.kopim.productlist.ui.theme.inputFieldStartPadding
import com.kopim.productlist.ui.theme.inputFieldUnderline
import com.kopim.productlist.ui.theme.inputFieldWidth

private val fieldBackground =
    Brush.verticalGradient(0.05f to Color.Transparent, 1f to Color.White)

@Composable
fun InputField(
    text: TextFieldValue,
    onEdit: (TextFieldValue) -> Unit,
    onConfirm: () -> Unit,
    boxModifier: Modifier = Modifier,
    fieldModifier: Modifier = Modifier
) {
    BasicTextField(
        value = text,
        onValueChange = onEdit,
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Text, capitalization = KeyboardCapitalization.Sentences),
        keyboardActions = KeyboardActions(onDone = { onConfirm() }),
        modifier = fieldModifier,
        textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primaryContainer),
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primaryContainer),
    ) { field ->
        Column(
            modifier = Modifier
                .height(inputFieldHeight)
                .widthIn(min = inputFieldWidth)
                .then(boxModifier),
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(fieldBackground),
                contentAlignment = Alignment.BottomStart
            ) {
                Box(
                    Modifier.padding(
                        start = inputFieldStartPadding,
                        end = inputFieldEndPadding,
                        bottom = inputFieldBottomPadding
                    )
                ) {
                    field()
                }
            }
            HorizontalDivider(
                Modifier.fillMaxWidth(),
                thickness = inputFieldUnderline,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview
@Composable
fun InputFieldPreview() {
    ProductsTheme {
        InputField(
            text = TextFieldValue("Hello world!"),
            onEdit = {},
            onConfirm = {}
        )
    }
}