package com.example.myapp.Api

import android.util.Base64
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.salequick.com/v2/login_v3_api/"
    private val AUTH = "Basic "+ Base64.encodeToString("X-Requested-With:eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJtZXJjaGFudF9pZCI6IjQxMyJ9.zRIu9DQXJjIKaFdUzbQvbBH1bbzZzyA0tiO0nXS3VI8".toByteArray(), Base64.NO_WRAP)


    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()

            val requestBuilder = original.newBuilder()
                .addHeader("Authorization", AUTH)
                .method(original.method, original.body)


            val request = requestBuilder.build()
            chain.proceed(request)
        }.build()

        val instance: Api by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

            retrofit.create(Api::class.java)
        }
}