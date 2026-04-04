@file:OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)

package com.kopim.productlist.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.kopim.productlist.R
import com.kopim.productlist.data.mvvm.NavCommand
import com.kopim.productlist.data.mvvm.list.ListViewModel
import com.kopim.productlist.data.utils.ListScreenMode
import com.kopim.productlist.data.utils.LocalChange
import com.kopim.productlist.data.utils.stableLazyKey
import com.kopim.productlist.data.utils.ProductUiData
import com.kopim.productlist.ui.components.BackNavigationButton
import com.kopim.productlist.ui.components.DefaultFab
import com.kopim.productlist.ui.components.LocalProductTile
import com.kopim.productlist.ui.components.NewProductInputSystem
import com.kopim.productlist.ui.components.ProductTile
import com.kopim.productlist.ui.components.ScreenTitle
import com.kopim.productlist.ui.navigation.EditListNavPoint
import com.kopim.productlist.ui.theme.ProductsTheme
import com.kopim.productlist.ui.theme.cartListSpacing
import com.kopim.productlist.ui.theme.defaultHorizontalEdgePadding
import com.kopim.productlist.ui.theme.textBlack
import com.kopim.productlist.ui.theme.thinPadding
import com.kopim.productlist.ui.theme.topIconSize
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
        vm.setListId(listId)
        vm.subscribeOnList()
        vm.updateList()
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
            containerColor = MaterialTheme.colorScheme.background,
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
                        navigationIcon = {
                            BackNavigationButton(
                                onClick = { navigator.pop() },
                                contentDescription = stringResource(R.string.content_desc_back),
                                tint = textBlack,
                            )
                        },
                        actions = {
                            IconButton(
                                onClick = { navigator.push(EditListNavPoint(listId)) }
                            ) {
                                Icon(
                                    modifier = Modifier.size(topIconSize),
                                    painter = painterResource(R.drawable.settings),
                                    contentDescription = stringResource(R.string.content_desc_open_list_settings),
                                    tint = textBlack,
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
                        items(
                            items = state.localProducts,
                            key = { it.stableLazyKey() },
                        ) { item ->
                            Column(Modifier.animateItem()) {
                                LocalProductTile(item)
                                Spacer(modifier = Modifier.height(cartListSpacing))
                            }
                        }
                        items(
                            items = state.cart,
                            key = { item -> item.id },
                        ) { item ->
                            Column(Modifier.animateItem()) {
                                ProductTile(
                                    data = item,
                                    onPick = { vm.selectItem(item.id) },
                                    onDelete = { vm.onCheck(item.id) },
                                    onEdit = { vm.onEditionStart(item.id) },
                                    onTextChange = { vm.onEditionFieldTextChange(item.id, it) },
                                    onChangeConfirm = { vm.onEditionConfirm(item.id) },
                                )
                                Spacer(modifier = Modifier.height(cartListSpacing))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CartScreenPreview() {
    val products = listOf(
        ProductUiData(1, Color(0xFF81D4FA), "Молоко", false),
        ProductUiData(2, Color(0xFF81D4FA), "Яйца", true),
        ProductUiData(3, Color(0xFF81D4FA), "Сыр", false, picked = true),
    )
    val locals = listOf(LocalChange.AdditionChange(name = "Хлеб (локально)", cartId = 1L))

    ProductsTheme {
        NewProductInputSystem(
            text = TextFieldValue(""),
            hints = emptyList(),
            expanded = false,
            onEdit = {},
            onConfirm = {},
            onHide = {},
            onHintPick = {},
        ) {
            Scaffold(
                containerColor = MaterialTheme.colorScheme.background,
                floatingActionButton = {
                    DefaultFab(onClick = {}) {
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
                        ScreenTitle(text = "Нужно купить:")
                        LazyColumn(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = defaultHorizontalEdgePadding),
                        ) {
                            items(
                                items = locals,
                                key = { it.stableLazyKey() },
                            ) { item ->
                                Column(Modifier.animateItem()) {
                                    LocalProductTile(item)
                                    Spacer(modifier = Modifier.height(cartListSpacing))
                                }
                            }
                            items(
                                items = products,
                                key = { item -> item.id },
                            ) { item ->
                                Column(Modifier.animateItem()) {
                                    ProductTile(
                                        data = item,
                                        onPick = {},
                                        onDelete = {},
                                        onEdit = {},
                                        onTextChange = {},
                                        onChangeConfirm = {},
                                    )
                                    Spacer(modifier = Modifier.height(cartListSpacing))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}