package kau.sohothackathon.compileerror.ui.voice

import android.content.Context
import android.media.*
import android.os.Environment
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import kau.sohothackathon.compileerror.R
import kau.sohothackathon.compileerror.ui.voice.helper.RecordingThread
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


@Composable
fun VoiceCallScreen() {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val ouputPlayer: MediaPlayer =
        MediaPlayer.create(context, R.raw.levitating)

    DisposableEffect(key1 = Unit) {
        playVoiceCall(context, ouputPlayer)
//        AudioCutter.cutAudio(context, R.raw.levitating)
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
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "음성통화 화면")
        Button(onClick = {
            playVoiceCall(context, ouputPlayer)
        }) {
            Text(text = "통화종료")
        }
        Button(onClick = {
            scope.launch {
                thread.start()
                delay(5000L)
                thread.stopRecording()
            }
        }) {
            Text(text = "녹음시작")
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "녹음종료")
        }
    }

}


fun playVoiceCall(context: Context, mediaPlayer: MediaPlayer) {
    try {
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener { mediaPlayer -> mediaPlayer.release() }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}
