package com.kopim.productlist.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.kopim.productlist.R
import com.kopim.productlist.data.mvvm.NavCommand
import com.kopim.productlist.data.mvvm.editlist.EditListViewModel
import com.kopim.productlist.ui.components.BackNavigationButton
import com.kopim.productlist.ui.components.ScreenTitle
import com.kopim.productlist.ui.theme.ProductsTheme
import com.kopim.productlist.ui.theme.defaultHorizontalEdgePadding
import com.kopim.productlist.ui.theme.defaultVerticalEdgePadding
import com.kopim.productlist.ui.theme.screenSectionSpacing
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun EditListScreen(
    listId: Long,
    vm: EditListViewModel = koinViewModel(parameters = { parametersOf(listId) }),
) {
    val state by vm.state.collectAsState()
    val navigator = LocalNavigator.currentOrThrow

    LaunchedEffect(Unit) {
        vm.navCommands.collect { cmd ->
            when (cmd) {
                is NavCommand.Push -> navigator.push(cmd.screen)
                is NavCommand.Pop -> navigator.pop()
                is NavCommand.PopMultiple -> repeat(cmd.count) { navigator.pop() }
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .imePadding()
                .padding(vertical = defaultVerticalEdgePadding)
        ) {
            ScreenTitle(
                text = stringResource(R.string.edit_list_screen_title),
                navigationIcon = {
                    BackNavigationButton(
                        onClick = { navigator.pop() },
                        contentDescription = stringResource(R.string.content_desc_back)
                    )
                }
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = defaultHorizontalEdgePadding),
                verticalArrangement = Arrangement.spacedBy(screenSectionSpacing)
            ) {
                OutlinedTextField(
                    value = state.listTitle,
                    onValueChange = vm::onListTitleChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(stringResource(R.string.edit_list_name_label)) },
                    singleLine = true,
                    enabled = !state.isLoading,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words
                    )
                )
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = stringResource(R.string.edit_list_code_label),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = state.shareCode.ifBlank { stringResource(R.string.edit_list_code_placeholder) },
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                state.errorMessage?.let { msg ->
                    Text(
                        text = msg,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Spacer(modifier = Modifier.height(screenSectionSpacing))
                TextButton(
                    onClick = vm::onLeaveList,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !state.isLoading,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text(stringResource(R.string.edit_list_leave))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EditListScreenPreview() {
    ProductsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .imePadding()
            ) {
                ScreenTitle(
                    text = stringResource(R.string.edit_list_screen_title),
                    navigationIcon = {
                        BackNavigationButton(
                            onClick = {},
                            contentDescription = stringResource(R.string.content_desc_back)
                        )
                    }
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = defaultHorizontalEdgePadding),
                    verticalArrangement = Arrangement.spacedBy(screenSectionSpacing)
                ) {
                    OutlinedTextField(
                        value = "Список на неделю",
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(stringResource(R.string.edit_list_name_label)) },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words
                        )
                    )
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = stringResource(R.string.edit_list_code_label),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "QWERTY",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    TextButton(
                        onClick = {},
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Text(stringResource(R.string.edit_list_leave))
                    }
                }
            }
        }
    }
}
