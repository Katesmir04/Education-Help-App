package com.kate.app.educationhelp.data.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kate.app.educationhelp.data.apiservice.IMyBackendAPI
import com.kate.app.educationhelp.domain.irepositories.IRepository
import com.kate.app.educationhelp.domain.models.Topic
import com.kate.app.educationhelp.domain.models.User
import com.kate.app.educationhelp.presentation.quize.QuizeResultsViewModel
import kotlinx.coroutines.tasks.await
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MyBackendRepository : IRepository, IRepository.AuthRepository {
    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

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

    override suspend fun getTestsByTopicId(id: String) = backApi.getTestsByTopicId(id)
    override suspend fun getAllQuizes() = backApi.getAllQuizes()
    override suspend fun addBonusesToUser(id: String, number: Int) =
        backApi.addBonusesToUser(id, number)

    override suspend fun signIn(email: String, password: String) =
        auth.signInWithEmailAndPassword(email, password).await()

    override suspend fun signUp(email: String, password: String) =
        auth.createUserWithEmailAndPassword(email, password).await()

    override suspend fun pushNewUser(user: User) = backApi.pushNewUser(user)

    override suspend fun getUserInfo(id: String) = backApi.getUserInfo(id)
    override suspend fun addPassedQuize(quizeItem: QuizeResultsViewModel.QuizeItem) =
        backApi.addPassedQuize(quizeItem)

    override suspend fun getPassedQuizes(id: String) = backApi.getPassedQuizes(id)
    override suspend fun getFavorites(id: String) = backApi.getFavorites(id)
    override suspend fun addFavorite(id: String, topic: Topic) = backApi.addFavorite(id, topic)
    override suspend fun removeFavorite(id: String, topic: Topic) =
        backApi.removeFavorite(id, topic)

    override suspend fun getAllUsers() = backApi.getAllUsers()

}