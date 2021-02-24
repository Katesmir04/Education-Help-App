package com.kate.app.educationhelp.data.repositories

import com.kate.app.educationhelp.data.apiservice.IMyBackendAPI
import com.kate.app.educationhelp.domain.irepositories.IRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MyBackendRepository: IRepository {


    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()


    private val backApi = retrofit.create(IMyBackendAPI::class.java)

    override suspend fun getAllTopics() = backApi.getAllTopics()
}