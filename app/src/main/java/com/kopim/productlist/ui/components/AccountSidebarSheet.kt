package com.kopim.productlist.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kopim.productlist.R
import com.kopim.productlist.data.mvvm.homefeed.AccountSidebarState
import com.kopim.productlist.ui.theme.Purple80
import com.kopim.productlist.ui.theme.deepBlue
import com.kopim.productlist.ui.theme.lightBlue
import com.kopim.productlist.ui.theme.okGreen
import com.kopim.productlist.ui.theme.Pink40
import com.kopim.productlist.ui.theme.warningRed

private val accountProfileColors: List<Color>
    @Composable
    get() = listOf(
        lightBlue,
        deepBlue,
        okGreen,
        warningRed,
        Purple80,
        Pink40,
    )

@Composable
fun AccountProfileColor(index: Int): Color {
    val list = accountProfileColors
    return list[index.coerceIn(0, list.lastIndex)]
}

@Composable
fun AccountSidebarOverlay(
    visible: Boolean,
    state: AccountSidebarState,
    onDismiss: () -> Unit,
    onNameClick: () -> Unit,
    onNameDraftChange: (String) -> Unit,
    onPasswordLabelClick: () -> Unit,
    onPasswordDraftChange: (String) -> Unit,
    onSubmitChanges: () -> Unit,
    onColorChangeClick: () -> Unit,
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.scrim.copy(alpha = 0.45f))
                    .clickable(onClick = onDismiss),
            )
            Surface(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .fillMaxHeight()
                    .widthIn(max = 320.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 3.dp,
                shadowElevation = 6.dp,
            ) {
                AccountSidebarContent(
                    state = state,
                    onNameClick = onNameClick,
                    onNameDraftChange = onNameDraftChange,
                    onPasswordLabelClick = onPasswordLabelClick,
                    onPasswordDraftChange = onPasswordDraftChange,
                    onSubmitChanges = onSubmitChanges,
                    onColorChangeClick = onColorChangeClick,
                )
            }
        }
    }
}

@Composable
private fun AccountSidebarContent(
    state: AccountSidebarState,
    onNameClick: () -> Unit,
    onNameDraftChange: (String) -> Unit,
    onPasswordLabelClick: () -> Unit,
    onPasswordDraftChange: (String) -> Unit,
    onSubmitChanges: () -> Unit,
    onColorChangeClick: () -> Unit,
) {
    val profileColor = AccountProfileColor(state.profileColorIndex)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.account_user_id_format, state.userId),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.height(16.dp))
        if (state.isNameEditing) {
            OutlinedTextField(
                value = state.nameDraft,
                onValueChange = onNameDraftChange,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center),
            )
        } else {
            Text(
                text = state.displayName,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onNameClick),
            )
        }
        Spacer(Modifier.height(16.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = stringResource(R.string.account_password_label),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onPasswordLabelClick),
            )
            if (state.isPasswordEditing) {
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = state.passwordDraft,
                    onValueChange = onPasswordDraftChange,
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center),
                )
            } else if (state.isPasswordRevealed) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = state.password,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = onPasswordLabelClick),
                )
            }
        }
        if (state.anyFieldActive) {
            Spacer(Modifier.height(20.dp))
            Button(onClick = onSubmitChanges) {
                Text(stringResource(R.string.account_submit_changes))
            }
        }
        Spacer(Modifier.height(28.dp))
        Box(
            modifier = Modifier
                .size(72.dp)
                .background(profileColor, MaterialTheme.shapes.medium),
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = stringResource(R.string.account_want_other_color),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onColorChangeClick),
        )
    }
}
