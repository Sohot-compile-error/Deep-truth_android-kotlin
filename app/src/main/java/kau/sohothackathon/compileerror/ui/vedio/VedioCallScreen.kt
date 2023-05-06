package kau.sohothackathon.compileerror.ui.vedio

import android.net.Uri
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import kau.sohothackathon.compileerror.R
import kau.sohothackathon.compileerror.ui.model.ApplicationState
import kau.sohothackathon.compileerror.ui.model.MediaType
import kau.sohothackathon.compileerror.ui.theme.Black
import kau.sohothackathon.compileerror.ui.theme.Red
import kau.sohothackathon.compileerror.ui.voice.helper.MediaCutter
import kotlinx.coroutines.delay

@Composable
fun VedioCallScreen(appState: ApplicationState) {

    /** Video Player */
    val mp4ResourceId = R.raw.test_vedio
    val context = LocalContext.current
    val mp4Uri = Uri.parse("rawresource:///${context.packageName}/$mp4ResourceId")
    val player = SimpleExoPlayer.Builder(context).build()
    val mediaItem = MediaItem.fromUri(mp4Uri)

    var isDialog by remember {
        mutableStateOf(false)
    }

    DisposableEffect(key1 = Unit) {
        player.setMediaItem(mediaItem)
        MediaCutter.cutMedia(
            context = context,
            resourceId = R.raw.test_vedio,
            type = MediaType.VIDEO,
            resultCallBack = {
                if (it == 1) isDialog = false
                else isDialog = false
            }
        ) // 음원 나누기
        player.prepare()
        player.play()
        onDispose {
            player.release()
        }
    }

    LaunchedEffect(key1 = Unit) {
        delay(7000L)
        isDialog = true
    }

    /** 전면 카메라 */
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val previewView = remember { PreviewView(context) }
    val preview = remember {
        Preview.Builder()
            .build()
            .also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }
    }

    val cameraSelector = remember {
        CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
            .build()
    }

    val imageCapture = remember {
        ImageCapture.Builder()
            .build()
    }

    val cameraProvider = cameraProviderFuture.get()
    val camera =
        cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageCapture)

    DisposableEffect(Unit) {
        onDispose {
            cameraProvider.unbindAll()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        AndroidView(
            factory = { context ->
                PlayerView(context).apply {
                    useController = false
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                    scaleX = 1f
                    scaleY = 1f
                    this@apply.player = player
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        if (isDialog) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(20.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Black.copy(alpha = 0.75f))
                    .padding(vertical = 20.dp, horizontal = 10.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close_24),
                    contentDescription = "IMG_VEDIO_CALL",
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(24.dp)
                        .clickable {
                            isDialog = false
                        },
                    tint = Color.White
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_notice_24),
                        contentDescription = "IC_NOTION",
                        modifier = Modifier
                            .size(36.dp),
                        tint = Color.White
                    )
                    Text(
                        text = "딥페이크 영상/목소리가\n감지되었습니다.",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )

                }

            }
        }


        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(30.dp, 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
        ) {
            AndroidView(
                { previewView },
                modifier = Modifier
                    .align(Alignment.End)
                    .size(120.dp, 220.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(20.dp))
                    .background(Black.copy(alpha = 0.7f))
                    .padding(15.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            ) {
                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Black),
                    shape = CircleShape,
                    modifier = Modifier.size(64.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_mic_mute_30),
                        contentDescription = "IMG_IC",
                        modifier = Modifier.size(30.dp),
                        tint = Color.White
                    )
                }
                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Black),
                    shape = CircleShape,
                    modifier = Modifier.size(64.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_camera_video_30),
                        contentDescription = "IC_CAMEAR_VIDEO",
                        modifier = Modifier.size(30.dp),
                        tint = Color.White
                    )
                }
                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Black),
                    shape = CircleShape,
                    modifier = Modifier.size(64.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_record_30),
                        contentDescription = "IMG_IC",
                        modifier = Modifier.size(30.dp),
                        tint = Color.White
                    )
                }
                Button(
                    onClick = {
                        appState.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Red),
                    shape = CircleShape,
                    modifier = Modifier.size(64.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close_24),
                        contentDescription = "IMG_CLOSE",
                        modifier = Modifier.size(30.dp),
                        tint = Color.White
                    )
                }
            }
        }

    }
}