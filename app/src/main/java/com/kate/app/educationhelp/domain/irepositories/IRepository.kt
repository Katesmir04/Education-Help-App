package com.kate.app.educationhelp.domain.irepositories

import com.google.firebase.auth.AuthResult
import com.kate.app.educationhelp.domain.models.Test
import com.kate.app.educationhelp.domain.models.Topic

interface IRepository {
    suspend fun getAllTopics(): List<Topic>
    suspend fun getTestsByTopicId(id: String): List<Test>

    interface AuthRepository{
        suspend fun signIn(email: String, password: String): AuthResult
        suspend fun signUp(email: String, password: String): AuthResult
    }
}