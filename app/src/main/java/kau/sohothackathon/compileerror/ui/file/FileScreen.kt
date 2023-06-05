package kau.sohothackathon.compileerror.ui.file

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kau.sohothackathon.compileerror.R
import kau.sohothackathon.compileerror.domain.model.MediaType
import kau.sohothackathon.compileerror.ui.MainViewModel
import kau.sohothackathon.compileerror.ui.model.ApplicationState
import kau.sohothackathon.compileerror.ui.theme.Black
import kau.sohothackathon.compileerror.ui.theme.DEEP_TRUTH_BLUE
import kau.sohothackathon.compileerror.ui.theme.Main
import kau.sohothackathon.compileerror.ui.theme.SubGray
import kau.sohothackathon.compileerror.util.Constants.AUDIO_PLAY_ROUTE
import kau.sohothackathon.compileerror.util.Constants.VIDEO_PLAY_ROUTE


@Composable
fun FileScreen(appState: ApplicationState, viewModel: MainViewModel) {

    LaunchedEffect(key1 = Unit) {
        viewModel.getAllMediafiles()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(20.dp)
    ) {

        FileSearchBar(
            search = viewModel.mediaSearch.value,
            updateSearch = viewModel::updateMediaSearch,
        )

        Text(
            text = if (viewModel.mediaSearch.value.isEmpty()) "기기 내부 미디어 파일" else "'${viewModel.mediaSearch.value}' 검색 결과",
            color = Color.Black,
            fontSize = 13.sp,
            modifier = Modifier.padding(top = 20.dp, bottom = 10.dp)
        )

        /** 미디어 파일  */
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(40.dp),
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            if (viewModel.mediaSearch.value.isEmpty()) {

                items(viewModel.mediaFiles.value) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                appState.navController.navigate(
                                    "${AUDIO_PLAY_ROUTE}?name=${it.name}&mediaType=${it.mediaType}&contentUri=${it.contrntUri.toString()}",
                                )
                            },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(20))
                                .background(Color(0xFFF5F5F5)),
                        ) {
                            Icon(
                                painter = when (it.mediaType) {
                                    MediaType.VIDEO -> painterResource(id = R.drawable.ic_baseline_video_file_24)
                                    MediaType.AUDIO -> painterResource(id = R.drawable.ic_baseline_audio_file_24)
                                },
                                contentDescription = "파일 아이콘",
                                modifier = Modifier
                                    .size(50.dp)
                                    .align(Alignment.Center),
                                tint = DEEP_TRUTH_BLUE
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = it.name,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = it.contrntUri.toString(),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
            } else {
                items(viewModel.filteredMediaFiles.value) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                if (it.mediaType == MediaType.AUDIO) {
                                    appState.navController.navigate(
                                        "${AUDIO_PLAY_ROUTE}?name=${it.name}&mediaType=${it.mediaType}&contentUri=${it.contrntUri.toString()}",
                                    )
                                } else {
                                    appState.navController.navigate(
                                        "${VIDEO_PLAY_ROUTE}?name=${it.name}&mediaType=${it.mediaType}&contentUri=${it.contrntUri.toString()}",
                                    )
                                }
                            },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(20))
                                .background(Color(0xFFF5F5F5)),
                        ) {
                            Icon(
                                painter = when (it.mediaType) {
                                    MediaType.VIDEO -> painterResource(id = R.drawable.ic_baseline_video_file_24)
                                    MediaType.AUDIO -> painterResource(id = R.drawable.ic_baseline_audio_file_24)
                                },
                                contentDescription = "파일 아이콘",
                                modifier = Modifier
                                    .size(50.dp)
                                    .align(Alignment.Center),
                                tint = DEEP_TRUTH_BLUE
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            buildAnnotatedString {
                                it.name.forEach { searchChar ->
                                    if (viewModel.mediaSearch.value.contains(searchChar)) {
                                        withStyle(
                                            style = SpanStyle(
                                                color = Main,
                                                fontWeight = FontWeight.Bold
                                            )
                                        ) {
                                            append(searchChar)
                                        }
                                    } else {
                                        withStyle(style = SpanStyle(color = Black)) {
                                            append(searchChar)
                                        }
                                    }
                                }
                            },
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(),
                        )
                        Text(
                            text = it.contrntUri.toString(),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun FileSearchBar(
    search: String = "",
    updateSearch: (String) -> Unit,
) {

    /** 유저 텍스트 필드 */
    Row() {
        BasicTextField(modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(80.dp))
            .background(SubGray),
            textStyle = LocalTextStyle.current.copy(
                fontSize = 16.sp,
                color = Color.Black,
            ),
            value = search,
            onValueChange = updateSearch,
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(20.dp, 15.dp),
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "IC_SEARCH",
                        modifier = Modifier.size(24.dp),
                    )
                    Box(Modifier.weight(1f)) {
                        if (search.isEmpty()) {
                            Text(
                                "파일명을 입력해주세요.",
                                style = LocalTextStyle.current.copy(
                                    color = Color.Black.copy(alpha = 0.5f),
                                    fontSize = 16.sp,
                                ),
                            )
                        }
                        innerTextField()
                    }
                }
            })
    }
}
