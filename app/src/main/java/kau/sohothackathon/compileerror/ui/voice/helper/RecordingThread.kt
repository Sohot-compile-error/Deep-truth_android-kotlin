package kau.sohothackathon.compileerror.ui.voice.helper

import android.media.MediaMetadataRetriever
import android.media.MediaRecorder
import android.util.Log
import com.arthenica.mobileffmpeg.FFmpeg
import java.io.File

class RecordingThread(private val filePath: String, private val noiseFile: File) : Thread() {
    private var inputPlayer: MediaRecorder? = null
    private lateinit var fileName: String

    override fun run() {
        fileName = "original_voice_${System.currentTimeMillis()}.wav"
        startRecording()
    }

    private fun startRecording() {
        Log.i("dlgocks1", "startRecording: ")
        // 녹음을 저장할 파일 생성
        inputPlayer = MediaRecorder()
        val outputFile = File(filePath, fileName)
        outputFile.createNewFile()
        inputPlayer?.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setAudioSamplingRate(44100)
            setAudioEncodingBitRate(192000)
            setOutputFile(outputFile)
            // 녹음 시작
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
        addNoise()
    }

    fun addNoise() {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource("$filePath/$fileName")
        val durationStr = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        val duration = durationStr?.toLong()

        Log.i("dlgocks1", "duration : $duration")

        val srcFile = File(filePath, fileName)
        val resultFile = File(filePath, "noise_voice_${System.currentTimeMillis()}.wav")
        FFmpeg.execute("-i ${srcFile.absolutePath} -i ${noiseFile.absolutePath} -filter_complex \"[0:a]volume=1[a];[1:a]volume=0.5[b];[a][b]amix=inputs=2:duration=longest\" ${resultFile.absolutePath}")
    }

}
