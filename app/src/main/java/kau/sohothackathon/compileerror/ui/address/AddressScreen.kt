@file:OptIn(ExperimentalMaterialApi::class)

package kau.sohothackathon.compileerror.ui.address

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kau.sohothackathon.compileerror.R
import kau.sohothackathon.compileerror.ui.model.ApplicationState
import kau.sohothackathon.compileerror.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun AddressScreen(appState: ApplicationState) {

    val viewModel: AddressViewModel = hiltViewModel()
    val context = LocalContext.current
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {
        viewModel.getAllContacts(context)
    }

    BottomSheetScaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray),
        sheetPeekHeight = 0.dp, // 바텀 네비게이션 바
        sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        scaffoldState = bottomSheetScaffoldState,
        sheetElevation = 0.dp,
        sheetContent = {
            DialBottomSheetContent(
                phoneSearch = viewModel.phoneSearch.value,
                updatePhoneSearch = viewModel::updatePhoneSearch,
                deletePhoneSearch = viewModel::deletePhoneSearch,
            )
        }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {

            if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                DefaultAddressContainer(
                    search = viewModel.search.value,
                    updateSearch = viewModel::updateSearch,
                    addresses = viewModel.addresses,
                    filteredAddresses = viewModel.filteredAddresses.value,
                )
                FloatingActionButton(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(20.dp, 40.dp), backgroundColor = Gray, onClick = {
                        scope.launch {
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        }
                    }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_dial),
                        contentDescription = "IC_DIAL",
                        tint = Black,
                        modifier = Modifier.size(24.dp),
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(1f)
                        .padding(top = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    if (viewModel.phoneSearch.value.isEmpty()) {
                        items(viewModel.addresses) { address ->
                            AddressItem(address, viewModel.phoneSearch.value)
                        }
                    } else {
                        items(viewModel.phonefilteredAddresses.value) { address ->
                            AddressItem(address, viewModel.phoneSearch.value)
                        }
                    }
                }
            }
        }
    }
}

