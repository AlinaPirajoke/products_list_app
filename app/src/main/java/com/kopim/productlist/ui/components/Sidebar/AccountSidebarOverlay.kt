package com.kopim.productlist.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
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
import com.kopim.productlist.ui.theme.defaultHorizontalEdgePadding

@Composable
fun AccountSidebarOverlay(
    visible: Boolean,
    state: AccountSidebarState,
    onDismiss: () -> Unit,
    onNameValueClick: () -> Unit,
    onNameLabelClick: () -> Unit,
    onNameDraftChange: (String) -> Unit,
    onPasswordMaskClick: () -> Unit,
    onPasswordLabelClick: () -> Unit,
    onPasswordDraftChange: (String) -> Unit,
    onSubmitChanges: () -> Unit,
    onColorChangeClick: () -> Unit,
    onSwitchProfileClick: () -> Unit,
) {
    AnimatedVisibility(
        visible = visible,
        modifier = Modifier.fillMaxSize(),
        enter = fadeIn(fadeTween),
        exit = fadeOut(fadeTween),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.scrim.copy(alpha = 0.15f))
                    .clickable(onClick = onDismiss),
            )
            AnimatedVisibility(
                visible = true,
                enter = slideInHorizontally { -it } + fadeIn(fadeTween),
                exit = slideOutHorizontally { -it } + fadeOut(fadeTween),
                modifier = Modifier.align(Alignment.CenterStart),
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxHeight()
                        .widthIn(max = 320.dp),
                    color = Color.White,
                    tonalElevation = 0.dp,
                    shadowElevation = 6.dp,
                ) {
                    AccountSidebarContent(
                        state = state,
                        onNameValueClick = onNameValueClick,
                        onNameLabelClick = onNameLabelClick,
                        onNameDraftChange = onNameDraftChange,
                        onPasswordMaskClick = onPasswordMaskClick,
                        onPasswordLabelClick = onPasswordLabelClick,
                        onPasswordDraftChange = onPasswordDraftChange,
                        onSubmitChanges = onSubmitChanges,
                        onColorChangeClick = onColorChangeClick,
                        onSwitchProfileClick = onSwitchProfileClick,
                    )
                }
            }
        }
    }
}

@Composable
private fun AccountSidebarContent(
    state: AccountSidebarState,
    onNameValueClick: () -> Unit,
    onNameLabelClick: () -> Unit,
    onNameDraftChange: (String) -> Unit,
    onPasswordMaskClick: () -> Unit,
    onPasswordLabelClick: () -> Unit,
    onPasswordDraftChange: (String) -> Unit,
    onSubmitChanges: () -> Unit,
    onColorChangeClick: () -> Unit,
    onSwitchProfileClick: () -> Unit,
) {
    val profileColor = state.profileColor.toProfileComposeColor()
    val labelStyle = sidebarTextStyle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = defaultHorizontalEdgePadding)
            .padding(top = 16.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AccountSidebarHeader(
                userId = state.userId,
                labelStyle = labelStyle,
            )
            NameSection(
                state = state,
                labelStyle = labelStyle,
                onNameValueClick = onNameValueClick,
                onNameLabelClick = onNameLabelClick,
                onNameDraftChange = onNameDraftChange,
            )
            sidebarDivider()
            PasswordSection(
                state = state,
                labelStyle = labelStyle,
                onPasswordMaskClick = onPasswordMaskClick,
                onPasswordLabelClick = onPasswordLabelClick,
                onPasswordDraftChange = onPasswordDraftChange,
            )
            sidebarDivider()
            ConfirmChangesAction(
                visible = state.anyFieldActive,
                onSubmitChanges = onSubmitChanges,
            )
            Spacer(Modifier.height(14.dp))
            if (state.anyFieldActive) Spacer(Modifier.height(30.dp))
            ProfileColorSection(
                profileColor = profileColor,
                labelStyle = labelStyle,
                onColorChangeClick = onColorChangeClick,
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Spacer(Modifier.height(16.dp))
            SwitchProfileButton(onSwitchProfileClick = onSwitchProfileClick)
        }
    }
}
