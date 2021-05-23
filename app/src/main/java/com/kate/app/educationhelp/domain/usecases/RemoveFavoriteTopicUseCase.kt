package com.kate.app.educationhelp.domain.usecases

import com.kate.app.educationhelp.domain.irepositories.IRepository
import com.kate.app.educationhelp.domain.models.Topic

class RemoveFavoriteTopicUseCase(private val repository: IRepository) {
    suspend fun execute(id: String, topic: Topic) = repository.removeFavorite(id, topic)
}