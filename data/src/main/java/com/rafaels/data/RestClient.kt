package com.rafaels.data

import com.rafaels.data.remote.MarsRoverApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RestClient {

    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(
                    if (BuildConfig.DEBUG)
                        HttpLoggingInterceptor.Level.BODY
                    else
                        HttpLoggingInterceptor.Level.NONE
                )
            )
            .build()
    }

    private val retrofitInstance: Retrofit = Retrofit.Builder()
        .baseUrl("https://gist.github.com/RafaelSMartin/e5081bfb3706e668286b80cfd79bee39/raw/9b86ba92a9eae7536c5d6ff127507ede23538aab/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(createOkHttpClient())
        .build()

    fun getMarsRoverApi(): MarsRoverApi = retrofitInstance.create(MarsRoverApi::class.java)
}