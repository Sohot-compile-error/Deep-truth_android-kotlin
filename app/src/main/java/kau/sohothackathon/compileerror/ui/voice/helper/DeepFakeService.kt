package kau.sohothackathon.compileerror.ui.voice.helper

import kau.sohothackathon.compileerror.network.DeepFakeRequest
import kau.sohothackathon.compileerror.network.DeepFakeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface DeepFakeService {

    @POST("api/audio")
    suspend fun sendAudioFile(@Body deepFakeRequest: DeepFakeRequest): Response<DeepFakeResponse>

    @POST("api/video")
    suspend fun sendVedopFile(@Body deepFakeRequest: DeepFakeRequest): Response<DeepFakeResponse>

}