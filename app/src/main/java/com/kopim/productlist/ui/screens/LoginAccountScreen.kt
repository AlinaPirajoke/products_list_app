package com.kopim.productlist.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.kopim.productlist.R
import com.kopim.productlist.data.mvvm.NavCommand
import com.kopim.productlist.data.mvvm.loginaccount.LoginAccountViewModel
import com.kopim.productlist.ui.components.BackNavigationButton
import com.kopim.productlist.ui.components.ScreenTitle
import com.kopim.productlist.ui.theme.defaultHorizontalEdgePadding
import com.kopim.productlist.ui.theme.screenSectionSpacing
import org.koin.compose.viewmodel.koinViewModel

private val loginFormMaxWidth = 400.dp
private val titleToFormSpacing = 8.dp
private val screenBottomPadding = 16.dp

@Composable
fun LoginAccountScreen(
    vm: LoginAccountViewModel = koinViewModel(),
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
        color = MaterialTheme.colorScheme.surface,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
                .imePadding()
                .padding(bottom = screenBottomPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ScreenTitle(
                text = stringResource(R.string.login_screen_title),
                navigationIcon = {
                    BackNavigationButton(
                        onClick = vm::onBackClick,
                        contentDescription = stringResource(R.string.content_desc_back),
                    )
                },
            )
            Column(
                modifier = Modifier
                    .widthIn(max = loginFormMaxWidth)
                    .fillMaxWidth()
                    .padding(horizontal = defaultHorizontalEdgePadding)
                    .padding(top = titleToFormSpacing),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(screenSectionSpacing),
            ) {
                OutlinedTextField(
                    value = state.userId,
                    onValueChange = vm::onUserIdChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(stringResource(R.string.login_id_label)) },
                    singleLine = true,
                    enabled = !state.isLoading,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                    ),
                    isError = state.errorMessage != null,
                )
                OutlinedTextField(
                    value = state.password,
                    onValueChange = vm::onPasswordChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(stringResource(R.string.login_password_label)) },
                    singleLine = true,
                    enabled = !state.isLoading,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(onDone = { vm.onSubmit() }),
                    isError = state.errorMessage != null,
                )
                state.errorMessage?.let { msg ->
                    Text(
                        text = msg,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
                Button(
                    onClick = vm::onSubmit,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !state.isLoading,
                ) {
                    Text(stringResource(R.string.login_submit))
                }
                Spacer(modifier = Modifier.height(screenSectionSpacing))
            }
        }
    }
}
