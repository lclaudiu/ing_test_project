package com.claudiuluca.ingtest.data.api

import com.claudiuluca.ingtest.BuildConfig
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class NetworkFactory(moshi: Moshi) {
    private val okHttpClient: OkHttpClient
    private val retrofitBuilder: Retrofit.Builder

    init {
        val level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE

        val loggingInterceptor = HttpLoggingInterceptor().apply { this.level = level }

        okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
    }

    fun <T> createApi(
        apiClass: Class<T>,
        baseUrl: String,
        interceptors: List<Interceptor> = emptyList()
    ): T {
        if (interceptors.isNotEmpty()) {
            val newClient = okHttpClient.newBuilder()
            interceptors.forEach {
                newClient.addInterceptor(it)
            }
            retrofitBuilder.client(newClient.build())
        }

        return retrofitBuilder.baseUrl(baseUrl).build().create(apiClass)
    }
}
