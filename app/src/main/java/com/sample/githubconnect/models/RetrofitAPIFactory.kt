package com.sample.githubconnect.models

import com.sample.githubconnect.models.services.UserServices
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.Retrofit.*
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitAPIFactory {
    private val BASE_URL = "https://api.github.com/"
    private val API_TIMEOUT: Long = 30

    fun retrofitAPI(): UserServices {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC
        val builder = OkHttpClient.Builder()
        builder.readTimeout(API_TIMEOUT, TimeUnit.SECONDS)
        builder.writeTimeout(API_TIMEOUT, TimeUnit.SECONDS)
        builder.connectTimeout(API_TIMEOUT, TimeUnit.SECONDS)

        val accept = "application/vnd.github.v3+json"
        val userAgent = "chetan-AD"
        val requestInterceptor = Interceptor { chain ->
            val originalRequest = chain.request()
            val newRequest = originalRequest.newBuilder()
                .addHeader("accept", accept)
                .addHeader("user-agent", userAgent)
                .build()
            chain.proceed(newRequest)
        }
        builder.addInterceptor(logging)
        builder.addInterceptor(requestInterceptor)

        return Builder()
            .baseUrl(BASE_URL)
            .client(builder.build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(UserServices::class.java)
    }
}