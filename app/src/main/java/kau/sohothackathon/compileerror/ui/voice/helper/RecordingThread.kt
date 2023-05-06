package kau.sohothackathon.compileerror.ui.voice.helper

import android.annotation.SuppressLint
import android.media.*
import android.util.Log
import com.arthenica.mobileffmpeg.FFmpeg
import kau.sohothackathon.compileerror.MainActivity
import kau.sohothackathon.compileerror.MainActivity.Companion.RECORDING_LENGTH
import java.io.File
import java.io.FileOutputStream
import java.util.*

class RecordingThread(private val filePath: String, private val noiseFile: File) : Thread() {
    private var inputPlayer: MediaRecorder? = null
    private lateinit var fileName: String
    private var isRunning = true
    override fun run() {
        fileName = "original_voice_${System.currentTimeMillis()}.wav"
        startRecording()
//        playNoise()
//        startRecordingWithAudioRecord()
    }


    fun playNoise() {
        val sampleRate = 11000 // 샘플링 주파수
        val bufferSize = AudioTrack.getMinBufferSize(
            sampleRate,
            AudioFormat.CHANNEL_OUT_MONO,
            AudioFormat.ENCODING_PCM_16BIT
        ) // 버퍼 크기

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
//            whiteNoise[i] = (random.nextFloat() / noiseScale * 2.0f - 1.0f).toInt().toByte()
            whiteNoise[i] = (random.nextFloat() / noiseScale * 2.0f - 1.0f).toInt().toByte()
        }
//        random.nextBytes(whiteNoise)
        audioTrack.play()

        while (isRunning) {
            audioTrack.write(whiteNoise, 0, bufferSize) // 화이트 노이즈 샘플을 AudioTrack에 쓰기
        }

    }

    @SuppressLint("MissingPermission")
    private fun startRecordingWithAudioRecord() {
        val bufferSize = AudioRecord.getMinBufferSize(
            MainActivity.SAMPLE_RATE,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT
        )
        val record = AudioRecord(
            MediaRecorder.AudioSource.DEFAULT,
            MainActivity.SAMPLE_RATE,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT,
            bufferSize
        )

        if (record.state != AudioRecord.STATE_INITIALIZED) {
            Log.e("dlgocks1", "Audio Record can't initialize!")
            return
        }
        record.startRecording()

        var shortsRead: Long = 0
        var recordingOffset = 0
        val audioBuffer = ShortArray(bufferSize / 2)
        val recordingBuffer = ShortArray(RECORDING_LENGTH)

        val outputFile = File(filePath, fileName)
        outputFile.createNewFile()
        val outputStream = FileOutputStream(outputFile)

        while (shortsRead < RECORDING_LENGTH) {
            val numberOfShort = record.read(audioBuffer, 0, audioBuffer.size)
            shortsRead += numberOfShort.toLong()
            System.arraycopy(audioBuffer, 0, recordingBuffer, recordingOffset, numberOfShort)
            recordingOffset += numberOfShort
        }
        outputStream.write(audioBuffer.map { it.toByte() }.toByteArray(), 0, audioBuffer.size)
        outputStream.close()

        record.stop()
        record.release()

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
        isRunning = false
//        addNoise()
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
