package kau.sohothackathon.compileerror.ui.voice

import android.content.Context
import android.media.*
import android.os.Environment
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradient
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kau.sohothackathon.compileerror.R
import kau.sohothackathon.compileerror.ui.model.ApplicationState
import kau.sohothackathon.compileerror.ui.theme.Black
import kau.sohothackathon.compileerror.ui.theme.Red
import kau.sohothackathon.compileerror.ui.voice.helper.RecordingThread
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


@Composable
fun VoiceCallScreen(appState: ApplicationState) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val ouputPlayer: MediaPlayer =
        MediaPlayer.create(context, R.raw.levitating)

    DisposableEffect(key1 = Unit) {
        playVoiceCall(context, ouputPlayer)
        onDispose {
            ouputPlayer.release()
        }
    }
    val outputDirectory =
        File(context.getExternalFilesDir(Environment.DIRECTORY_MUSIC), "input")
    outputDirectory.mkdirs()
    val mp3InputStream = context.resources.openRawResource(R.raw.time_is_running_out)
    val tempFile = File.createTempFile("prefix", ".mp3", context.cacheDir)
    FileOutputStream(tempFile).use { outputStream ->
        mp3InputStream.copyTo(outputStream)
    }
    val thread = RecordingThread(outputDirectory.absolutePath, tempFile)

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
            text = "010-2465-1235",
            color = Color.White,
            modifier = Modifier.padding(top = 50.dp),
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold
        )
        Text(text = "00:00", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text(
            text = "내 목소리를 안전하게 지키고 있습니다.",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

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


        Column(
            modifier = Modifier
                .padding(horizontal = 35.dp)
                .padding(bottom = 90.dp),
            verticalArrangement = Arrangement.spacedBy(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(75.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.6f))
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_dial),
                            contentDescription = "IC_DIAL",
                            modifier = Modifier
                                .size(30.dp)
                                .align(Alignment.Center),
                            tint = Black
                        )
                    }
                    Text(text = "음소거", modifier = Modifier.padding(top = 12.dp), fontSize = 20.sp)
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column() {
                    Box(
                        modifier = Modifier
                            .size(75.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.6f))
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_dial),
                            contentDescription = "IC_DIAL",
                            modifier = Modifier
                                .size(30.dp)
                                .align(Alignment.Center),
                            tint = Black
                        )
                    }
                    Text(text = "음소거", modifier = Modifier.padding(top = 12.dp), fontSize = 20.sp)
                }
            }

            Button(
                colors = ButtonDefaults.buttonColors(Red),
                modifier = Modifier.size(75.dp),
                shape = CircleShape,
                onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_dial),
                    contentDescription = "IC_QUIT_CALL",
                    modifier = Modifier.size(36.dp)
                )
            }

        }

    }

//    Column(modifier = Modifier.fillMaxSize()) {
//        Text(text = "음성통화 화면")
//        Button(onClick = {
//            playVoiceCall(context, ouputPlayer)
//        }) {
//            Text(text = "통화종료")
//        }
//        Button(onClick = {
//            scope.launch {
//                thread.start()
//                delay(5000L)
//                thread.stopRecording()
//            }
//        }) {
//            Text(text = "녹음시작")
//        }
//        Button(onClick = { /*TODO*/ }) {
//            Text(text = "녹음종료")
//        }
//    }

}


fun playVoiceCall(context: Context, mediaPlayer: MediaPlayer) {
    try {
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener { mediaPlayer -> mediaPlayer.release() }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}
