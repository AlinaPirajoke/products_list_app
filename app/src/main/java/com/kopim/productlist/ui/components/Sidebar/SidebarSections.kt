package com.kopim.productlist.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kopim.productlist.R
import com.kopim.productlist.data.mvvm.homefeed.AccountSidebarState
import com.kopim.productlist.ui.theme.textBlack
import com.kopim.productlist.ui.theme.warningRed

@Composable
internal fun AccountSidebarHeader(userId: String, labelStyle: TextStyle) {
    Text(
        text = stringResource(R.string.account_sidebar_title),
        style = sidebarTitleStyle(),
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
    )
    sidebarDivider()
    Text(
        text = stringResource(R.string.account_user_id_format, userId),
        style = labelStyle,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
    )
    sidebarDivider()
}

@Composable
internal fun NameSection(
    state: AccountSidebarState,
    labelStyle: TextStyle,
    onNameValueClick: () -> Unit,
    onNameLabelClick: () -> Unit,
    onNameDraftChange: (String) -> Unit,
) {
    AnimatedContent(
        targetState = state.isNameEditing,
        transitionSpec = {
            (slideInVertically { h -> h } + fadeIn(fadeTween)) togetherWith
                (slideOutVertically { h -> -h } + fadeOut(fadeTween))
        },
        modifier = Modifier.fillMaxWidth(),
    ) { editing ->
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            if (editing) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = stringResource(R.string.account_name_label),
                        style = labelStyle,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.sidebarClickable(onNameLabelClick),
                    )
                    OutlinedTextField(
                        value = state.nameDraft,
                        onValueChange = onNameDraftChange,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        singleLine = true,
                        textStyle = MaterialTheme.typography.bodyMedium.copy(
                            color = textBlack,
                            textAlign = TextAlign.Center,
                        ),
                        colors = accountFieldColors(),
                    )
                }
            } else {
                if (state.displayName.isBlank()) {
                    Text(
                        text = stringResource(R.string.account_name_tap_to_set),
                        style = labelStyle,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .sidebarClickable(onNameValueClick),
                    )
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = stringResource(R.string.account_name_label) + ": ",
                            style = labelStyle,
                        )
                        Text(
                            text = state.displayName,
                            style = labelStyle,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.sidebarClickable(onNameValueClick),
                        )
                    }
                }
            }
        }
    }
}

@Composable
internal fun PasswordSection(
    state: AccountSidebarState,
    labelStyle: TextStyle,
    onPasswordMaskClick: () -> Unit,
    onPasswordLabelClick: () -> Unit,
    onPasswordDraftChange: (String) -> Unit,
) {
    AnimatedContent(
        targetState = state.isPasswordEditing,
        transitionSpec = {
            (slideInVertically { h -> h } + fadeIn(fadeTween)) togetherWith
                (slideOutVertically { h -> -h } + fadeOut(fadeTween))
        },
        modifier = Modifier.fillMaxWidth(),
    ) { editing ->
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            if (editing) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = stringResource(R.string.account_password_label),
                        style = labelStyle,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.sidebarClickable(onPasswordLabelClick),
                    )
                    OutlinedTextField(
                        value = state.passwordDraft,
                        onValueChange = onPasswordDraftChange,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        singleLine = true,
                        visualTransformation = VisualTransformation.None,
                        textStyle = MaterialTheme.typography.bodyMedium.copy(
                            color = textBlack,
                            textAlign = TextAlign.Center,
                        ),
                        colors = accountFieldColors(),
                    )
                }
            } else {
                if (!state.passwordSessionKnown) {
                    Text(
                        text = stringResource(R.string.account_password_tap_to_set),
                        style = labelStyle,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .sidebarClickable(onPasswordMaskClick),
                    )
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = stringResource(R.string.account_password_label) + ": ",
                            style = labelStyle,
                        )
                        PasswordMask(
                            onClick = onPasswordMaskClick,
                            dotCount = state.password.length.takeIf { it > 0 } ?: 6,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PasswordMask(
    onClick: () -> Unit,
    dotCount: Int = 6,
) {
    val n = dotCount.coerceIn(1, 64)
    Row(
        modifier = Modifier.sidebarClickable(onClick),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        repeat(n) {
            Box(
                modifier = Modifier
                    .size(7.dp)
                    .background(textBlack, CircleShape),
            )
        }
    }
}

@Composable
internal fun ConfirmChangesAction(
    visible: Boolean,
    onSubmitChanges: () -> Unit,
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically { it } + fadeIn(fadeTween) + expandVertically(),
        exit = slideOutVertically { it } + fadeOut(fadeTween) + shrinkVertically(),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Button(
                onClick = onSubmitChanges,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = textBlack,
                ),
            ) {
                Text(
                    stringResource(R.string.account_confirm_changes),
                    color = textBlack,
                )
            }
        }
    }
}

@Composable
internal fun ProfileColorSection(
    profileColor: Color,
    labelStyle: TextStyle,
    onColorChangeClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(72.dp)
            .background(profileColor, MaterialTheme.shapes.medium),
    )
    Spacer(Modifier.height(12.dp))
    Text(
        text = stringResource(R.string.account_want_other_color),
        style = labelStyle,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .sidebarClickable(onColorChangeClick),
    )
}

@Composable
internal fun SwitchProfileButton(onSwitchProfileClick: () -> Unit) {
    Button(
        onClick = onSwitchProfileClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = warningRed,
            contentColor = textBlack,
        ),
    ) {
        Text(text = stringResource(R.string.account_switch_profile))
    }
}

@Composable
internal fun sidebarDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(vertical = 14.dp),
        color = MaterialTheme.colorScheme.outlineVariant,
    )
}
