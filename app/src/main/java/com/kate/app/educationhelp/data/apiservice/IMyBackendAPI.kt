package com.kate.app.educationhelp.data.apiservice

import com.kate.app.educationhelp.domain.models.Quize
import com.kate.app.educationhelp.domain.models.Test
import com.kate.app.educationhelp.domain.models.Topic
import com.kate.app.educationhelp.domain.models.User
import com.kate.app.educationhelp.presentation.quize.QuizeResultsViewModel.QuizeItem
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface IMyBackendAPI {

    @GET("/data/allTopics")
    suspend fun getAllTopics(): List<Topic>

    @GET("/data/getTestsByTopicId")
    suspend fun getTestsByTopicId(@Query("id") id: String): List<Test>

    @GET("/data/getAllQuizes")
    suspend fun getAllQuizes(): List<Quize>

    @POST("/data/pushNewUser")
    suspend fun pushNewUser(@Body user: User)

    @GET("/data/addBonusesToUser")
    suspend fun addBonusesToUser(@Query("id") id: String, @Query("number") number: Int)

    @GET("/data/getUserInfo")
    suspend fun getUserInfo(@Query("id") id: String): User

    @POST("/data/addPassedQuize")
    suspend fun addPassedQuize(@Body quizeItem: QuizeItem)

    @GET("/data/getPassedQuizes")
    suspend fun getPassedQuizes(@Query("id") id: String): List<QuizeItem>

    @GET("/data/getFavorites")
    suspend fun getFavorites(@Query("id") id: String): List<Topic>

    @POST("/data/addFavorite")
    suspend fun addFavorite(@Query("id") id: String, @Body topic: Topic)

    @POST("/data/removeFavorite")
    suspend fun removeFavorite(@Query("id") id: String, @Body topic: Topic)

    @GET("/data/getAllUsers")
    suspend fun getAllUsers(): List<User>
}