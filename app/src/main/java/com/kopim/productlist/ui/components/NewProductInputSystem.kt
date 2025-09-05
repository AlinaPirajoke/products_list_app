package com.kopim.productlist.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.TextFieldValue
import com.kopim.productlist.data.utils.Hint
import com.kopim.productlist.ui.theme.hintBoxHeight
import com.kopim.productlist.ui.theme.hintBoxWidth
import kotlinx.coroutines.delay

@Composable
fun NewProductInputSystem(
    text: TextFieldValue,
    expanded: Boolean,
    onEdit: (TextFieldValue) -> Unit,
    onConfirm: (String) -> Unit,
    onHintPick: (Hint) -> Unit,
    onHide: () -> Unit,
    modifier: Modifier = Modifier,
    hints: List<Hint> = listOf(),
    content: @Composable () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Box(
        Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        content()

        if (expanded) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.DarkGray.copy(alpha = 0.35f))
                    .imePadding()
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .clickable(onClick = onHide, role = Role.Tab)
                ) {}

                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier)

                    HintsBox(
                        hints,
                        onPick = { onHintPick(it) },
                        Modifier.size(hintBoxWidth, hintBoxHeight)
                    )

                    InputBlock(
                        text = text,
                        onEdit = onEdit,
                        onConfirm = { onConfirm(text.text) },
                        modifier = modifier.fillMaxWidth(),
                        focusRequester = focusRequester
                    )
                }
            }
        }

        LaunchedEffect(expanded) {
            if (expanded){
                delay(25)
                focusRequester.requestFocus()
                keyboardController?.show()
            } else {
                focusManager.clearFocus()
                keyboardController?.hide()
            }
        }
    }
}