package kau.sohothackathon.compileerror.ui.voice.helper

import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.media.MediaRecorder
import android.util.Log
import java.io.File
import java.util.*

class VoiceRecordingThread(private val filePath: String, private val noiseFile: File) : Thread() {
    private var inputPlayer: MediaRecorder? = null
    private lateinit var fileName: String
    private var isRunning = true
    override fun run() {
        fileName = "original_voice_${System.currentTimeMillis()}.wav"
        startRecording()
        playNoise()
    }


    /**
     * 화이트 노이즈를 재생하는 함수
     * */
    private fun playNoise() {
        val sampleRate = 41000 // 샘플링 주파수
        val bufferSize = AudioTrack.getMinBufferSize(
            sampleRate,
            AudioFormat.CHANNEL_OUT_MONO,
            AudioFormat.ENCODING_PCM_16BIT
        )

        val audioTrack = AudioTrack(
            AudioManager.STREAM_MUSIC,
            sampleRate,
            AudioFormat.CHANNEL_OUT_MONO,
            AudioFormat.ENCODING_PCM_16BIT,
            bufferSize,
            AudioTrack.MODE_STREAM
        )

        val whiteNoise = ByteArray(bufferSize) // 화이트 노이즈 샘플 생성
        val random = Random()
        val noiseScale = 100 // 화이트 노이즈 크기를 나타내는 상수
        for (i in whiteNoise.indices) {
            whiteNoise[i] = (random.nextFloat() / noiseScale * 2.0f - 1.0f).toInt().toByte()
        }
        audioTrack.play()
        while (isRunning) {
            audioTrack.write(whiteNoise, 0, bufferSize) // 화이트 노이즈 샘플을 AudioTrack에 쓰기
        }
    }

    // 녹음 시작
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


}
