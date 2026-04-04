package com.kopim.productlist.ui.components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.kopim.productlist.data.model.profile.ProfileColorString
import com.kopim.productlist.ui.theme.textBlack

internal const val ANIM_MS = 240
internal val fadeTween = tween<Float>(durationMillis = ANIM_MS)

/** Строка цвета в [Color], как для `creatorColor` у позиций списка. */
internal fun String.toProfileComposeColor(): Color =
    try {
        Color(trim().toColorInt())
    } catch (_: IllegalArgumentException) {
        Color(ProfileColorString.DEFAULT.toColorInt())
    }

@Composable
internal fun accountFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = textBlack,
    unfocusedTextColor = textBlack,
    focusedContainerColor = Color.Transparent,
    unfocusedContainerColor = Color.Transparent,
    focusedBorderColor = MaterialTheme.colorScheme.outline,
    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
    cursorColor = textBlack,
    focusedLabelColor = textBlack,
    unfocusedLabelColor = textBlack,
)

@Composable
internal fun sidebarTextStyle(): TextStyle =
    MaterialTheme.typography.bodyMedium.copy(color = textBlack)

@Composable
internal fun sidebarTitleStyle(): TextStyle =
    MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onBackground)

@Composable
internal fun Modifier.sidebarClickable(onClick: () -> Unit): Modifier {
    val interactionSource = remember { MutableInteractionSource() }
    val shape = RoundedCornerShape(8.dp)
    return this
        .clip(shape)
        .clickable(
            interactionSource = interactionSource,
            indication = ripple(bounded = true),
            onClick = onClick,
        )
        .padding(horizontal = 10.dp, vertical = 6.dp)
}
