package kau.sohothackathon.compileerror.ui.file

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kau.sohothackathon.compileerror.R
import kau.sohothackathon.compileerror.domain.model.MediaType
import kau.sohothackathon.compileerror.ui.MainViewModel
import kau.sohothackathon.compileerror.ui.model.ApplicationState
import kau.sohothackathon.compileerror.ui.theme.DEEP_TRUTH_BLUE


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
        Text(
            text = "기기 내부 미디어 파일",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(40.dp),
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(viewModel.mediaFiles.value) {
                Column(
                    modifier = Modifier
                        .weight(1f),
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
        }


    }
}