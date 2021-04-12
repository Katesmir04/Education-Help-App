package com.kate.app.educationhelp.domain.usecases

import com.kate.app.educationhelp.domain.irepositories.IRepository
import com.kate.app.educationhelp.domain.models.Test

class GetTestsByTopicIdUseCase(private val repository: IRepository) {
    suspend fun execute(id: String): List<Test> = repository.getTestsByTopicId(id)
}