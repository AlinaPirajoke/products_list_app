package com.kopim.productlist.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.kopim.productlist.R
import com.kopim.productlist.data.mvvm.NavCommand
import com.kopim.productlist.data.mvvm.list.ListViewModel
import com.kopim.productlist.data.utils.ListScreenMode
import com.kopim.productlist.ui.components.DefaultFab
import com.kopim.productlist.ui.components.LocalProductTile
import com.kopim.productlist.ui.components.NewProductInputSystem
import com.kopim.productlist.ui.components.ProductTile
import com.kopim.productlist.ui.components.ScreenTitle
import com.kopim.productlist.ui.navigation.EditListNavPoint
import com.kopim.productlist.ui.theme.cartListSpacing
import com.kopim.productlist.ui.theme.defaultHorizontalEdgePadding
import com.kopim.productlist.ui.theme.thinPadding
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CartScreen(
    listId: Long,
    vm: ListViewModel = koinViewModel(),
) {
    val listState = rememberLazyListState()
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

    DisposableEffect(listId) {
        vm.unsubscribeOnList()
        vm.setListId(listId)
        vm.subscribeOnList()
        onDispose {
            vm.unsubscribeOnList()
        }
    }

    NewProductInputSystem(
        text = state.newProductFieldValue,
        hints = state.newProductHints,
        expanded = state.screenMode is ListScreenMode.AdditionMode,
        onEdit = vm::onNewProductFieldValueChange,
        onConfirm = vm::onNewProductConfirm,
        onHide = vm::enterCartMode,
        onHintPick = vm::onHintPick,
    ) {
        Scaffold(
            floatingActionButton = {
                DefaultFab(onClick = vm::enterAdditionMode) {
                    Icon(
                        painter = painterResource(R.drawable.add),
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(thinPadding).fillMaxSize(),
                        contentDescription = stringResource(R.string.content_desc_fab_add_product),
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.End
        ) { innerPaddingValues ->
            Surface(color = MaterialTheme.colorScheme.background) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPaddingValues)
                ) {
                    val defaultTitle = stringResource(R.string.default_list_label)
                    ScreenTitle(
                        text = state.listTitle?.takeIf { it.isNotBlank() } ?: defaultTitle,
                        actions = {
                            IconButton(
                                onClick = { navigator.push(EditListNavPoint(listId)) }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.settings),
                                    contentDescription = stringResource(R.string.content_desc_open_list_settings),
                                    tint = MaterialTheme.colorScheme.primaryContainer,
                                )
                            }
                        }
                    )

                    LazyColumn(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = defaultHorizontalEdgePadding),
                        state = listState
                    ) {
                        items(state.localProducts) { item ->
                            LocalProductTile(item)
                            Spacer(modifier = Modifier.height(cartListSpacing))
                        }
                        items(state.cart, key = { item -> item.id }) { item ->
                            ProductTile(
                                data = item,
                                onPick = { vm.selectItem(item.id) },
                                onDelete = { vm.onCheck(item.id) },
                                onEdit = { vm.onEditionStart(item.id) },
                                onTextChange = { vm.onEditionFieldTextChange(item.id, it) },
                                onChangeConfirm = { vm.onEditionConfirm(item.id) }
                            )
                            Spacer(modifier = Modifier.height(cartListSpacing))
                        }
                    }
                }
            }
        }
    }
}