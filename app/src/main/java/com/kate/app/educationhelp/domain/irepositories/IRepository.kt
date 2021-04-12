package com.kate.app.educationhelp.domain.irepositories

import com.kate.app.educationhelp.domain.models.Test
import com.kate.app.educationhelp.domain.models.Topic

interface IRepository {
    suspend fun getAllTopics(): List<Topic>
    suspend fun getTestsByTopicId(id: String): List<Test>
}