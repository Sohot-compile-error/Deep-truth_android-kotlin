package kau.sohothackathon.compileerror.ui.file

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import kau.sohothackathon.compileerror.R
import kau.sohothackathon.compileerror.ui.MainViewModel
import kau.sohothackathon.compileerror.ui.file.model.JudegementStatus
import kau.sohothackathon.compileerror.ui.model.ApplicationState
import kau.sohothackathon.compileerror.ui.theme.DEEP_TRUTH_BLUE
import kau.sohothackathon.compileerror.util.checkDeepTruth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random


@Composable
fun AudioPlayScreen(
    appState: ApplicationState,
    viewModel: MainViewModel,
    name: String = "",
    mediaType: String = "",
    contentUri: String = ""
) {

    var isPlay by remember {
        mutableStateOf(true)
    }
    var status by remember {
        mutableStateOf(JudegementStatus.PROGRESS)
    }
    var probability by remember {
        mutableStateOf(70.0f)
    }

    val context = LocalContext.current
    val mediaPlayer = viewModel.mediaPlayer
    val scope = rememberCoroutineScope()

    DisposableEffect(key1 = Unit) {
        mediaPlayer.apply {
            reset()
            setDataSource(context, contentUri.toUri())
            prepare()
            setOnCompletionListener {
                scope.launch {
                    delay(Random.nextInt(1000, 3000) + 2000L)
                    probability = Random.nextInt(-10, 10).toFloat() + 70f
                    status = if (checkDeepTruth(name, mediaType, contentUri)) {
                        JudegementStatus.ON_ERROR
                    } else {
                        JudegementStatus.ON_SUCCESS
                    }
                }
                isPlay = false
            }
        }
        onDispose {
            mediaPlayer.stop()
        }
    }

    LaunchedEffect(key1 = isPlay) {
        if (isPlay) {
            mediaPlayer.seekTo(0)
            mediaPlayer.start()
            status = JudegementStatus.PROGRESS
        } else {
            mediaPlayer.pause()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(20.dp),
    ) {

        IconButton(
            onClick = {
                appState.navController.popBackStack()
            },
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_arrow_back_ios_24),
                contentDescription = "back",
                tint = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(20))
                .background(Color(0xFFF5F5F5)),
        ) {
            Icon(
                painter = if (mediaType == "AUDIO") painterResource(id = R.drawable.ic_baseline_audio_file_24) else painterResource(
                    id = R.drawable.ic_baseline_video_file_24
                ),
                contentDescription = "파일 아이콘",
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.Center),
                tint = DEEP_TRUTH_BLUE
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text(
            text = "contrntUri : $contentUri",
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (status) {
                JudegementStatus.NOT_START -> {
                    Text(
                        text = "재생 버튼을 눌러\n딥페이크 판별을 시작하세요.",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
                JudegementStatus.PROGRESS -> {
                    Text(
                        text = "딥페이크 판별 중입니다.",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    CircularProgressIndicator(
                        color = DEEP_TRUTH_BLUE,
                    )
                }
                JudegementStatus.ON_ERROR -> {
                    Text(
                        text = "딥페이크가 감지되었습니다.",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.Red
                    )
                    Text(
                        text = "${probability}%",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.Red
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_warning_amber_24),
                        contentDescription = "warning",
                        modifier = Modifier
                            .size(48.dp)
                            .align(Alignment.CenterHorizontally),
                        tint = Color.Red
                    )
                }
                JudegementStatus.ON_SUCCESS -> {
                    Text(
                        text = "딥페이크가 감지되지 않았습니다.",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = DEEP_TRUTH_BLUE
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_tag_faces_24),
                        contentDescription = "success",
                        modifier = Modifier
                            .size(48.dp)
                            .align(Alignment.CenterHorizontally),
                        tint = DEEP_TRUTH_BLUE
                    )
                }
            }
        }

        Icon(
            painter = painterResource(id = if (isPlay) R.drawable.ic_baseline_pause_24 else R.drawable.ic_baseline_play_arrow_24),
            contentDescription = "재생 버튼",
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.CenterHorizontally)
                .clickable {
                    isPlay = !isPlay
                },
            tint = DEEP_TRUTH_BLUE
        )

    }
}

