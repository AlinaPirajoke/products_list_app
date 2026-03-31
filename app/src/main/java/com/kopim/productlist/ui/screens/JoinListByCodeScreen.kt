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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.kopim.productlist.R
import com.kopim.productlist.data.mvvm.NavCommand
import com.kopim.productlist.data.mvvm.joinlist.JoinListByCodeViewModel
import com.kopim.productlist.ui.components.BackNavigationButton
import com.kopim.productlist.ui.components.ScreenTitle
import com.kopim.productlist.ui.theme.defaultHorizontalEdgePadding
import com.kopim.productlist.ui.theme.defaultVerticalEdgePadding
import com.kopim.productlist.ui.theme.screenSectionSpacing
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun JoinListByCodeScreen(
    vm: JoinListByCodeViewModel = koinViewModel(),
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
                .padding(vertical = defaultVerticalEdgePadding),
        ) {
            ScreenTitle(
                text = stringResource(R.string.join_list_screen_title),
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
                    value = state.joinCode,
                    onValueChange = vm::onJoinCodeChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(stringResource(R.string.join_list_code_label)) },
                    supportingText = { Text(stringResource(R.string.join_list_code_supporting)) },
                    singleLine = true,
                    enabled = !state.isLoading,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = { vm.onSubmit() }),
                    isError = state.errorMessage != null
                )
                state.errorMessage?.let { msg ->
                    Text(
                        text = msg,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Button(
                    onClick = vm::onSubmit,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !state.isLoading
                ) {
                    Text(stringResource(R.string.join_list_submit))
                }
                Spacer(modifier = Modifier.height(screenSectionSpacing))
            }
        }
    }
}
