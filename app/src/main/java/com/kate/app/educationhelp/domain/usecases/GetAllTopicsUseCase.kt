package com.kate.app.educationhelp.domain.usecases

import com.kate.app.educationhelp.domain.irepositories.IRepository
import com.kate.app.educationhelp.domain.models.Topic

class GetAllTopicsUseCase(private val repository: IRepository) {
    suspend fun execute(): List<Topic> = repository.getAllTopics()
}