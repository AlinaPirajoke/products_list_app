package com.kopim.productlist.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.kopim.productlist.R
import com.kopim.productlist.data.mvvm.homefeed.HomeFeedViewModel
import com.kopim.productlist.ui.components.ScreenTitle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeFeedScreen(
    contentPadding: PaddingValues,
    vm: HomeFeedViewModel = koinViewModel(),
) {
    val listState = rememberLazyListState()
    val state by vm.state.collectAsState()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            ScreenTitle(stringResource(if (state.lists.isEmpty()) R.string.empty_home_feed_label else R.string.default_home_feed_label))
        }

        LazyColumn(state = listState) {
            // ....
        }
    }
}