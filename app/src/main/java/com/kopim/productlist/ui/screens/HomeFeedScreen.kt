package com.kopim.productlist.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.kopim.productlist.R
import com.kopim.productlist.data.mvvm.NavCommand
import com.kopim.productlist.data.mvvm.homefeed.HomeFeedViewModel
import com.kopim.productlist.data.utils.ShortCartData
import com.kopim.productlist.ui.components.AccountSidebarOverlay
import com.kopim.productlist.ui.components.AddNewListButton
import com.kopim.productlist.ui.components.DefaultFab
import com.kopim.productlist.ui.components.ListPreviewCard
import com.kopim.productlist.ui.components.ScreenTitle
import com.kopim.productlist.ui.navigation.HomeFeedNavPoint
import com.kopim.productlist.ui.navigation.JoinListByCodeNavPoint
import com.kopim.productlist.ui.navigation.LoginAccountNavPoint
import com.kopim.productlist.ui.theme.ProductsTheme
import com.kopim.productlist.ui.theme.defaultHorizontalEdgePadding
import com.kopim.productlist.ui.theme.homeFeedAddListSlotHeight
import com.kopim.productlist.ui.theme.listPreviewCardOuterPadding
import com.kopim.productlist.ui.theme.thinPadding
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeFeedScreen(
    vm: HomeFeedViewModel = koinViewModel(),
) {
    val listState = rememberLazyListState()
    val state by vm.state.collectAsState()
    val navigator = LocalNavigator.currentOrThrow
    var accountDrawerOpen by rememberSaveable { mutableStateOf(false) }

    val showAddAsFab by remember {
        derivedStateOf {
            listState.canScrollForward || listState.canScrollBackward
        }
    }

    val onAddList: () -> Unit = { navigator.push(JoinListByCodeNavPoint) }

    LaunchedEffect(Unit) {
        vm.navCommands.collect { cmd ->
            when (cmd) {
                is NavCommand.Push -> navigator.push(cmd.screen)
                is NavCommand.Pop -> navigator.pop()
                is NavCommand.PopMultiple -> repeat(cmd.count) { navigator.pop() }
            }
        }
    }

    LaunchedEffect(navigator.lastItem) {
        if (navigator.lastItem is HomeFeedNavPoint) {
            vm.onHomeFeedBecameTop()
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        floatingActionButton = {
            if (showAddAsFab) {
                DefaultFab(onClick = onAddList) {
                    Icon(
                        painter = painterResource(R.drawable.add),
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(thinPadding).fillMaxSize(),
                        contentDescription = stringResource(R.string.content_desc_fab_add_list_by_code)
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(innerPadding)
        ) {
            Box(Modifier.fillMaxSize()) {
                Column(modifier = Modifier.fillMaxSize()) {
                    ScreenTitle(
                        text = stringResource(
                            if (state.lists.isEmpty()) R.string.empty_home_feed_label
                            else R.string.default_home_feed_label
                        ),
                        actions = {
                            IconButton(onClick = { accountDrawerOpen = true }) {
                                Icon(
                                    imageVector = Icons.Filled.MoreVert,
                                    contentDescription = stringResource(R.string.content_desc_open_account_menu),
                                    tint = MaterialTheme.colorScheme.primaryContainer,
                                )
                            }
                        },
                    )
                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = defaultHorizontalEdgePadding),
                        contentPadding = PaddingValues(bottom = listPreviewCardOuterPadding),
                        verticalArrangement = Arrangement.spacedBy(listPreviewCardOuterPadding)
                    ) {
                        items(
                            items = state.lists,
                            key = { it.id }
                        ) { listData ->
                            ListPreviewCard(listData) { vm.onNavigateToList(listData.id) }
                        }
                        item(key = "add_list_footer") {
                            if (showAddAsFab) {
                                Spacer(Modifier.height(homeFeedAddListSlotHeight))
                            } else {
                                AddNewListButton(onClick = onAddList)
                            }
                        }
                    }
                }
                AccountSidebarOverlay(
                    visible = accountDrawerOpen,
                    state = state.account,
                    onDismiss = { accountDrawerOpen = false },
                    onNameValueClick = vm::onAccountNameValueClick,
                    onNameLabelClick = vm::onAccountNameLabelClick,
                    onNameDraftChange = vm::onAccountNameDraftChange,
                    onPasswordMaskClick = vm::onAccountPasswordMaskClick,
                    onPasswordLabelClick = vm::onAccountPasswordLabelClick,
                    onPasswordDraftChange = vm::onAccountPasswordDraftChange,
                    onSubmitChanges = vm::onAccountSubmitChanges,
                    onColorChangeClick = vm::onAccountColorChangeRequest,
                    onSwitchProfileClick = {
                            accountDrawerOpen = false
                            navigator.push(LoginAccountNavPoint)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeFeedScreenPreview() {
    val previewLists = listOf(
        ShortCartData(
            name = "Название списка",
            id = 1,
            items = listOf(
                ShortCartData.ShortListItemData("Продукт 1"),
                ShortCartData.ShortListItemData("Продукт 2"),
                ShortCartData.ShortListItemData("Продукт 3"),
                ShortCartData.ShortListItemData("Продукт 4"),
                ShortCartData.ShortListItemData("Продукт 5"),
                ShortCartData.ShortListItemData("Продукт 6"),
            )
        )
    )
    ProductsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                ScreenTitle(text = "Ваши списки:")
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = defaultHorizontalEdgePadding),
                    verticalArrangement = Arrangement.spacedBy(listPreviewCardOuterPadding)
                ) {
                    items(previewLists, key = { it.id }) { listData ->
                        ListPreviewCard(model = listData)
                    }
                    item {
                        AddNewListButton(onClick = {})
                    }
                }
            }
        }
    }
}
