package com.kopim.productlist.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.kopim.productlist.R
import com.kopim.productlist.ui.theme.dirtyWhite
import com.kopim.productlist.ui.theme.fabSize

@Composable
fun DefaultFab(onClick: () -> Unit, icon: @Composable () -> Unit) {
    FloatingActionButton(onClick = onClick, modifier = Modifier.size(fabSize), containerColor = dirtyWhite) {
        icon()
    }
}