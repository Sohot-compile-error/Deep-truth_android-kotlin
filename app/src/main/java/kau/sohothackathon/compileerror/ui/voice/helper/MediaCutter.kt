package kau.sohothackathon.compileerror.ui.voice.helper

import android.app.Activity
import android.content.Context
import android.media.MediaMetadataRetriever
import android.os.Environment
import android.util.Log
import com.arthenica.mobileffmpeg.Config
import com.arthenica.mobileffmpeg.FFmpeg
import kau.sohothackathon.compileerror.ui.model.MediaType
import java.io.File
import java.io.FileOutputStream


object MediaCutter {

    /**
     * @param resourceId 리소스 id를 입력합니다 -> 후에 통화 녹음파일로 대체될 예정
     * @param segmentDuration 자를 단위를 ms 단위로 입력합니다.
     * context와 resourceId를 받아 해당 리소스를 단위기준로 자릅니다.
     */
    fun cutMedia(
        context: Context,
        resourceId: Int,
        type: MediaType,
        resultCallBack: (Int) -> Unit,
        segmentDuration: Long = 10000L,
    ) {
        // 1. R.raw에서 mp3 파일을 로드합니다.
        val mp3InputStream = context.resources.openRawResource(resourceId)

        // 2. 로드한 mp3 파일을 임시 파일에 저장합니다. - 전화 녹음 대체
        val tempFile = File.createTempFile("prefix", ".mp3", context.cacheDir)
        FileOutputStream(tempFile).use { outputStream ->
            mp3InputStream.copyTo(outputStream)
        }

        val outputDirectory =
            File(context.getExternalFilesDir(Environment.DIRECTORY_MUSIC), "output")
        outputDirectory.mkdirs()

        val metadataRetriever = MediaMetadataRetriever()
        metadataRetriever.setDataSource(tempFile.absolutePath)

        val duration =
            metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
                ?.toLong()
                ?: 0

        // 3. MobileFFmpeg 라이브러리를 사용하여 파일을 10초 단위로 자릅니다.
        for (startTime in 0 until duration step segmentDuration) {
            val endTime =
                if (startTime + segmentDuration < duration) startTime + segmentDuration else duration

            val outputFile = File(
                outputDirectory,
                "output_${System.currentTimeMillis()}_${startTime}_${endTime}.wav"
            )

            val command = arrayOf(
                "-i",
                tempFile.absolutePath,
                "-ss",
                (startTime / 1000).toString(),
                "-to",
                (endTime / 1000).toString(),
                "-vn",
                "-acodec",
                "pcm_s16le",
                "-ac",
                "1",
                "-ar",
                "16000",
                outputFile.absolutePath
            )

            val executionId = FFmpeg.executeAsync(command) { executionId, returnCode ->
                if (returnCode == Config.RETURN_CODE_SUCCESS) {
                    // TODO outputFile 서버로 보내서 비동기 검증 수행
                    val s3fileName =
                        "output_${System.currentTimeMillis()}_${startTime}_${endTime}.wav"
                    Log.i("dlgocks1", s3fileName)
                    S3Client.upload(
                        context as Activity,
                        s3fileName,
                        outputFile,
                        type,
                        resultCallBack
                    )
                }
            }
        }
    }
}

