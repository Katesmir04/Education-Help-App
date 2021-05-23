package com.kate.app.educationhelp.domain.irepositories

import com.google.firebase.auth.AuthResult
import com.kate.app.educationhelp.domain.models.Quize
import com.kate.app.educationhelp.domain.models.Test
import com.kate.app.educationhelp.domain.models.Topic
import com.kate.app.educationhelp.domain.models.User
import com.kate.app.educationhelp.presentation.quize.QuizeResultsViewModel.QuizeItem

interface IRepository {
    suspend fun getAllTopics(): List<Topic>
    suspend fun getTestsByTopicId(id: String): List<Test>
    suspend fun getAllQuizes(): List<Quize>
    suspend fun addBonusesToUser(id: String, number: Int)
    suspend fun getUserInfo(id: String): User
    suspend fun addPassedQuize(quizeItem: QuizeItem)
    suspend fun getPassedQuizes(id: String): List<QuizeItem>
    suspend fun getFavorites(id: String): List<Topic>
    suspend fun addFavorite(id: String, topic: Topic)
    suspend fun removeFavorite(id: String, topic: Topic)

    interface AuthRepository {
        suspend fun signIn(email: String, password: String): AuthResult
        suspend fun signUp(email: String, password: String): AuthResult
        suspend fun pushNewUser(user: User)
    }
}