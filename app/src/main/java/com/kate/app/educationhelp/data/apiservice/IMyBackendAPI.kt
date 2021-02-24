package com.kate.app.educationhelp.data.apiservice

import com.kate.app.educationhelp.domain.models.Topic
import retrofit2.http.GET

interface IMyBackendAPI {
    @GET("/data/allTopics")
    suspend fun getAllTopics(): List<Topic>
}