package com.kate.app.educationhelp.data.apiservice

import com.kate.app.educationhelp.domain.models.Test
import com.kate.app.educationhelp.domain.models.Topic
import com.kate.app.educationhelp.domain.models.User
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface IMyBackendAPI {

    @GET("/data/allTopics")
    suspend fun getAllTopics(): List<Topic>

    @GET("/data/getTestsByTopicId")
    suspend fun getTestsByTopicId(@Query("id") id: String): List<Test>

    @POST("/data/pushNewUser")
    suspend fun pushNewUser(@Query("user") user: User)
}