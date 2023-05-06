package kau.sohothackathon.compileerror.ui.vedio

import android.hardware.display.DisplayManager
import android.hardware.display.VirtualDisplay
import android.media.MediaRecorder
import android.media.projection.MediaProjection
import android.os.Build
import android.util.Log
import android.view.Surface
import androidx.annotation.RequiresApi
import java.io.File
import java.util.*


class VideoRecordingThread(
    private val mediaProjection: MediaProjection,
    private val filePath: String,
    private val noiseFile: File
) : Thread() {
    private var inputPlayer: MediaRecorder? = null
    private lateinit var fileName: String
    private var isRunning = true
    private lateinit var surface: Surface
    private lateinit var virtualDisplay: VirtualDisplay

    private var outputFile: String? = null
    private val isRecording = false

    override fun run() {
        fileName = "original_video_${System.currentTimeMillis()}.mp4"
        startRecording()
        playNoise()
    }

    private fun playNoise() {
        // Not Dowing
    }

    // 화면 녹음 시작
    @RequiresApi(Build.VERSION_CODES.O)
    private fun startRecording() {
        inputPlayer = MediaRecorder()


        val outputFile = File(filePath, fileName)
        outputFile.createNewFile()
        inputPlayer?.apply {
            setVideoSource(MediaRecorder.VideoSource.SURFACE)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setVideoEncoder(MediaRecorder.VideoEncoder.H264)
            setVideoEncodingBitRate(BIT_RATE)
            setVideoFrameRate(FRAME_RATE)
            setVideoSize(720, 1280)
            setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT)
            setVideoEncodingBitRate(BIT_RATE)
            setVideoFrameRate(FRAME_RATE)
            setVideoSize(720, 1280)
            setVideoEncoder(MediaRecorder.VideoEncoder.H264)
            setOutputFile(outputFile)
            this@VideoRecordingThread.surface = surface
            virtualDisplay = mediaProjection.createVirtualDisplay(
                "VideoRecoringThread", 720, 1280, 1,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC, surface, null, null
            );
            prepare()
            start()
        }
    }

    fun stopRecording() {
        Log.i("dlgocks1", "stopRecording: ")
        // 녹음 중지
        inputPlayer?.stop()
        inputPlayer?.release()
        // MediaRecorder 객체 해제
        inputPlayer = null
        isRunning = false
    }

    companion object {
        private val FRAME_RATE = 30
        private val I_FRAME_INTERVAL = 5
        private val BIT_RATE = 1000000
        private val MAX_RECORDING_TIME_MS = 60000 // 최대 녹화 시간 (60초)

    }

}
