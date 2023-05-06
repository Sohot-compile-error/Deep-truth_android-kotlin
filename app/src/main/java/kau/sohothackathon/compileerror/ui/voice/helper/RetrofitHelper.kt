package kau.sohothackathon.compileerror.ui.voice.helper

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitHelper {

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://15.165.148.96:80/")
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: DeepFakeService = retrofit.create(DeepFakeService::class.java)


}