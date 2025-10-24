package com.kopim.productlist.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kopim.productlist.R
import com.kopim.productlist.data.mvvm.list.ListViewModel
import com.kopim.productlist.data.utils.ListScreenMode
import com.kopim.productlist.ui.components.DefaultFab
import com.kopim.productlist.ui.components.LocalProductTile
import com.kopim.productlist.ui.components.NewProductInputSystem
import com.kopim.productlist.ui.components.ProductTile
import com.kopim.productlist.ui.components.ScreenTitle
import com.kopim.productlist.ui.theme.cartListSpacing
import com.kopim.productlist.ui.theme.defaultPadding
import com.kopim.productlist.ui.theme.surfaceWhite
import com.kopim.productlist.ui.theme.textBlack
import com.kopim.productlist.ui.theme.thinPadding
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CartScreen(
    contentPadding: PaddingValues,
    listId: Long = 1,
    vm: ListViewModel = koinViewModel(),
) {
    val listState = rememberLazyListState()
    val state by vm.state.collectAsState()

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
        modifier = Modifier.padding(contentPadding)
    ) {
        Scaffold(
            floatingActionButton = {
                DefaultFab(onClick = vm::enterAdditionMode) {
                    Icon(
                        painter = painterResource(R.drawable.add),
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(thinPadding).fillMaxSize(),
                        contentDescription = null,
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
                    ScreenTitle(stringResource(R.string.default_list_label))

                    LazyColumn(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = defaultPadding),
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