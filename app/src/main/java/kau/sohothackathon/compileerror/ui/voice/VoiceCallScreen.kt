package kau.sohothackathon.compileerror.ui.voice

import android.media.MediaPlayer
import android.os.Environment
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kau.sohothackathon.compileerror.R
import kau.sohothackathon.compileerror.ui.model.ApplicationState
import kau.sohothackathon.compileerror.ui.model.MediaType
import kau.sohothackathon.compileerror.ui.theme.Black
import kau.sohothackathon.compileerror.ui.theme.Red
import kau.sohothackathon.compileerror.ui.voice.helper.MediaCutter
import kau.sohothackathon.compileerror.ui.voice.helper.VoiceRecordingThread
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


@Composable
fun VoiceCallScreen(appState: ApplicationState) {

    val viewModel: VoiceCallViewModel = hiltViewModel()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val ouputPlayer: MediaPlayer =
        MediaPlayer.create(context, R.raw.songdongho_test)

    val outputDirectory =
        File(context.getExternalFilesDir(Environment.DIRECTORY_MUSIC), "input")
    outputDirectory.mkdirs()
    val noiseFile = context.resources.openRawResource(R.raw.noise2)
    val tempFile = File.createTempFile("prefix", ".mp3", context.cacheDir)
    FileOutputStream(tempFile).use { outputStream ->
        noiseFile.copyTo(outputStream)
    }

    val thread = VoiceRecordingThread(outputDirectory.absolutePath, tempFile)
    DisposableEffect(key1 = Unit) {
        // TODO 서버로부터 음원 받기 -> 현재 로컬에서 작동 중
        playVoiceCall(ouputPlayer) // 음원 플레이
        MediaCutter.cutMedia(
            context = context,
            resourceId = R.raw.levitating,
            type = MediaType.AUDIO,
            resultCallBack = {
                if (it == 1) viewModel.updateIsDetected(false)
                else viewModel.updateIsDetected(true)
            }
        ) // 음원 나누기
        viewModel.resetTimer()
        onDispose {
            viewModel.stopTimer()
            ouputPlayer.release()
        }
    }

    LaunchedEffect(key1 = Unit) {
        // TODO 영상을 위해 임시 딜레이
        delay(8000L)
        viewModel.updateIsDetected(true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFBFD6FF),
                        Color(0xFFE5D7E5)
                    )
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            color = Color.White,
            modifier = Modifier.padding(top = 50.dp),
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "${viewModel.callTime.value / 60}:${"%02d".format(viewModel.callTime.value % 60)}",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "내 목소리를 안전하게 지키고 있습니다.",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )

        if (viewModel.isDetected.value) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(20.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Black.copy(alpha = 0.3f))
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close_24),
                    contentDescription = "IC_CLOSE",
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(10.dp)
                        .size(24.dp)
                        .clickable {
                            viewModel.updateIsDetected(false)
                        },
                    tint = Color.White
                )

                Column(
                    modifier = Modifier.align(Alignment.Center),
                    verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_notice_24),
                        contentDescription = "IC_NOTICE",
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = "딥페이크 목소리가 감지되었습니다.", fontSize = 20.sp,
                        color = Color.White, fontWeight = FontWeight.Bold
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_voice_detector_90),
                    contentDescription = "IC_VOICE_DETECTOR_90",
                    modifier = Modifier
                        .size(90.dp, 67.dp)
                )
            }
        }


        Column(
            modifier = Modifier
                .padding(horizontal = 35.dp)
                .padding(bottom = 90.dp),
            verticalArrangement = Arrangement.spacedBy(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(75.dp)
                            .clip(CircleShape)
                            .clickable {
                            }
                            .background(Color.White.copy(alpha = 0.6f))
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_mic_mute_30),
                            contentDescription = "IC_DIAL",
                            modifier = Modifier
                                .size(30.dp)
                                .align(Alignment.Center),
                            tint = Black
                        )
                    }
                    Text(text = "음소거", modifier = Modifier.padding(top = 12.dp), fontSize = 16.sp)
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(75.dp)
                            .clip(CircleShape)
                            .clickable {
                            }
                            .background(Color.White.copy(alpha = 0.6f))
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_three_dots_30),
                            contentDescription = "IC_DIAL",
                            modifier = Modifier
                                .size(30.dp)
                                .align(Alignment.Center),
                            tint = Black
                        )
                    }
                    Text(text = "키패드", modifier = Modifier.padding(top = 12.dp), fontSize = 16.sp)
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(75.dp)
                            .clip(CircleShape)
                            .clickable {
                            }
                            .background(Color.White.copy(alpha = 0.6f))
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_volume_up_30),
                            contentDescription = "IC_DIAL",
                            modifier = Modifier
                                .size(30.dp)
                                .align(Alignment.Center),
                            tint = Black
                        )
                    }
                    Text(text = "스피커", modifier = Modifier.padding(top = 12.dp), fontSize = 16.sp)
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(75.dp)
                            .clip(CircleShape)
                            .clickable {
                            }
                            .background(Color.White.copy(alpha = 0.6f))
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_shuffle_30),
                            contentDescription = "IC_DIAL",
                            modifier = Modifier
                                .size(30.dp)
                                .align(Alignment.Center),
                            tint = Black
                        )
                    }
                    Text(
                        text = "노이즈\n셔플",
                        modifier = Modifier.padding(top = 12.dp),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(75.dp)
                            .clip(CircleShape)
                            .clickable {
                            }
                            .background(Color.White.copy(alpha = 0.6f))
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_camera_video_30),
                            contentDescription = "IC_DIAL",
                            modifier = Modifier
                                .size(30.dp)
                                .align(Alignment.Center),
                            tint = Black
                        )
                    }
                    Text(
                        text = "영상통화\n전환",
                        modifier = Modifier.padding(top = 12.dp),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(75.dp)
                            .clip(CircleShape)
                            .clickable {
                                scope.launch {
                                    thread.start()
                                    delay(5000L)
                                    thread.stopRecording()
                                }
                            }
                            .background(Color.White.copy(alpha = 0.6f))
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_person_lines_30),
                            contentDescription = "IC_DIAL",
                            modifier = Modifier
                                .size(30.dp)
                                .align(Alignment.Center),
                            tint = Black
                        )
                    }
                    Text(
                        text = "연락처",
                        modifier = Modifier.padding(top = 12.dp),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Button(
                colors = ButtonDefaults.buttonColors(Red),
                modifier = Modifier.size(75.dp),
                shape = CircleShape,
                onClick = {
                    appState.popBackStack()
                }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_telephone_30),
                    contentDescription = "IC_QUIT_CALL",
                    modifier = Modifier.size(36.dp),
                    tint = Color.White
                )
            }

        }

    }

}


fun playVoiceCall(mediaPlayer: MediaPlayer) {
    try {
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener { mediaPlayer -> mediaPlayer.release() }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}
