package kau.sohothackathon.compileerror.ui.voice.helper

import android.app.Activity
import android.util.Log
import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferNetworkLossHandler
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3Client
import kau.sohothackathon.compileerror.network.DeepFakeRequest
import kau.sohothackathon.compileerror.ui.model.MediaType
import kau.sohothackathon.compileerror.ui.voice.helper.RetrofitHelper.apiService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File


object S3Client {

    fun upload(
        activity: Activity,
        fileName: String,
        file: File,
        type: MediaType,
        resultCallBack: (Int) -> Unit
    ) {
        Log.d("dlgocks1", "upload start")

        val awsCredentials: AWSCredentials =
            BasicAWSCredentials(
                "AKIAQRKQNEOUPL3JUPF5",
                "LLg3Fhez8Yd0aQJD6vQXat+bmXK9tbjUrDSuLQFs"
            ) // IAM 생성하며 받은 것 입력

        val s3Client = AmazonS3Client(awsCredentials, Region.getRegion(Regions.AP_NORTHEAST_2))

        val transferUtility = TransferUtility.builder().s3Client(s3Client)
            .context(activity.applicationContext).build()
        TransferNetworkLossHandler.getInstance(activity.applicationContext)

        val uploadObserver = transferUtility.upload(
            "sohot/voicecall-deepfake-detection",
            fileName,
            file
        ) // (bucket api, file이름, file객체)

        uploadObserver.setTransferListener(object : TransferListener {
            override fun onStateChanged(id: Int, state: TransferState) {
                if (state === TransferState.COMPLETED) {
                    Log.d("dlgocks1", "upload Complete $id")
                    GlobalScope.launch {
                        try {
                            val response = when (type) {
                                MediaType.AUDIO -> apiService.sendAudioFile(
                                    deepFakeRequest =
                                    DeepFakeRequest("sohot/voicecall-deepfake-detection/${fileName}")
                                )
                                MediaType.VIDEO -> apiService.sendVedopFile(
                                    deepFakeRequest =
                                    DeepFakeRequest("sohot/voicecall-deepfake-detection/${fileName}")
                                )
                            }

                            if (response.isSuccessful) {
                                val exampleData = response.body()
                                Log.d("dlgocks1 - retrofit2 response", exampleData.toString())
                                exampleData?.let {
                                    resultCallBack(it.predictionRate)
                                }
                            } else {
                                Log.d("dlgocks1 - retrofit2 response - fail", response.toString())
                            }
                        } catch (e: Exception) {
                            Log.d("dlgocks1 - retrofit2 response - fail", e.toString())
                        }
                    }
                }
            }

            override fun onProgressChanged(id: Int, current: Long, total: Long) {
                val done = (current.toDouble() / total * 100.0).toInt()
                Log.d("dlgocks1", "UPLOAD - - ID: $id, percent done = $done")
            }

            override fun onError(id: Int, ex: Exception) {
                Log.d("dlgocks1", "UPLOAD ERROR - - ID: $id - - EX:$ex")
            }
        })
    }


}