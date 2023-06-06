@file:OptIn(ExperimentalMaterialApi::class)

package kau.sohothackathon.compileerror.ui.address

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import kau.sohothackathon.compileerror.R
import kau.sohothackathon.compileerror.ui.MainViewModel
import kau.sohothackathon.compileerror.ui.model.ApplicationState
import kau.sohothackathon.compileerror.ui.theme.*
import kau.sohothackathon.compileerror.util.Constants.VIDEO_CALL_ROUTE
import kau.sohothackathon.compileerror.util.Constants.VOICE_CALL_ROUTE
import kotlinx.coroutines.launch

@Composable
fun AddressScreen(
    appState: ApplicationState,
    viewModel: MainViewModel = hiltViewModel(),
) {

    val context = LocalContext.current
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()
    val navigateToVoiceCall: (String) -> Unit = {
        appState.navigate(VOICE_CALL_ROUTE)
    }
    val navigateToVedioCall: (String) -> Unit = {
        appState.navigate(VIDEO_CALL_ROUTE)
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.getAllContacts(context)
    }

    BottomSheetScaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray)
            .navigationBarsPadding(),
        sheetPeekHeight = 0.dp, // 바텀 네비게이션 바
        sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        scaffoldState = bottomSheetScaffoldState,
        sheetElevation = 0.dp,
        sheetContent = {
            DialBottomSheetContent(
                phoneSearch = viewModel.phoneSearch.value,
                updatePhoneSearch = viewModel::updatePhoneSearch,
                deletePhoneSearch = viewModel::deletePhoneSearch,
                naivageToVoiceCall = navigateToVoiceCall,
            )
        }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                DefaultAddressContainer(
                    search = viewModel.search.value,
                    updateSearch = viewModel::updateSearch,
                    addresses = viewModel.addresses,
                    filteredAddresses = viewModel.filteredAddresses.value,
                    navigateToVoiceCall = navigateToVoiceCall,
                    navigateToVedioCall = navigateToVedioCall
                )
                FloatingActionButton(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .navigationBarsPadding()
                        .padding(20.dp, 80.dp), backgroundColor = Gray, onClick = {
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
                            AddressItem(
                                address = address,
                                searchString = viewModel.phoneSearch.value,
                                navigateToVoiceCall = navigateToVoiceCall,
                                navigateToVedioCall = navigateToVedioCall
                            )
                        }
                    } else {
                        items(viewModel.phonefilteredAddresses.value) { address ->
                            AddressItem(
                                address = address,
                                searchString = viewModel.phoneSearch.value,
                                navigateToVoiceCall = navigateToVoiceCall,
                                navigateToVedioCall = navigateToVedioCall
                            )
                        }
                    }
                }
            }
        }
    }
}

