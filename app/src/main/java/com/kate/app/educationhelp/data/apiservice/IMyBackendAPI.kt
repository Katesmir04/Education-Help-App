package com.kate.app.educationhelp.data.apiservice

import com.kate.app.educationhelp.domain.models.Quize
import com.kate.app.educationhelp.domain.models.Test
import com.kate.app.educationhelp.domain.models.Topic
import retrofit2.http.GET
import retrofit2.http.Query

interface IMyBackendAPI {

    @GET("/data/allTopics")
    suspend fun getAllTopics(): List<Topic>

    @GET("/data/getTestsByTopicId")
    suspend fun getTestsByTopicId(@Query("id") id: String): List<Test>

    @GET("/data/getAllQuizes")
    suspend fun getAllQuizes(): List<Quize>
}